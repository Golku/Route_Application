package com.example.jason.route_application_kotlin.features.route.mapFragment;

import android.util.Log;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.MarkerInfo;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class RouteMapPresenter implements MvpRouteMap.Presenter {

    private final String logTag = "logTagDebug";

    private MvpRouteMap.View view;

    private List<FormattedAddress> addressList;

    private List<Marker> routeOrder;

    private Marker previousSelectedMarker;

    RouteMapPresenter(MvpRouteMap.View view, List<FormattedAddress> addressList) {
        this.view = view;
        this.addressList = addressList;
        this.routeOrder = new ArrayList<>();
        this.previousSelectedMarker = null;
    }

    @Override
    public void setMarkers() {
        view.addMarkersToMap(addressList);
    }

    @Override
    public void multipleMarkersDeselected(int markerPosition) {

        String markerDestination = routeOrder.get(markerPosition).getTitle();

        for(int i=markerPosition; i<routeOrder.size(); i++){
            Marker marker = routeOrder.get(i);
            MarkerInfo markerInfo = (MarkerInfo) marker.getTag();

            markerInfo.setSelected(false);

            if(markerInfo.isBusiness()){
                markerInfo.setIconType("business");
            }else{
                markerInfo.setIconType("private");
            }

            view.changeMarkerIcon(marker);
        }

        routeOrder.subList(markerPosition, routeOrder.size()).clear();

        if (routeOrder.size() > 0) {
            previousSelectedMarker = routeOrder.get(routeOrder.size() -1);
        } else {
            previousSelectedMarker = null;
        }
        view.removePolyLine();
        view.deselectMultipleMarker(markerDestination);
    }

    @Override
    public void processMarker(Marker clickedMarker) {

        if(clickedMarker.getTag() != null) {
            if (clickedMarker.getTag().equals("origin")) {
                view.showToast("My location");
                return;
            }
        }

        MarkerInfo markerInfo = (MarkerInfo) clickedMarker.getTag();

        SingleDriveRequest request = new SingleDriveRequest();
        String origin = null;
        String destination = null;

        LatLng start = null;
        LatLng end = null;

        if (previousSelectedMarker != null) {

            if (clickedMarker.equals(previousSelectedMarker)) {
                routeOrder.remove(clickedMarker);

                markerInfo.setSelected(false);

                if(markerInfo.isBusiness()){
                    markerInfo.setIconType("business");
                }else{
                    markerInfo.setIconType("private");
                }

                int routeSize = routeOrder.size();

                if (routeSize > 0) {
                    int lastMarkerIndex = routeSize - 1;
                    previousSelectedMarker = routeOrder.get(lastMarkerIndex);
                } else {
                    previousSelectedMarker = null;
                }

                view.removePolyLine();
                view.changeMarkerIcon(clickedMarker);
                view.deselectMarker();

            } else {

                if (markerInfo.isSelected()) {
                    view.showSnackBar(routeOrder.indexOf(clickedMarker));
                }else{

                    origin = previousSelectedMarker.getTitle();
                    destination = clickedMarker.getTitle();
                    start = previousSelectedMarker.getPosition();
                    end = clickedMarker.getPosition();

                    markerInfo.setSelected(true);
                    markerInfo.setIconType("selected");

                    routeOrder.add(clickedMarker);
                    previousSelectedMarker = clickedMarker;
                    view.changeMarkerIcon(clickedMarker);
                }
            }
        } else {

            //use phone location for origin.
            origin = "Vrij-Harnasch 21, Den Hoorn";
            destination = clickedMarker.getTitle();
            start = new LatLng(52.008234, 4.312999);
            end = clickedMarker.getPosition();

            markerInfo.setSelected(true);
            markerInfo.setIconType("selected");

            routeOrder.add(clickedMarker);
            previousSelectedMarker = clickedMarker;
            view.changeMarkerIcon(clickedMarker);
        }

        if(origin != null && destination != null) {
            request.setOrigin(origin);
            request.setDestination(destination);
            view.getPolylineToMarker(start, end);
            view.getDriveInformation(request);
        }
    }
}
