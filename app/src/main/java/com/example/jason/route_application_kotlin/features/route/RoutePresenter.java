package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.TravelInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements MvpRoute.Presenter, ApiPresenterCallBack {

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
        this.organizedRoute = new OrganizedRoute();
        this.selectedPrivateAddressesCount = 0;
        this.selectedBusinessAddressesCount = 0;
    }

    @Override
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    @Override
    public void getRouteFromApi() {
        interactor.getOrganizedRouteFromApi(this, routeCode);
    }

    @Override
    public void getTravelInformation(TravelInformationRequest travelInformationRequest) {
        interactor.getTravelInformation(this, travelInformationRequest);
    }

    private void setupRouteInformation() {

        Map<String, Integer> counters = new HashMap<>();

        int privateAddressListSize = this.unOrganizedRoute.getPrivateAddressList().size();
        this.organizedRoute.setPrivateAddressesCount(privateAddressListSize);
        int businessAddressListSize = this.unOrganizedRoute.getBusinessAddressList().size();
        this.organizedRoute.setBusinessAddressesCount(businessAddressListSize);

        counters.put("privateCurrentSize", this.selectedPrivateAddressesCount);
        counters.put("privateMaxSize", privateAddressListSize);
        counters.put("businessCurrentSize", this.selectedBusinessAddressesCount);
        counters.put("businessMaxSize", businessAddressListSize);

        view.updateRouteInformation(counters);
    }

    @Override
    public void updateRouteInformation(Map<String, Integer> counters) {

    }

    @Override
    public void onListItemClick(String address) {
        view.showAddressDetails(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        view.navigateToDestination(address);
    }

    private void onReadyToBeBuild(UnOrganizedRoute unOrganizedRoute) {
        if(unOrganizedRoute != null){
            this.unOrganizedRoute = unOrganizedRoute;
            setupRouteInformation();
            view.setupFragments(unOrganizedRoute);
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

    @Override
    public void onApiResponse(ApiResponse apiResponse) {

//        If the server has an error and sends back a apiResponse with a html page
//        the response processing will fail! FIX THIS!!!

        if(apiResponse.getSingleDrive() != null){
            this.organizedRoute.getRouteList().add(apiResponse.getSingleDrive());
            view.passSingleDrive(this.organizedRoute);
            return;
        }

        int routeState = apiResponse.getRouteState();

        switch (routeState) {
            case 5 : onReadyToBeBuild(apiResponse.getUnOrganizedRoute());
                break;
            case 8 : onRouteOrganized(apiResponse.getOrganizedRoute());
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
