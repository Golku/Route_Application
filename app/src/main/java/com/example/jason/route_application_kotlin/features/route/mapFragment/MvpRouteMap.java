package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public interface MvpRouteMap {

    interface View{

        void addMarkersToMap(List<FormattedAddress> addresses);

        void getDriveInformation(SingleDriveRequest request);

        void getPolylineToMarker(LatLng start, LatLng end);

        void removeAddress(String destination);

        void removePolyLine();

        void showSnackBar(String destination);

        void showToast(String message);

    }

    interface Presenter{

        void setMarkers(List<FormattedAddress> addressList);

        void processMarker(Marker marker);

    }

}
