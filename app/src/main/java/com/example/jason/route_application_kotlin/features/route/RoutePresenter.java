package com.example.jason.route_application_kotlin.features.route;

import android.os.Handler;
import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements MvpRoute.Presenter, ApiPresenterCallBack {

    private final String log_tag = "routeLogTag";

    private MvpRoute.View view;
    private MvpRoute.Interactor interactor;

    private String routeCode;

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
    public void getRouteFromApi() {
        view.onStartNetworkOperation();
        interactor.getOrganizedRouteFromApi(this, routeCode);
    }

    @Override
    public void onListItemClick(String address) {
        view.showAddressDetails(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        view.navigateToDestination(address);
    }

    private void onRouteOrganized(OrganizedRoute organizedRoute) {

        if(organizedRoute != null){
            view.onFinishNetworkOperation();
            view.setUpAdapter(organizedRoute);
        }else{
            view.showToast("Api din't send the route properly. Please try again.");
            view.closeActivity();
        }

    }

    private void onInvalidState() {
        view.showToast("Invalid route state");
        view.closeActivity();
    }

    @Override
    public void onApiResponse(ApiResponse apiResponse) {

//        If the server has an error and sends back a apiResponse with a html page
//        the response processing will fail! FIX THIS!!!

        int routeState = apiResponse.getRouteState();

        switch (routeState) {
            case 7 : onRouteOrganized(apiResponse.getOrganizedRoute());
                break;
            default: onInvalidState();
        }
    }

    @Override
    public void onApiResponseFailure() {
        view.showToast("Unable to connect to the api");
        view.closeActivity();
    }
}
