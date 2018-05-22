package com.example.jason.route_application.features.container.mapFragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.example.jason.route_application.data.pojos.Event;

/**
 * Created by Jason on 3/28/2018.
 */

public interface MvpMap {

    interface View{

        void changeMarkerIcon(Marker marker, String iconName);

        void postEvent(Event event);

        void getPolylineToMarker(LatLng start, LatLng end);

        void showSnackBar(int markerPosition);

        void removePolyLine();

        void showToast(String message);
    }

    interface Presenter{

        void setMapData(GoogleMap googleMap);

        void infoWindowClick(Marker marker);

        void multipleMarkersDeselected(int markerPosition);

        void eventReceived(Event event);
    }

}
