package com.example.jason.route_application_kotlin.data.api;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface ApiCallback {

    interface ContainerResponseCallback {
        void onContainerResponse(ContainerResponse response);
        void onContainerResponseFailure();
    }

    interface SingleDriveResponseCallback {
        void onSingleDriveResponse(SingleDriveResponse response);
        void onSingleDriveResponseFailure();
    }
}
