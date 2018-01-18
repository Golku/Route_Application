package com.example.jason.route_application.model.pojos;

import java.util.ArrayList;
import java.util.List;

public class OutGoingRoute {

    private String routeCode;
    private String origin;
    private List<String> addressList = new ArrayList<>();

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
