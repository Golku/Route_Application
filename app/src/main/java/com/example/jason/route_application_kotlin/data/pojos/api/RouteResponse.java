package com.example.jason.route_application_kotlin.data.pojos.api;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 06-Apr-18.
 */

public class RouteResponse {

    private int routeState;
    private ArrayList<FormattedAddress> addressList;
    private ArrayList<SingleDrive> routeList;
    private List<String> invalidAddresses;

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public ArrayList<FormattedAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<FormattedAddress> addressList) {
        this.addressList = addressList;
    }

    public ArrayList<SingleDrive> getRouteList() {
        return routeList;
    }

    public void setRouteList(ArrayList<SingleDrive> routeList) {
        this.routeList = routeList;
    }

    public List<String> getInvalidAddresses() {
        return invalidAddresses;
    }

    public void setInvalidAddresses(List<String> invalidAddresses) {
        this.invalidAddresses = invalidAddresses;
    }
}
