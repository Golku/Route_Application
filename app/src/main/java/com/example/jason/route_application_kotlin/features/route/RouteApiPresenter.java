package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteApiPresenter implements MvpRoute.Presenter, ApiPresenterCallBack {

    private final MvpRoute.View view;

    private MvpRoute.Interactor interactor;

    @Inject
    public RouteApiPresenter(MvpRoute.View view, MvpRoute.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void sendRouteToApi(OutGoingRoute outGoingRoute) {
        interactor.submitRouteForOrganizing(this, outGoingRoute);
    }

    @Override
    public void processApiResponse(ApiResponse apiResponse) {
        //check response for errors and other messages
        view.setUpAdapter(apiResponse.getOrganizedRoute());
//        Log.d("RouteApiPresenter", String.valueOf(apiResponse.isOrganizingInProgress()));
//        Log.d("RouteApiPresenter", apiResponse.getOrganizedRoute().getRouteCode());
//
//        for(int i=0; i<apiResponse.getOrganizedRoute().getRouteList().size(); i++){
//            Log.d("RouteApiPresenter", apiResponse.getOrganizedRoute().getRouteList().get(i).getOriginFormattedAddress().getFormattedAddress());
//            Log.d("RouteApiPresenter", apiResponse.getOrganizedRoute().getRouteList().get(i).getDestinationFormattedAddress().getFormattedAddress());
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
