package com.example.jason.route_application_kotlin.features.route;

import android.app.Activity;
import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.PresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import com.example.jason.route_application_kotlin.interactors.RouteInteractor;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements MvpRoute.Presenter, PresenterCallBack {

    private final MvpRoute.View view;

    private MvpRoute.Interactor routeInteractor;

    @Inject
    public RoutePresenter(MvpRoute.View view, MvpRoute.Interactor interactor) {
        this.view = view;
        this.routeInteractor = interactor;
    }

    @Override
    public void sendRouteToApi(OutGoingRoute outGoingRoute) {
        routeInteractor.submitRouteForOrganizing(this, outGoingRoute);
    }

    @Override
    public void processApiResponse(ApiResponse apiResponse) {
        view.setUpAdapter(apiResponse.getOrganizedRoute());
//        Log.d("RoutePresenter", String.valueOf(apiResponse.isOrganizingInProgress()));
//        Log.d("RoutePresenter", apiResponse.getOrganizedRoute().getRouteCode());
//
//        for(int i=0; i<apiResponse.getOrganizedRoute().getRouteList().size(); i++){
//            Log.d("RoutePresenter", apiResponse.getOrganizedRoute().getRouteList().get(i).getOriginFormattedAddress().getFormattedAddress());
//            Log.d("RoutePresenter", apiResponse.getOrganizedRoute().getRouteList().get(i).getDestinationFormattedAddress().getFormattedAddress());
//        }
    }

    @Override
    public void onListItemClick(String address) {
        view.showAddressDetails(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        view.navigateToDestination(address);
    }
}
