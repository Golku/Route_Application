package com.example.jason.route_application.features.container;

import com.example.jason.route_application.data.api.ApiCallback;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.DriveRequest;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpContainer {

    interface View{

        void setupFragments(RouteInfoHolder routeInfoHolder);

        void updateDeliveryCompletion(int[] deliveryCompletion);

        void updateRouteEndTime(String endTime);

        void delegateRouteChange(RouteListFragmentDelegation delegation);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void getContainer(Session session);

        void getRoute();

        void getDriveInformation(DriveRequest request);

        void markerDeselected();

        void multipleMarkersDeselected(String destination);

        void onUpdateDeliveryCompletion(int[] deliveryCompletion);

        void onListItemClick(String address);

        void onGoButtonClick(String address);
    }

    interface Interactor{

        void getContainer(String username, ApiCallback.ContainerResponseCallback callback);

        void getRoute(String username, ApiCallback.RouteResponseCallback callback);

        void getDriveInformation(DriveRequest request, ApiCallback.DriveResponseCallback callback);
    }

}
