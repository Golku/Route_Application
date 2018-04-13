package com.example.jason.route_application_kotlin.data.pojos;

import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;
import com.google.android.gms.maps.model.LatLng;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RouteInfoHolder implements Parcelable{

    private boolean organized;
    private LatLng userLocation;
    private List<FormattedAddress> addressList;
    private List<SingleDrive> routeList;

    public RouteInfoHolder() {
    }

    private RouteInfoHolder(Parcel in) {
        organized = in.readByte() != 0;
        userLocation = in.readParcelable(LatLng.class.getClassLoader());
        addressList = in.createTypedArrayList(FormattedAddress.CREATOR);
        routeList = in.createTypedArrayList(SingleDrive.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (organized ? 1 : 0));
        dest.writeParcelable(userLocation, flags);
        dest.writeTypedList(addressList);
        dest.writeTypedList(routeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouteInfoHolder> CREATOR = new Creator<RouteInfoHolder>() {
        @Override
        public RouteInfoHolder createFromParcel(Parcel in) {
            return new RouteInfoHolder(in);
        }

        @Override
        public RouteInfoHolder[] newArray(int size) {
            return new RouteInfoHolder[size];
        }
    };

    public boolean isOrganized() {
        return organized;
    }

    public void setOrganized(boolean organized) {
        this.organized = organized;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LatLng userLocation) {
        this.userLocation = userLocation;
    }

    public List<FormattedAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<FormattedAddress> addressList) {
        this.addressList = addressList;
    }

    public List<SingleDrive> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<SingleDrive> routeList) {
        this.routeList = routeList;
    }
}
