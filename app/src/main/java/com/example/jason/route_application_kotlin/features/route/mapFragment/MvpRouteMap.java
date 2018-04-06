package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.google.android.gms.maps.model.Marker;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.UnOrganizedRoute;

import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public interface MvpRouteMap {

    interface View{
        void showToast(String message);
        void getDriveInformation(SingleDriveRequest request);
    }

    interface Presenter{
        void setRoute(UnOrganizedRoute unOrganizedRoute);
        List<FormattedAddress> getAddressesList();
        void orderMaker(Marker marker);
    }

}
