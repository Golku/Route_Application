package com.example.jason.route_application_kotlin.data.pojos;

import java.util.ArrayList;

/**
 * Created by Jason on 14-Feb-18.
 */

public class ApiResponse {

    private boolean organizingInProgress;
    private boolean routeHasInvalidAddresses;
    private boolean routeIsNull;
    private OrganizedRoute organizedRoute;
    private ArrayList<String> invalidAddresses;

    public boolean getOrganizingInProgress() {
        return organizingInProgress;
    }

    public void setOrganizingInProgress(boolean organizingInProgress) {
        this.organizingInProgress = organizingInProgress;
    }

    public boolean getRouteHasInvalidAddresses() {
        return routeHasInvalidAddresses;
    }

    public void setRouteHasInvalidAddresses(boolean routeHasInvalidAddresses) {
        this.routeHasInvalidAddresses = routeHasInvalidAddresses;
    }

    public boolean getRouteIsNull() {
        return routeIsNull;
    }

    public void setRouteIsNull(boolean routeIsNull) {
        this.routeIsNull = routeIsNull;
    }

    public OrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }

    public ArrayList<String> getInvalidAddresses() {
        return invalidAddresses;
    }

    public void setInvalidAddresses(ArrayList<String> invalidAddresses) {
        this.invalidAddresses = invalidAddresses;
    }
}

