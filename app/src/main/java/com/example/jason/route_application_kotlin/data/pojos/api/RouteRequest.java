package com.example.jason.route_application_kotlin.data.pojos.api;

import java.util.List;

/**
 * Created by Jason on 14-Feb-18.
 */

public class RouteRequest {

    private String username;
    private List<String> addressList;

    public RouteRequest(String username, List<String> addressList) {
        this.username = username;
        this.addressList = addressList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }
}
