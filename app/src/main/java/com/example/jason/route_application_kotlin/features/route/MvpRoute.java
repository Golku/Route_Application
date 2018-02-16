package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.PresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRoute {

    interface View{

        void setUpAdapter(OrganizedRoute organizedRoute);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

    }

    interface Presenter{

        void sendRouteToApi(OutGoingRoute outGoingRoute);

        void onListItemClick(String address);

        void onGoButtonClick(String address);

    }

    interface Interactor{
        void submitRouteForOrganizing(PresenterCallBack presenterCallBack, OutGoingRoute outGoingRoute);
    }

}
