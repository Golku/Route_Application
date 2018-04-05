package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.DriveInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;

import java.util.Map;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRoute {

    interface View{

        void setupAddressTracker(int privateAddress, int businessAddress);

        void updateAddressTracker(int privateAddress, int businessAddress);

        void setupFragments(UnOrganizedRoute unOrganizedRoute);

        void passSingleDrive(SingleDrive singleDrive);

        void showAddressDetails(String address);

        void navigateToDestination(String address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void setRouteCode(String routeCode);

        void getRouteFromApi();

        void getDriveInformation(DriveInformationRequest request);

        void onListItemClick(String address);

        void onGoButtonClick(String address);

    }

    interface Interactor{
        void getDriveInformation(ApiPresenterCallBack apiPresenterCallBack, DriveInformationRequest request);
        void getOrganizedRouteFromApi(ApiPresenterCallBack apiPresenterCallBack, String routeCode);
    }

}
