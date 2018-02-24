package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import java.util.ArrayList;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRoute {

    interface View{

        void showInvalidAddresses(ArrayList<String> invalidAddresses);

        void setUpAdapter(OrganizedRoute organizedRoute);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void onStartNetworkOperation();

        void onFinishNetworkOperation();

        void showToast(String message);
    }

    interface Presenter{

        void setRouteCode(String routeCode);

        void getRouteFromApi();

        void sendRouteToApi(OutGoingRoute outGoingRoute);

        void onListItemClick(String address);

        void onGoButtonClick(String address);

    }

    interface Interactor{
        void getOrganizedRouteFromApi(ApiPresenterCallBack apiPresenterCallBack, String routeCode);
        void submitRouteForOrganizing(ApiPresenterCallBack apiPresenterCallBack, OutGoingRoute outGoingRoute);
    }

}
