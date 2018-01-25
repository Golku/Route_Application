package com.example.jason.route_application.controller;

import com.example.jason.route_application.model.InformationFetcher;
import com.example.jason.route_application.model.pojos.ApiResponse;
import com.example.jason.route_application.model.pojos.FormattedAddress;
import com.example.jason.route_application.model.pojos.SingleOrganizedRoute;
import com.example.jason.route_application.view.RouteActivity;

public class RouteActivityController {

    private RouteActivity routeActivity;
    public ApiResponse apiResponse;

    public RouteActivityController(RouteActivity routeActivity) {
        this.routeActivity = routeActivity;

    }

    public void getRoute(){
        routeActivity.beginGetRouteTask();
    }

    public void setUpRoute(SingleOrganizedRoute singleOrganizedRoute){
        routeActivity.setUpAdapter(singleOrganizedRoute);
    }

    public void displayMessage(String type, boolean visible, String message){

        if(type == "loadingBar"){
            if(visible){
                routeActivity.showLoadingBar();
            }else{
                routeActivity.hideLoadingBar();
            }
        } else if (type == "toast"){
            routeActivity.showToast(message);
        }

    }

    public void onListItemClick(int adapterPosition){
        routeActivity.startAddressDetailsActivity(adapterPosition);
    }

    public void onListItemGoButtonClick(int adapterPosition){
        routeActivity.startNavigationOnGoogleMaps(adapterPosition);
    }

}
