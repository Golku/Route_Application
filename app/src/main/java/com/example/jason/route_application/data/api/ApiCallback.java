package com.example.jason.route_application.data.api;

import com.example.jason.route_application.data.pojos.api.Container;
import com.example.jason.route_application.data.pojos.api.SingleDrive;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface ApiCallback {

    interface ContainerResponseCallback {
        void onContainerResponse(Container response);
        void onContainerResponseFailure();
    }

    interface RouteSubmitCallback{
        void onRouteSubmitResponse(boolean submitted);
        void onRouteSubmitResponseFailure();
    }

    interface SingleDriveResponseCallback {
        void onSingleDriveResponse(SingleDrive response);
        void onSingleDriveResponseFailure();
    }


}
