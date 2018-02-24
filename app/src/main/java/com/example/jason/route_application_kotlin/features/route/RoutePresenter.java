package com.example.jason.route_application_kotlin.features.route;

import android.os.Handler;
import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements MvpRoute.Presenter, ApiPresenterCallBack {

    private final String log_tag = "routePresenterLogTag";

    private final MvpRoute.View view;

    private MvpRoute.Interactor interactor;

    private String routeCode;
    private int routeFetchAttempt;
    private final Handler handler = new Handler();

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
    public void sendRouteToApi(OutGoingRoute outGoingRoute) {
        view.onStartNetworkOperation();
        interactor.submitRouteForOrganizing(this, outGoingRoute);
    }

    @Override
    public void getRouteFromApi() {
        routeFetchAttempt++;
        interactor.getOrganizedRouteFromApi(this, routeCode);
    }

    @Override
    public void processApiResponse(ApiResponse apiResponse) {
        //check response for errors and other messages
        if(!apiResponse.getRouteIsNull()) {
            if(!apiResponse.getOrganizingInProgress()){
                if(apiResponse.getRouteHasInvalidAddresses()){
                    view.onFinishNetworkOperation();
                    view.showInvalidAddresses(apiResponse.getInvalidAddresses());
                }else{
                    if(apiResponse.getOrganizedRoute() != null) {
                        view.onFinishNetworkOperation();
                        view.setUpAdapter(apiResponse.getOrganizedRoute());
                    }else{
                        view.onFinishNetworkOperation();
                        view.showToast("Api din't send the route properly. Please try again.");
                    }
                }
            }else{
                if(routeFetchAttempt<5){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getRouteFromApi();
                        }
                    }, 5000);
                }else{
                    //message the user that the route was not fetch after x amount of attempts
                    //user can start the route fetching process again with a button click.
                    view.showToast("Unable to fetch route after 5 attempts.");
                }
            }
        }else{
            view.onFinishNetworkOperation();
            view.showToast("Route does not exist. Try resubmitting the route.");
        }
//        Log.d("RoutePresenter", String.valueOf(apiResponse.isOrganizingInProgress()));
//        Log.d("RoutePresenter", apiResponse.getOrganizedRoute().getRouteCode());
//
//        for(int i=0; i<apiResponse.getOrganizedRoute().getRouteList().size(); i++){
//            Log.d("RoutePresenter", apiResponse.getOrganizedRoute().getRouteList().get(i).getOriginFormattedAddress().getFormattedAddress());
//            Log.d("RoutePresenter", apiResponse.getOrganizedRoute().getRouteList().get(i).getDestinationFormattedAddress().getFormattedAddress());
//        }
    }

    @Override
    public void onApiResponseFailure() {
        view.onFinishNetworkOperation();
        view.showToast("Connection failed");
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
