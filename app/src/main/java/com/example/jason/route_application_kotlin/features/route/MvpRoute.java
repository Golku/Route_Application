package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.UnOrganizedRoute;

import java.util.List;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRoute {

    interface View{

        void setupAddressTracker(int privateAddress, int businessAddress);

        void updateAddressTracker(int privateAddress, int businessAddress);

        void delegateUnorganizedRoute(List<FormattedAddress> addressList);

        void addAddressToRouteList(SingleDrive singleDrive);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void setRouteCode(String routeCode);

        void onMapReady();

        void getRouteFromApi();

        void getDriveInformation(SingleDriveRequest request);

        void onListItemClick(String address);

        void onGoButtonClick(String address);

    }

    interface Interactor{

        void getRoute(ApiCallback.RouteResponseCallback callback, String routeCode);

        void getDriveInformation(ApiCallback.SingleDriveResponseCallback callback, SingleDriveRequest request);

    }

}
