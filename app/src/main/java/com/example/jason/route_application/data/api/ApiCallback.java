package com.example.jason.route_application.data.api;

import com.example.jason.route_application.data.pojos.api.Container;
import com.example.jason.route_application.data.pojos.api.Route;
import com.example.jason.route_application.data.pojos.api.Drive;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface ApiCallback {

    interface ContainerResponseCallback {
        void onContainerResponse(Container response);
        void onContainerResponseFailure();
    }

    interface RouteResponseCallback{
        void onRouteResponse(Route response);
        void onRouteResponseFailure();
    }

    interface RouteSubmitCallback{
        void onRouteSubmitResponse(boolean submitted);
        void onRouteSubmitResponseFailure();
    }

    interface DriveResponseCallback {
        void onSingleDriveResponse(Drive response);
        void onSingleDriveResponseFailure();
    }


}
