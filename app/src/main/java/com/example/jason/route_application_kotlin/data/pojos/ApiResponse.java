package com.example.jason.route_application_kotlin.data.pojos;

/**
 * Created by Jason on 14-Feb-18.
 */

public class ApiResponse {

    private boolean organizingInProgress;
    private OrganizedRoute organizedRoute;

    public boolean isOrganizingInProgress() {
        return organizingInProgress;
    }

    public void setOrganizingInProgress(boolean organizingInProgress) {
        this.organizingInProgress = organizingInProgress;
    }

    public OrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }

}
