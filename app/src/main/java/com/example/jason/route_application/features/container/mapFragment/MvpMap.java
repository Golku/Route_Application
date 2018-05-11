package com.example.jason.route_application.features.container.mapFragment;

import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.FragmentEvent;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public interface MvpMap {

    interface View{

        void markAddressOnMap(Address addresses);

        void removeAllMarkersFromMap();

        void moveMapCamera(double lat, double lng);

        void sendFragmentEvent(FragmentEvent fragmentEvent);

        void getPolylineToMarker(LatLng start, LatLng end);

        void changeMarkerIcon(Marker marker);

        void showSnackBar(int markerPosition);

        void removePolyLine();

        void showToast(String message);
    }

    interface Presenter{

        void setMarkers();

        void markerClick(Marker marker);

        void infoWindowClick(Marker marker);

        void multipleMarkersDeselected(int markerPosition);

        void activityEvent(ActivityEvent activityEvent);
    }

}
