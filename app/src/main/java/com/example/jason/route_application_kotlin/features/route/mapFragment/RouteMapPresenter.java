package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class RouteMapPresenter implements MvpRouteMap.Presenter{

    private MvpRouteMap.View view;

    private UnOrganizedRoute unOrganizedRoute;

    private List<Marker> routeOrder;

    RouteMapPresenter(MvpRouteMap.View view) {
        this.view = view;
    }

    @Override
    public void setRoute(UnOrganizedRoute unOrganizedRoute) {
        this.unOrganizedRoute = unOrganizedRoute;
    }

    @Override
    public List<FormattedAddress> getAddressesList() {
        return this.unOrganizedRoute.getValidAddressesList();
    }
}
