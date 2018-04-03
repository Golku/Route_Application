package com.example.jason.route_application_kotlin.data.models;

import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;

/**
 * Created by Jason on 3/25/2018.
 */

public class FragmentCommunication {

    private OrganizedRoute organizedRoute;

    public FragmentCommunication(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }

    public OrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }
}
