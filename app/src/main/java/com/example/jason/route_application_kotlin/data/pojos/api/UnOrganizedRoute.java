package com.example.jason.route_application_kotlin.data.pojos.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import java.util.List;

/**
 * Created by Jason on 3/26/2018.
 */

public class UnOrganizedRoute implements Parcelable{

    private String routeCode;
    private String origin;
    private int routeState;
    private List<String> addressList;

    private List<FormattedAddress> validAddressesList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<FormattedAddress> invalidAddressesList;

    private UnOrganizedRoute(Parcel in) {
        routeCode = in.readString();
        origin = in.readString();
        routeState = in.readInt();
        addressList = in.createStringArrayList();
        validAddressesList = in.createTypedArrayList(FormattedAddress.CREATOR);
        privateAddressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        businessAddressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        invalidAddressesList = in.createTypedArrayList(FormattedAddress.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(routeCode);
        dest.writeString(origin);
        dest.writeInt(routeState);
        dest.writeStringList(addressList);
        dest.writeTypedList(validAddressesList);
        dest.writeTypedList(privateAddressList);
        dest.writeTypedList(businessAddressList);
        dest.writeTypedList(invalidAddressesList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UnOrganizedRoute> CREATOR = new Creator<UnOrganizedRoute>() {
        @Override
        public UnOrganizedRoute createFromParcel(Parcel in) {
            return new UnOrganizedRoute(in);
        }

        @Override
        public UnOrganizedRoute[] newArray(int size) {
            return new UnOrganizedRoute[size];
        }
    };

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

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    public List<FormattedAddress> getValidAddressesList() {
        return validAddressesList;
    }

    public void setValidAddressesList(List<FormattedAddress> validAddressesList) {
        this.validAddressesList = validAddressesList;
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

    public List<FormattedAddress> getInvalidAddressesList() {
        return invalidAddressesList;
    }

    public void setInvalidAddressesList(List<FormattedAddress> invalidAddressesList) {
        this.invalidAddressesList = invalidAddressesList;
    }
}
