package com.example.jason.route_application_kotlin.data.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/26/2018.
 */

public class UnOrganizedRoute implements Parcelable{

    private String routeCode;
    private String origin;
    private int routeState;
    private ArrayList<String> addressList;

    private List<FormattedAddress> validAddressesList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<FormattedAddress> invalidAddressesList;

    protected UnOrganizedRoute(Parcel in) {
        routeCode = in.readString();
        origin = in.readString();
        routeState = in.readInt();
        addressList = in.createStringArrayList();
        validAddressesList = in.createTypedArrayList(FormattedAddress.CREATOR);
        privateAddressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        businessAddressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        invalidAddressesList = in.createTypedArrayList(FormattedAddress.CREATOR);
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

    public ArrayList<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<String> addressList) {
        this.addressList = addressList;
    }

    public List<FormattedAddress> getValidAddressesList() {
        return validAddressesList;
    }

    public void setValidAddressesList(List<FormattedAddress> allValidAddressesList) {
        this.validAddressesList = allValidAddressesList;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(routeCode);
        parcel.writeString(origin);
        parcel.writeInt(routeState);
        parcel.writeStringList(addressList);
        parcel.writeTypedList(validAddressesList);
        parcel.writeTypedList(privateAddressList);
        parcel.writeTypedList(businessAddressList);
        parcel.writeTypedList(invalidAddressesList);
    }
}
