package com.example.jason.route_application_kotlin.data.pojos;

/**
 * Created by Jason on 3/26/2018.
 */

public class TravelInformationRequest {

    private String origin;
    private String destination;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
