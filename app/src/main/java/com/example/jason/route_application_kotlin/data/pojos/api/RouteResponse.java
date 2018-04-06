package com.example.jason.route_application_kotlin.data.pojos.api;

import java.util.List;

/**
 * Created by Jason on 06-Apr-18.
 */

public class RouteResponse {

    private int routeState;
    private UnOrganizedRoute unOrganizedRoute;
    private OrganizedRoute organizedRoute;
    private List<String> invalidAddresses;

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public UnOrganizedRoute getUnOrganizedRoute() {
        return unOrganizedRoute;
    }

    public void setUnOrganizedRoute(UnOrganizedRoute unOrganizedRoute) {
        this.unOrganizedRoute = unOrganizedRoute;
    }

    public OrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }

    public List<String> getInvalidAddresses() {
        return invalidAddresses;
    }

    public void setInvalidAddresses(List<String> invalidAddresses) {
        this.invalidAddresses = invalidAddresses;
    }

}
