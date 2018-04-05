package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.data.pojos.DriveInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import com.google.android.gms.maps.model.Marker;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class RouteMapPresenter implements MvpRouteMap.Presenter {

    private final String logTag = "logTagDebug";

    private MvpRouteMap.View view;

    private UnOrganizedRoute unOrganizedRoute;

    private List<Marker> routeOrder;

    private Marker previousSelectedMarker;

    private Marker originMarker;

    RouteMapPresenter(MvpRouteMap.View view) {
        this.view = view;
        this.routeOrder = new ArrayList<>();
        this.previousSelectedMarker = null;
    }

    @Override
    public void setRoute(UnOrganizedRoute unOrganizedRoute) {
        this.unOrganizedRoute = unOrganizedRoute;
    }

    @Override
    public List<FormattedAddress> getAddressesList() {
        return this.unOrganizedRoute.getValidAddressesList();
    }

    @Override
    public void orderMaker(Marker clickedMarker) {

        DriveInformationRequest request = new DriveInformationRequest();

        String origin = null;
        String destination = null;

        if (previousSelectedMarker != null) {

            if (clickedMarker.equals(previousSelectedMarker)) {

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

                boolean newMarker = true;

                int index = routeOrder.indexOf(clickedMarker);

                if(index>=0){
                    newMarker = false;
                }

                for (Marker orderedMarker : routeOrder) {
                    if (orderedMarker.getId().equals(clickedMarker.getId())) {
                        newMarker = false;
                        //remove all markers in the list after this one
                    }
                }

                if (newMarker) {
                    origin = previousSelectedMarker.getTitle();
                    destination = clickedMarker.getTitle();
                    routeOrder.add(clickedMarker);
                    previousSelectedMarker = clickedMarker;
                }

            }

        } else {
            //use phone location for origin.
            origin = "Vrij-Harnasch 21, Den Hoorn";
            destination = clickedMarker.getTitle();
            routeOrder.add(clickedMarker);
            previousSelectedMarker = clickedMarker;
        }

        request.setOrigin(origin);
        request.setDestination(destination);

        view.getDriveInformation(request);
    }
}
