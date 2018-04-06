package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.google.android.gms.maps.model.Marker;

import com.example.jason.route_application_kotlin.data.pojos.DriveInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;

import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public interface MvpRouteMap {

    interface View{
        void showToast(String message);
        void getDriveInformation(DriveInformationRequest request);
    }

    interface Presenter{
        void setRoute(UnOrganizedRoute unOrganizedRoute);
        List<FormattedAddress> getAddressesList();
        void orderMaker(Marker marker);
    }

}
