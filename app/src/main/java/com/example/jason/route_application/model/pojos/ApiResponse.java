package com.example.jason.route_application.model.pojos;


public class ApiResponse {

    private boolean organizingRoute;
    private SingleOrganizedRoute organizedRoute;

    public boolean isOrganizingRoute() {
        return organizingRoute;
    }

    public void setOrganizingRoute(boolean organizingRoute) {
        this.organizingRoute = organizingRoute;
    }

    public SingleOrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(SingleOrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }
}
