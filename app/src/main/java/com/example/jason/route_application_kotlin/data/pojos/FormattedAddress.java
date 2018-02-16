package com.example.jason.route_application_kotlin.data.pojos;

/**
 * Created by Jason on 07-Feb-18.
 */

public class FormattedAddress {

    private String rawAddress;
    private String formattedAddress;
    private String street;
    private String postCode;
    private String city;
    private String country;
    private boolean isBusiness;

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

    public boolean getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(boolean business) {
        isBusiness = business;
    }
}
