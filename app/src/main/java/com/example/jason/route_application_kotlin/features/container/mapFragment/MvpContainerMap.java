package com.example.jason.route_application_kotlin.features.container.mapFragment;

import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public interface MvpContainerMap {

    interface View{

        void addMarkersToMap(List<FormattedAddress> addresses);

        void getDriveInformation(SingleDriveRequest request);

        void getPolylineToMarker(LatLng start, LatLng end);

        void deselectMarker();

        void deselectMultipleMarker(String destination);

        void changeMarkerIcon(Marker marker);

        void showSnackBar(int markerPosition);

        void removePolyLine();

        void showToast(String message);

    }

    interface Presenter{

        void setMarkers();

        void multipleMarkersDeselected(int markerPosition);

        void processMarker(Marker marker);
    }

}
