package com.example.jason.route_application.features.container;

import com.example.jason.route_application.data.api.ApiCallback;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.FragmentDelegation;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.AddressRequest;
import com.example.jason.route_application.data.pojos.api.DriveRequest;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpContainer {

    interface View{

        void setupFragments(RouteInfoHolder routeInfoHolder);

        void updateDeliveryCompletion(int[] deliveryCompletion);

        void updateRouteEndTimeTv(String endTime);

        void delegateRouteChange(FragmentDelegation delegation);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void getContainer(Session session);

        void getRoute();

        void getAddress(String address);

        void getDrive(DriveRequest request);

        void removeAddress(Address address);

        void removeDrive();

        void removeMultipleDrive(String destination);

        void onListItemClick(String address);

        void onGoButtonClick(String address);
    }

    interface Interactor{

        void containerRequest(String username, ApiCallback.ContainerResponseCallback callback);

        void routeRequest(String username, ApiCallback.RouteResponseCallback callback);

        void addressRequest(AddressRequest request, ApiCallback.AddAddressCallback callback);

        void driveRequest(DriveRequest request, ApiCallback.DriveResponseCallback callback);
    }

}
