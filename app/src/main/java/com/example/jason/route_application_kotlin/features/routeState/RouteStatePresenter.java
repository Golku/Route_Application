package com.example.jason.route_application_kotlin.features.routeState;

import android.os.Handler;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

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

    private void onValidatingAddresses() {

        view.updateRouteStateTextView("Validating the addresses...");

        if(networkFetchAttempts<12){

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getRouteState();
                }
            }, 10000);

        }else{
            view.showToast("Still validating after 5 fetch attempts");
        }

    }

    private void onHasInvalidAddresses() {
        view.startCorrectInvalidAddressesActivity(routeCode);
    }

    private void onOrganizingRoute() {

        view.updateRouteStateTextView("The route is being organized...");

        if(networkFetchAttempts<12){

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getRouteState();
                }
            }, 10000);

        }else{
            view.showToast("Still organizing after 5 fetch attempts");
        }

    }

    private void onRouteOrganized() {
        view.startRouteActivity(routeCode);
    }

    private void onInvalidRoute() {
        view.updateRouteStateTextView("This route does not exist");
        view.closeActivity();
    }

    @Override
    public void onApiResponse(ApiResponse apiResponse) {

        String routeState = apiResponse.getRouteState();

        switch (routeState) {
            case "validatingAddresses": onValidatingAddresses();
                break;
            case "hasInvalidAddresses": onHasInvalidAddresses();
                break;
            case "organizingRoute": onOrganizingRoute();
                break;
            case "routeOrganized": onRouteOrganized();
                break;
            case "invalidRoute": onInvalidRoute();
                break;
            default: view.closeActivity();
        }

    }

    @Override
    public void onApiResponseFailure() {

    }
}
