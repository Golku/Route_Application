package com.example.jason.route_application.data.pojos.api;

import com.example.jason.route_application.data.pojos.FormattedAddress;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Route{

    private String username;
    private String routeCode;
    private int routeState;
    private List<FormattedAddress> addressList;
    private int privateAddressCount;
    private int businessAddressCount;
    private int invalidAddressCount;
    private List<SingleDrive> routeList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public List<FormattedAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<FormattedAddress> addressList) {
        this.addressList = addressList;
    }

    public int getPrivateAddressCount() {
        return privateAddressCount;
    }

    public void setPrivateAddressCount(int privateAddressCount) {
        this.privateAddressCount = privateAddressCount;
    }

    public int getBusinessAddressCount() {
        return businessAddressCount;
    }

    public void setBusinessAddressCount(int businessAddressCount) {
        this.businessAddressCount = businessAddressCount;
    }

    public int getInvalidAddressCount() {
        return invalidAddressCount;
    }

    public void setInvalidAddressCount(int invalidAddressCount) {
        this.invalidAddressCount = invalidAddressCount;
    }

    public List<SingleDrive> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<SingleDrive> routeList) {
        this.routeList = routeList;
    }
}
