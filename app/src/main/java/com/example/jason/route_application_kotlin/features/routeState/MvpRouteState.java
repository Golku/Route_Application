package com.example.jason.route_application_kotlin.features.routeState;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

/**
 * Created by Jason on 3/15/2018.
 */

public interface MvpRouteState {

    interface View{
        void updateRouteStateTextView(String routeState);
        void startCorrectInvalidAddressesActivity(String routeCode);
        void startRouteActivity(String routeCode);
        void showToast(String message);
        void closeActivity();
    }

    interface Presenter{
        void setRouteCode(String routeCode);
        void submitRoute(OutGoingRoute outGoingRoute);
        void getRouteState();
    }

    interface Interactor{
        void sendRoute(ApiPresenterCallBack apiPresenterCallBack, OutGoingRoute outGoingRoute);
        void getRouteState(ApiPresenterCallBack apiPresenterCallBack, String routeCode);
    }

}
