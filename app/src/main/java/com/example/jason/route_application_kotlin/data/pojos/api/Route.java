package com.example.jason.route_application_kotlin.data.pojos.api;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Route implements Parcelable{

    private String routeCode;
    private List<FormattedAddress> addressList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<SingleDrive> routeList;

    protected Route(Parcel in) {
        routeCode = in.readString();
        addressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        privateAddressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        businessAddressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        routeList = in.createTypedArrayList(SingleDrive.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(routeCode);
        dest.writeTypedList(addressList);
        dest.writeTypedList(privateAddressList);
        dest.writeTypedList(businessAddressList);
        dest.writeTypedList(routeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public List<FormattedAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<FormattedAddress> addressList) {
        this.addressList = addressList;
    }

    public List<FormattedAddress> getPrivateAddressList() {
        return privateAddressList;
    }

    public void setPrivateAddressList(List<FormattedAddress> privateAddressList) {
        this.privateAddressList = privateAddressList;
    }

    public List<FormattedAddress> getBusinessAddressList() {
        return businessAddressList;
    }

    public void setBusinessAddressList(List<FormattedAddress> businessAddressList) {
        this.businessAddressList = businessAddressList;
    }

    public List<SingleDrive> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<SingleDrive> routeList) {
        this.routeList = routeList;
    }
}
