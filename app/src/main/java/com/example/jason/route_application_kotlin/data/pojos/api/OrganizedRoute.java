package com.example.jason.route_application_kotlin.data.pojos.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jason on 16-Feb-18.
 */

public class OrganizedRoute implements Parcelable{

    private String routeCode;
    private int privateAddressesCount;
    private int businessAddressesCount;
    private int wrongAddressesCount;
    private List<SingleDrive> routeList;

    public OrganizedRoute() {
    }

    private OrganizedRoute(Parcel in) {
        routeCode = in.readString();
        privateAddressesCount = in.readInt();
        businessAddressesCount = in.readInt();
        wrongAddressesCount = in.readInt();
    }

    public static final Creator<OrganizedRoute> CREATOR = new Creator<OrganizedRoute>() {
        @Override
        public OrganizedRoute createFromParcel(Parcel in) {
            return new OrganizedRoute(in);
        }

        @Override
        public OrganizedRoute[] newArray(int size) {
            return new OrganizedRoute[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(routeCode);
        parcel.writeInt(privateAddressesCount);
        parcel.writeInt(businessAddressesCount);
        parcel.writeInt(wrongAddressesCount);
    }
}
