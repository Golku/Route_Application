package com.example.jason.route_application_kotlin.data.api;

import com.example.jason.route_application_kotlin.data.pojos.api.RouteResponse;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveResponse;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface ApiCallback {

    interface RouteResponseCallback {
        void onRouteResponse(RouteResponse response);
        void onRouteResponseFailure();
    }

    interface SingleDriveResponseCallback {
        void onSingleDriveResponse(SingleDriveResponse response);
        void onSingleDriveResponseFailure();
    }
}
