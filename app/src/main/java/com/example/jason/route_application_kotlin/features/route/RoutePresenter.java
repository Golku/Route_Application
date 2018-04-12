package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.RouteInfoHolder;
import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.Route;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveResponse;

import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements
        MvpRoute.Presenter,
        ApiCallback.SingleDriveResponseCallback {

    private final String log_tag = "routeLogTag";

    private MvpRoute.View view;
    private MvpRoute.Interactor interactor;

    private Route route;
    private List<SingleDrive> routeList;

    private SimpleDateFormat sdf;
    private long deliveryTimeSum;

    @Inject
    public RoutePresenter(MvpRoute.View view, MvpRoute.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        sdf = new SimpleDateFormat("kk:mm");
        deliveryTimeSum = 0;
    }

    @Override
    public void initializeRoute(Route route) {
        this.route = route;

        RouteInfoHolder routeInfoHolder = new RouteInfoHolder();

        routeInfoHolder.setAddressList(route.getAddressList());

        if(route.getRouteList() != null && !route.getRouteList().isEmpty()){
            routeList = route.getRouteList();
            routeInfoHolder.setOrganized(true);
        }else{
            routeList = new ArrayList<>();
            routeInfoHolder.setOrganized(false);
        }

        routeInfoHolder.setRouteList(routeList);

        view.setupFragments(routeInfoHolder);
    }

    @Override
    public void getDriveInformation(SingleDriveRequest request) {
        interactor.getDriveInformation(this, request);
    }

    private void addDeliveryTime(SingleDrive singleDrive){
        long date = System.currentTimeMillis();
        long driveTime = singleDrive.getDriveDurationInSeconds()*1000;
        long packageDeliveryTime = 120000;
        deliveryTimeSum = deliveryTimeSum + (driveTime + packageDeliveryTime);
        long deliveryTime = date + deliveryTimeSum;
        String deliveryTimeString = sdf.format(deliveryTime);
        singleDrive.setDeliveryTimeInMillis(deliveryTime);
        singleDrive.setDeliveryTimeHumanReadable(deliveryTimeString);
        getRouteEndTime();
    }

    @Override
    public void markerDeselected(String destination) {

        int position = -1;

        for(SingleDrive singleDrive : routeList){

            String driveDestination = singleDrive.getDestinationFormattedAddress().getFormattedAddress();

            if(destination.equals(driveDestination)){
                position = routeList.indexOf(singleDrive);
                routeList.remove(singleDrive);
                removeDeliveryTime(singleDrive);
                break;
            }
        }

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("remove");
        delegation.setPosition(position);

        view.delegatePosition(delegation);
    }

    @Override
    public void multipleMarkersDeselected(String destination) {

        int position = -1;

        for(SingleDrive singleDrive : routeList){

            String driveDestination = singleDrive.getDestinationFormattedAddress().getFormattedAddress();

            if(destination.equals(driveDestination)){
                position = routeList.indexOf(singleDrive);
                deliveryTimeSum = routeList.get(position-1).getDeliveryTimeInMillis();
                break;
            }
        }

        routeList.subList(position, routeList.size()).clear();

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("multipleRemove");
        delegation.setPosition(position);

//        view.delegatePosition(delegation);
    }

    private void removeDeliveryTime(SingleDrive singleDrive){
        long driveTime = singleDrive.getDriveDurationInSeconds()*1000;
        long packageDeliveryTime = 120000;
        deliveryTimeSum = deliveryTimeSum - (driveTime + packageDeliveryTime);
        getRouteEndTime();
    }

    private void getRouteEndTime(){
        int routeSize = routeList.size();
        if (routeSize > 0) {
            int finalDriveIndex = routeSize - 1;
            SingleDrive finalDrive = routeList.get(finalDriveIndex);
            view.updateRouteEndTime(finalDrive.getDeliveryTimeHumanReadable());
        } else {
            view.updateRouteEndTime("end time");
        }
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
    public void onSingleDriveResponse(SingleDriveResponse response) {
        if(response.getSingleDrive() != null){
            routeList.add(response.getSingleDrive());
            addDeliveryTime(response.getSingleDrive());

            RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
            delegation.setOperation("add");
            delegation.setPosition(routeList.size() - 1);

            view.delegatePosition(delegation);
        }
    }

    @Override
    public void onSingleDriveResponseFailure() {
        view.showToast("Unable to fetch drive information from api");
    }
}
