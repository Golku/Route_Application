package com.example.jason.route_application.features.container;

import com.example.jason.route_application.data.api.ApiCallback;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.FragmentDelegation;
import com.example.jason.route_application.data.pojos.api.Container;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.Route;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.features.shared.BasePresenter;

import android.util.Log;

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
        ApiCallback.RouteResponseCallback,
        ApiCallback.DriveResponseCallback,
        ApiCallback.AddAddressCallback{

    private final String debugTag = "debugTag";

    private MvpContainer.View view;

    private MvpContainer.Interactor interactor;

    private Session session;

    private Container container;

    private Route route;

    private List<Address> addressList;

    private List<Drive> routeList;

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

    @Override
    public void getRoute() {
        interactor.getRoute(session.getUsername(), this);
    }

    private void initializeContainer(Container container) {
        this.container = container;

//        In this method prepare the container by loading the values that are displayed in the top information bar

//        deliveryCompletion[0] = deliveredPrivate;
//        deliveryCompletion[2] = deliveredBusiness;
//        deliveryCompletion[1] = route.getPrivateAddressCount();
//        deliveryCompletion[3] = route.getBusinessAddressCount();
//        view.updateDeliveryCompletion(deliveryCompletion);

        addressList = new ArrayList<>();
        routeList = new ArrayList<>();

        setupRouteInfo(container.getRoute());
    }

    private void setupRouteInfo(Route route) {

        if (route == null) {
            Log.d(debugTag, "route is null");
            return;
        }

        container.setRoute(route);

        if (route.getAddressList() != null) {
            addressList = route.getAddressList();
            for (Address address : addressList) {
                Log.d(debugTag, "address: " + address.getAddress());
                Log.d(debugTag, "business: " + address.isBusiness());
            }
        }

        if (route.getRouteList() != null) {
            routeList = route.getRouteList();
        }

        RouteInfoHolder routeInfoHolder = new RouteInfoHolder();

        routeInfoHolder.setAddressList(addressList);
        routeInfoHolder.setRouteList(routeList);

        view.setupFragments(routeInfoHolder);
    }

    private List<String> orderRoute(List<Drive> routeList) {
        List<String> routeOrder = new ArrayList<>();
        for (Drive drive : routeList) {
            routeOrder.add(drive.getDestinationAddress().getAddress());
        }
        return routeOrder;
    }

    @Override
    public void getDriveInformation(DriveRequest request) {
        interactor.getDriveInformation(request, this);
    }

    @Override
    public void addAddress(Address address) {
        interactor.addAddress(address, this);
    }

    private void addDeliveryTime(Drive drive) {
        long deliveryTime;
        long driveTime = drive.getDriveDurationInSeconds() * 1000;
        long PACKAGE_DELIVERY_TIME = 120000;

        if (routeList.size() > 1) {
            Drive previousDrive = routeList.get(routeList.indexOf(drive) - 1);
            deliveryTime = previousDrive.getDeliveryTimeInMillis() + driveTime + PACKAGE_DELIVERY_TIME;
        } else {
            long date = System.currentTimeMillis();
            deliveryTime = date + driveTime + PACKAGE_DELIVERY_TIME;
        }

        String deliveryTimeString = sdf.format(deliveryTime);

        drive.setDeliveryTimeInMillis(deliveryTime);
        drive.setDeliveryTimeHumanReadable(deliveryTimeString);

        onUpdateRouteEndTime();

        FragmentDelegation delegation = new FragmentDelegation();
        delegation.setOperation("add");
        delegation.setPosition(routeList.indexOf(drive));
        delegation.setListIdentifier("routeList");
        view.delegateRouteChange(delegation);
    }

    @Override
    public void markerDeselected() {
        int position = routeList.size() - 1;
        routeList.remove(position);

        onUpdateRouteEndTime();

        FragmentDelegation delegation = new FragmentDelegation();
        delegation.setOperation("remove");
        delegation.setPosition(position);
        delegation.setListIdentifier("routeList");
        view.delegateRouteChange(delegation);
    }

    @Override
    public void multipleMarkersDeselected(String destination) {
        int position = -1;

        for (Drive drive : routeList) {
            String driveDestination = drive.getDestinationAddress().getAddress();

            if (destination.equals(driveDestination)) {
                position = routeList.indexOf(drive);
                break;
            }
        }

        routeList.subList(position, routeList.size()).clear();

        onUpdateRouteEndTime();

        FragmentDelegation delegation = new FragmentDelegation();
        delegation.setOperation("multipleRemove");
        delegation.setPosition(position);
        view.delegateRouteChange(delegation);
    }

    private void onUpdateRouteEndTime() {
        if (routeList.size() > 0) {
            Drive finalDrive = routeList.get(routeList.size() - 1);
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

//        If the server has an error and sends back a routeResponse with a html page
//        the response processing will fail! FIX THIS!!!

    @Override
    public void onContainerResponse(Container response) {
        if (response != null) {
            initializeContainer(response);
        }
    }

    @Override
    public void onContainerResponseFailure() {
        view.showToast("Unable to fetch container from api");
    }

    @Override
    public void onRouteResponse(Route response) {
        setupRouteInfo(response);
    }

    @Override
    public void onRouteResponseFailure() {
        view.showToast("Unable to fetch route from api");
    }

    @Override
    public void onSingleDriveResponse(Drive response) {
        if (response != null) {
            routeList.add(response);
            addDeliveryTime(response);
        }
    }

    @Override
    public void onSingleDriveResponseFailure() {
        view.showToast("Unable to fetch drive information from api");
    }

    @Override
    public void onAddAddressResponse(Address response) {

    }

    @Override
    public void onAddAddressResponseFailure() {

    }
}
