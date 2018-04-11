package com.example.jason.route_application_kotlin.data.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason on 07-Feb-18.
 */

public class FormattedAddress implements Parcelable {

    private String rawAddress;
    private String formattedAddress;
    private String street;
    private String postCode;
    private String city;
    private String country;
    private double lat;
    private double lng;
    private boolean isBusiness;

    public FormattedAddress() {
    }

    protected FormattedAddress(Parcel in) {
        rawAddress = in.readString();
        formattedAddress = in.readString();
        street = in.readString();
        postCode = in.readString();
        city = in.readString();
        country = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        isBusiness = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rawAddress);
        dest.writeString(formattedAddress);
        dest.writeString(street);
        dest.writeString(postCode);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeByte((byte) (isBusiness ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FormattedAddress> CREATOR = new Creator<FormattedAddress>() {
        @Override
        public FormattedAddress createFromParcel(Parcel in) {
            return new FormattedAddress(in);
        }

        @Override
        public FormattedAddress[] newArray(int size) {
            return new FormattedAddress[size];
        }
    };

    public String getRawAddress() {
        return rawAddress;
    }

    public void setRawAddress(String rawAddress) {
        this.rawAddress = rawAddress;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(boolean business) {
        isBusiness = business;
    }
}
