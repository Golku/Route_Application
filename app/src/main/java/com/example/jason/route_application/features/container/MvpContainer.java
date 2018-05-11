package com.example.jason.route_application.features.container;

import com.example.jason.route_application.data.api.ApiCallback;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.AddressRequest;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.data.pojos.FragmentEvent;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpContainer {

    interface View{

        void setupFragments(RouteInfoHolder routeInfoHolder);

        void updateDeliveryCompletion(int[] deliveryCompletion);

        void updateRouteEndTimeTv(String endTime);

        void sendActivityEvent(ActivityEvent activityEvent);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void setSession(Session session);

        void getContainer();

        void fragmentEvent(FragmentEvent fragmentEvent);

        void delegateActivityEvent(ActivityEvent activityEvent);
    }

    interface Interactor{

        void containerRequest(String username, ApiCallback.ContainerResponseCallback callback);

        void addressRequest(AddressRequest request, ApiCallback.AddAddressCallback callback);

        void driveRequest(DriveRequest request, ApiCallback.DriveResponseCallback callback);
    }
}
