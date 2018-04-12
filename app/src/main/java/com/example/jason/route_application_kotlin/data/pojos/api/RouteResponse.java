package com.example.jason.route_application_kotlin.data.pojos.api;

import java.util.List;

/**
 * Created by Jason on 06-Apr-18.
 */

public class RouteResponse {

    private int routeState;
    private Route route;
    private List<String> invalidAddresses;

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<String> getInvalidAddresses() {
        return invalidAddresses;
    }

    public void setInvalidAddresses(List<String> invalidAddresses) {
        this.invalidAddresses = invalidAddresses;
    }
}
