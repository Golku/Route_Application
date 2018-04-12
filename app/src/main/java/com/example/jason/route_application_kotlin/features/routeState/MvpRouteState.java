package com.example.jason.route_application_kotlin.features.routeState;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.api.Route;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteRequest;

/**
 * Created by Jason on 3/15/2018.
 */

public interface MvpRouteState {

    interface View{

        void updateRouteStateTextView(String routeState);

        void startCorrectInvalidAddressesActivity(String routeCode);

        void startRoute(Route route);

        void showToast(String message);

        void closeActivity();

    }

    interface Presenter{

        void setRouteCode(String routeCode);

        void submitRoute(RouteRequest routeRequest);

        void getRouteState();

    }

    interface Interactor{

        void sendRoute(ApiCallback.RouteResponseCallback callback , RouteRequest routeRequest);

        void getRouteState(ApiCallback.RouteResponseCallback callback, String routeCode);

    }

}
