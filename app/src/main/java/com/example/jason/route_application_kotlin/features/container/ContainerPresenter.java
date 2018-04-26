package com.example.jason.route_application_kotlin.features.container;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.RouteInfoHolder;
import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.Container;
import com.example.jason.route_application_kotlin.data.pojos.Session;
import com.example.jason.route_application_kotlin.data.pojos.api.Route;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.features.shared.BasePresenter;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 07-Feb-18.
 */

public class ContainerPresenter extends BasePresenter implements
        MvpContainer.Presenter,
        ApiCallback.ContainerResponseCallback,
        ApiCallback.SingleDriveResponseCallback {

    private final String debugTag = "debugTag";

    private MvpContainer.View view;
    private MvpContainer.Interactor interactor;

    private Session session;
    private Container container;
    private Route route;
    private List<FormattedAddress> addressList;
    private List<SingleDrive> routeList;

    private int[] deliveryCompletion;
    private int deliveredPrivate;
    private int deliveredBusiness;

    private SimpleDateFormat sdf;

    @Inject
    public ContainerPresenter(MvpContainer.View view, MvpContainer.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        deliveryCompletion = new int[4];
        deliveredPrivate = 0;
        deliveredBusiness = 0;
        sdf = new SimpleDateFormat("kk:mm");
    }

    @Override
    public void getContainer(Session session) {
        this.session = session;
        interactor.getContainer(session.getUsername(), this);
    }

    private void setRouteInfo(){

        if(container.getRoute() == null){
            addressList = new ArrayList<>();
            routeList = new ArrayList<>();
            return;
        }else{
            route = container.getRoute();
            System.out.println("route is not null");
        }

        if(route.getAddressList() != null){
            addressList = route.getAddressList();
        }else{
            addressList = new ArrayList<>();
        }

        if (route.getRouteList() != null && !route.getRouteList().isEmpty()) {
            routeList = route.getRouteList();
        } else {
            routeList = new ArrayList<>();
        }

        deliveryCompletion[1] = route.getPrivateAddressCount();
        deliveryCompletion[3] = route.getBusinessAddressCount();
    }

    private void initializeContainer(Container container) {
        this.container = container;

        deliveryCompletion[0] = deliveredPrivate;
        deliveryCompletion[2] = deliveredBusiness;

        setRouteInfo();

        view.updateDeliveryCompletion(deliveryCompletion);

        RouteInfoHolder routeInfoHolder = new RouteInfoHolder();

        routeInfoHolder.setAddressList(addressList);
        routeInfoHolder.setRouteList(routeList);

        view.setupFragments(routeInfoHolder);
    }

    private List<String> orderRoute(List<SingleDrive> routeList){
        List<String> routeOrder = new ArrayList<>();
        for(SingleDrive drive : routeList){
            routeOrder.add(drive.getDestinationFormattedAddress().getFormattedAddress());
        }
        return routeOrder;
    }

    @Override
    public void getDriveInformation(SingleDriveRequest request) {
        interactor.getDriveInformation(request,this);
    }

    private void addDeliveryTime(SingleDrive singleDrive) {
        long deliveryTime;
        long driveTime = singleDrive.getDriveDurationInSeconds() * 1000;
        long PACKAGE_DELIVERY_TIME = 120000;

        if (routeList.size() > 1) {
            SingleDrive previousDrive = routeList.get(routeList.indexOf(singleDrive) - 1);
            deliveryTime = previousDrive.getDeliveryTimeInMillis() + driveTime + PACKAGE_DELIVERY_TIME;
        } else {
            long date = System.currentTimeMillis();
            deliveryTime = date + driveTime + PACKAGE_DELIVERY_TIME;
        }

        String deliveryTimeString = sdf.format(deliveryTime);

        singleDrive.setDeliveryTimeInMillis(deliveryTime);
        singleDrive.setDeliveryTimeHumanReadable(deliveryTimeString);

        onUpdateRouteEndTime();

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("add");
        delegation.setPosition(routeList.indexOf(singleDrive));
        view.delegateRouteChange(delegation);
    }

    @Override
    public void markerDeselected() {
        int position = routeList.size() - 1;
        routeList.remove(position);

        onUpdateRouteEndTime();

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("remove");
        delegation.setPosition(position);
        view.delegateRouteChange(delegation);
    }

    @Override
    public void multipleMarkersDeselected(String destination) {
        int position = -1;

        for (SingleDrive singleDrive : routeList) {
            String driveDestination = singleDrive.getDestinationFormattedAddress().getFormattedAddress();

            if (destination.equals(driveDestination)) {
                position = routeList.indexOf(singleDrive);
                break;
            }
        }

        routeList.subList(position, routeList.size()).clear();

        onUpdateRouteEndTime();

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("multipleRemove");
        delegation.setPosition(position);
        view.delegateRouteChange(delegation);
    }

    private void onUpdateRouteEndTime() {
        if (routeList.size() > 0) {
            SingleDrive finalDrive = routeList.get(routeList.size() - 1);
            view.updateRouteEndTime(finalDrive.getDeliveryTimeHumanReadable());
        } else {
            view.updateRouteEndTime("");
        }
    }

    @Override
    public void onUpdateDeliveryCompletion(int[] deliveryCompletion) {

    }

    @Override
    public void onListItemClick(String address) {
        view.showAddressDetails(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        view.navigateToDestination(address);
    }

    @Override
    public void onContainerResponse(Container response) {
        if (response != null){
            initializeContainer(response);
        }
    }

    @Override
    public void onContainerResponseFailure() {
        view.showToast("Unable to fetch container from api");
    }

//        If the server has an error and sends back a routeResponse with a html page
//        the response processing will fail! FIX THIS!!!
    @Override
    public void onSingleDriveResponse(SingleDrive response) {
        if (response != null) {
            routeList.add(response);
            addDeliveryTime(response);
        }
    }

    @Override
    public void onSingleDriveResponseFailure() {
        view.showToast("Unable to fetch drive information from api");
    }
}
