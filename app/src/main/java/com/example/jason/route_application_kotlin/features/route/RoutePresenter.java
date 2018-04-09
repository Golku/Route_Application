package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteResponse;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveResponse;
import com.example.jason.route_application_kotlin.data.pojos.api.UnOrganizedRoute;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements
        MvpRoute.Presenter,
        ApiCallback.RouteResponseCallback,
        ApiCallback.SingleDriveResponseCallback {

    private final String log_tag = "routeLogTag";

    private MvpRoute.View view;
    private MvpRoute.Interactor interactor;

    private String routeCode;

    private UnOrganizedRoute unOrganizedRoute;
    private OrganizedRoute organizedRoute;

    private int selectedPrivateAddressesCount;
    private int selectedBusinessAddressesCount;

    @Inject
    public RoutePresenter(MvpRoute.View view, MvpRoute.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    @Override
    public void onMapReady() {
        if(unOrganizedRoute != null) {
            view.delegateAddressList(unOrganizedRoute.getValidAddressesList());
        }
    }

    @Override
    public void getRouteFromApi() {
        interactor.getRoute(this, routeCode);
    }

    @Override
    public void getDriveInformation(SingleDriveRequest request) {
        interactor.getDriveInformation(this, request);
    }

    @Override
    public void onMarkerRemoved(String destination) {
        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("remove");
        delegation.setDestination(destination);
        view.delegateDestination(delegation);
    }

    @Override
    public void onRemoveMultipleMarkers(String destination) {
        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("removeMultiple");
        delegation.setDestination(destination);
        view.delegateMultipleDestination(delegation);
    }

    @Override
    public void onListItemClick(String address) {
        view.showAddressDetails(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        view.navigateToDestination(address);
    }

    private void onRouteUnorganized(UnOrganizedRoute unOrganizedRoute) {
        if(unOrganizedRoute != null){
            this.unOrganizedRoute = unOrganizedRoute;
        }else{
            view.showToast("Api din't send the route properly. Please try again.");
            view.closeActivity();
        }
    }

    private void onRouteOrganized(OrganizedRoute organizedRoute) {
        if(organizedRoute != null){
//            view.setupFragments(organizedRoute);
        }else{
            view.showToast("Api din't send the route properly. Please try again.");
            view.closeActivity();
        }
    }

    private void onInvalidState() {
        view.showToast("Invalid route state");
        view.closeActivity();
    }

//        If the server has an error and sends back a routeResponse with a html page
//        the response processing will fail! FIX THIS!!!

    @Override
    public void onRouteResponse(RouteResponse response) {
        int routeState = response.getRouteState();

        switch (routeState) {
            case 5 : onRouteUnorganized(response.getUnOrganizedRoute());
                break;
            case 8 : onRouteOrganized(response.getOrganizedRoute());
                break;
            default: onInvalidState();
        }
    }

    @Override
    public void onRouteResponseFailure() {
        view.showToast("Unable to fetch route from api");
        view.closeActivity();
    }

    @Override
    public void onSingleDriveResponse(SingleDriveResponse response) {
        if(response.getSingleDrive() != null){
            RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
            delegation.setOperation("add");
            delegation.setSingleDrive(response.getSingleDrive());
            view.delegateDriveInformation(delegation);
        }
    }

    @Override
    public void onSingleDriveResponseFailure() {
        view.showToast("Unable to fetch drive information from api");
    }
}
