package com.example.jason.route_application_kotlin.data.pojos;

import java.util.List;

/**
 * Created by Jason on 16-Feb-18.
 */

public class OrganizedRoute {

    private String routeCode;
    private int privateAddressesCount;
    private int businessAddressesCount;
    private int wrongAddressesCount;
    private List<SingleDrive> routeList;

    public OrganizedRoute(String routeCode, int privateAddressesCount, int businessAddressesCount, int wrongAddressesCount, List<SingleDrive> routeList) {
        this.routeCode = routeCode;
        this.privateAddressesCount = privateAddressesCount;
        this.businessAddressesCount = businessAddressesCount;
        this.wrongAddressesCount = wrongAddressesCount;
        this.routeList = routeList;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public int getPrivateAddressesCount() {
        return privateAddressesCount;
    }

    public void setPrivateAddressesCount(int privateAddressesCount) {
        this.privateAddressesCount = privateAddressesCount;
    }

    public int getBusinessAddressesCount() {
        return businessAddressesCount;
    }

    public void setBusinessAddressesCount(int businessAddressesCount) {
        this.businessAddressesCount = businessAddressesCount;
    }

    public int getWrongAddressesCount() {
        return wrongAddressesCount;
    }

    public void setWrongAddressesCount(int wrongAddressesCount) {
        this.wrongAddressesCount = wrongAddressesCount;
    }

    public List<SingleDrive> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<SingleDrive> routeList) {
        this.routeList = routeList;
    }

}
