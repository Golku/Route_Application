package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.UnOrganizedRoute;

import com.google.android.gms.maps.model.Marker;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class RouteMapPresenter implements MvpRouteMap.Presenter {

    private final String logTag = "logTagDebug";

    private MvpRouteMap.View view;

    private List<Marker> routeOrder;

    private Marker previousSelectedMarker;

    RouteMapPresenter(MvpRouteMap.View view) {
        this.view = view;
        this.routeOrder = new ArrayList<>();
        this.previousSelectedMarker = null;
    }

    @Override
    public void setMarkers(List<FormattedAddress> addressList) {
        view.addMarkersToMap(addressList);
    }

    @Override
    public void processMarker(Marker clickedMarker) {

        if(clickedMarker.getTag() != null) {
            if (clickedMarker.getTag().equals("origin")) {
                view.showToast("origin");
                return;
            }
        }

        SingleDriveRequest request = new SingleDriveRequest();

        String origin = null;
        String destination = null;

        if (previousSelectedMarker != null) {
            if (clickedMarker.equals(previousSelectedMarker)) {
                clickedMarker.setTag(true);
                routeOrder.remove(clickedMarker);

                int routeSize = routeOrder.size();

                if (routeSize > 0) {
                    int lastMarkerIndex = routeSize - 1;
                    previousSelectedMarker = routeOrder.get(lastMarkerIndex);
                } else {
                    previousSelectedMarker = null;
                }
//                change the clickedMarker icon to the unselected icon
//                clickedMarker.setIcon();
            } else {
                boolean newMarker = (boolean) clickedMarker.getTag();

                if (newMarker) {
                    origin = previousSelectedMarker.getTitle();
                    destination = clickedMarker.getTitle();
                    clickedMarker.setTag(false);
                    routeOrder.add(clickedMarker);
                    previousSelectedMarker = clickedMarker;
                }else{
//                    ask the user if he wants to remove all after this one.
                }
            }
        } else {
            //use phone location for origin.
            origin = "Vrij-Harnasch 21, Den Hoorn";
            destination = clickedMarker.getTitle();
            clickedMarker.setTag(false);
            routeOrder.add(clickedMarker);
            previousSelectedMarker = clickedMarker;
        }

        if(origin != null && destination != null) {
            request.setOrigin(origin);
            request.setDestination(destination);
            view.getDriveInformation(request);
        }
    }
}
