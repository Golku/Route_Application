package com.example.jason.route_application.data.api;

import com.example.jason.route_application.data.pojos.Address;
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

    interface DriveResponseCallback {
        void onSingleDriveResponse(Drive response);
        void onSingleDriveResponseFailure();
    }

    interface AddAddressCallback {
        void onAddAddressResponse(Address response);
        void onAddAddressResponseFailure();
    }

}
