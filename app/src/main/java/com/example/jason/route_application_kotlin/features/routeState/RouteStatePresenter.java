package com.example.jason.route_application_kotlin.features.routeState;

import android.os.Handler;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;

import javax.inject.Inject;

/**
 * Created by Jason on 3/15/2018.
 */

public class RouteStatePresenter implements MvpRouteState.Presenter, ApiPresenterCallBack {

    private MvpRouteState.View view;
    private MvpRouteState.Interactor interactor;
    private final Handler handler;

    private int networkFetchAttempts;
    private String routeCode;

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

    private void onReadyToBeBuild() {
        view.startRouteActivity(routeCode);
    }

    private void onOrganizingRoute() {
        view.updateRouteStateTextView("The route is being organized...");
        requestTimer("Still organizing after 6 fetch attempts");
    }

    private void onOrganizingError() {
        view.showToast("Unable to organized route");
        view.closeActivity();
    }

    private void onRouteOrganized() {
        view.startRouteActivity(routeCode);
    }

    @Override
    public void onApiResponse(ApiResponse apiResponse) {

        int routeState = apiResponse.getRouteState();

//        view.showToast("route state: "+String.valueOf(routeState));

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
            case 5 : onReadyToBeBuild();
                break;
            case 6 : onOrganizingRoute();
                break;
            case 7 : onOrganizingError();
                break;
            case 8 : onRouteOrganized();
                break;
            default: view.closeActivity();//Make a function where you handle a non existent state
        }

    }

    @Override
    public void onApiResponseFailure() {
        view.showToast("Unable to connect to the api");
        view.closeActivity();
    }
}
