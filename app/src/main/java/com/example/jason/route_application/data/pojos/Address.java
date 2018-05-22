package com.example.jason.route_application.data.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason on 07-Feb-18.
 */

public class Address implements Parcelable {

    private String address;
    private String street;
    private String postCode;
    private String city;
    private String country;
    private double lat;
    private double lng;
    private int openingHours;
    private int packageCount;
    private boolean business;
    private boolean userInputted;
    private boolean userLocation;
    private boolean valid;

    public Address(){

    }

    protected Address(Parcel in) {
        address = in.readString();
        street = in.readString();
        postCode = in.readString();
        city = in.readString();
        country = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        openingHours = in.readInt();
        packageCount = in.readInt();
        business = in.readByte() != 0;
        userInputted = in.readByte() != 0;
        userLocation = in.readByte() != 0;
        valid = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(street);
        dest.writeString(postCode);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeInt(openingHours);
        dest.writeInt(packageCount);
        dest.writeByte((byte) (business ? 1 : 0));
        dest.writeByte((byte) (userInputted ? 1 : 0));
        dest.writeByte((byte) (userLocation ? 1 : 0));
        dest.writeByte((byte) (valid ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(int openingHours) {
        this.openingHours = openingHours;
    }

    public int getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }

    public boolean isBusiness() {
        return business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public boolean isUserInputted() {
        return userInputted;
    }

    public void setUserInputted(boolean userInputted) {
        this.userInputted = userInputted;
    }

    public boolean isUserLocation() {
        return userLocation;
    }

    public void setUserLocation(boolean userLocation) {
        this.userLocation = userLocation;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
