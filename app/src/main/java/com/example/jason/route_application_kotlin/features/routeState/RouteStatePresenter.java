package com.example.jason.route_application_kotlin.features.routeState;

import android.os.Handler;
import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.OutGoingRoute;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteResponse;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;

import javax.inject.Inject;

import java.util.ArrayList;

/**
 * Created by Jason on 3/15/2018.
 */

public class RouteStatePresenter implements MvpRouteState.Presenter, ApiCallback.RouteResponseCallback {

    private final String debugTag = "debugTag";

    private MvpRouteState.View view;
    private MvpRouteState.Interactor interactor;

    private final Handler handler;
    private String routeCode;
    private int networkFetchAttempts;

    @Inject
    public RouteStatePresenter(MvpRouteState.View view, MvpRouteState.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.handler = new Handler();
    }

    @Override
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    @Override
    public void submitRoute(OutGoingRoute outGoingRoute) {
        interactor.sendRoute(this, outGoingRoute);
    }

    @Override
    public void getRouteState() {
        networkFetchAttempts++;
        interactor.getRouteState(this, routeCode);
    }

    private void requestTimer(String message){
        if(networkFetchAttempts<12){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getRouteState();
                }
            }, 10000);
        }else{
            view.showToast(message);
            view.closeActivity();
//            show retry button. This button will call a function to reset networkFetchAttempts
//            and start trying to get the route again
        }
    }

    private void onRouteIsNull() {
        view.showToast("This route does not exist");
        view.closeActivity();
    }

    private void onInvalidRouteSubmitted() {
        view.showToast("The route was submitted with no addresses");
        view.closeActivity();
    }

    private void onInQueue() {
        view.updateRouteStateTextView("Route is in queue...");
        requestTimer("Still in queue after 6 fetch attempts");
    }

    private void onValidatingAddresses() {
        view.updateRouteStateTextView("Validating the addresses...");
        requestTimer("Still validating after 6 fetch attempts");
    }

    private void onHasInvalidAddresses() {
        networkFetchAttempts = 0;
        view.startCorrectInvalidAddressesActivity(routeCode);
    }

    private void onReadyToBeBuild(ArrayList<FormattedAddress> addressList) {
        view.startRouteUnorganized(routeCode, addressList);
    }

    private void onOrganizingRoute() {
        view.updateRouteStateTextView("The route is being organized...");
        requestTimer("Still organizing after 6 fetch attempts");
    }

    private void onOrganizingError() {
        view.showToast("Unable to organized route");
        view.closeActivity();
    }

    private void onRouteOrganized(ArrayList<SingleDrive> routeList) {
        view.startRouteOrganized(routeCode, routeList);
    }

//    if the server has an error and returns a html page the application crashes. FIX THIS!!
    @Override
    public void onRouteResponse(RouteResponse response) {
        int routeState = response.getRouteState();

        switch (routeState) {
            case 0 : onRouteIsNull();
                break;
            case 1 : onInvalidRouteSubmitted();
                break;
            case 2 : onInQueue();
                break;
            case 3 : onValidatingAddresses();
                break;
            case 4 : onHasInvalidAddresses();
                break;
            case 5 : onReadyToBeBuild(response.getAddressList());
                break;
            case 6 : onOrganizingRoute();
                break;
            case 7 : onOrganizingError();
                break;
            case 8 : onRouteOrganized(response.getRouteList());
                break;
            default: view.closeActivity();//Make a function where you handle a non existent state
        }
    }

    @Override
    public void onRouteResponseFailure() {
        view.showToast("Unable to connect to the api");
        view.closeActivity();
    }
}
