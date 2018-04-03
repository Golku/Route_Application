package com.example.jason.route_application_kotlin.data.pojos;

import java.util.ArrayList;

/**
 * Created by Jason on 14-Feb-18.
 */


public class ApiResponse {

    private String requestType;
    private int routeState;
    private SingleDrive singleDrive;
    private UnOrganizedRoute unOrganizedRoute;
    private OrganizedRoute organizedRoute;
    private ArrayList<String> invalidAddresses;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public SingleDrive getSingleDrive() {
        return singleDrive;
    }

    public void setSingleDrive(SingleDrive singleDrive) {
        this.singleDrive = singleDrive;
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

    public ArrayList<String> getInvalidAddresses() {
        return invalidAddresses;
    }

    public void setInvalidAddresses(ArrayList<String> invalidAddresses) {
        this.invalidAddresses = invalidAddresses;
    }
}
