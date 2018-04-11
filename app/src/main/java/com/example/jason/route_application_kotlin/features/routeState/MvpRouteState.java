package com.example.jason.route_application_kotlin.features.routeState;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.api.OutGoingRoute;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/15/2018.
 */

public interface MvpRouteState {

    interface View{

        void updateRouteStateTextView(String routeState);

        void startCorrectInvalidAddressesActivity(String routeCode);

        void startRouteUnorganized(String routeCode, ArrayList<FormattedAddress> addressList);

        void startRouteOrganized(String routeCode, ArrayList<SingleDrive> routeList);

        void showToast(String message);

        void closeActivity();

    }

    interface Presenter{

        void setRouteCode(String routeCode);

        void submitRoute(OutGoingRoute outGoingRoute);

        void getRouteState();

    }

    interface Interactor{

        void sendRoute(ApiCallback.RouteResponseCallback callback , OutGoingRoute outGoingRoute);

        void getRouteState(ApiCallback.RouteResponseCallback callback, String routeCode);

    }

}
