package com.example.jason.route_application_kotlin.data.pojos.api;

import java.util.List;

public class Container {

    private Route route;
    private List<String> userAddresses;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<String> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<String> userAddresses) {
        this.userAddresses = userAddresses;
    }
}
