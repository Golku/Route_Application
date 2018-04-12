package com.example.jason.route_application_kotlin.data.pojos.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 14-Feb-18.
 */

public class RouteRequest {

    private String routeCode;
    private String origin;
    private List<String> addressList;

    public RouteRequest(String routeCode, String origin, ArrayList<String> addressList) {
        this.routeCode = routeCode;
        this.origin = origin;
        this.addressList = addressList;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }
}
