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

        void addMarkersToMap(List<FormattedAddress> addresses);

        void getDriveInformation(SingleDriveRequest request);

        void showToast(String message);

    }

    interface Presenter{

        void setMarkers(UnOrganizedRoute unOrganizedRoute);

        void processMarker(Marker marker);

    }

}
