package com.example.jason.route_application_kotlin.data.pojos;

/**
 * Created by Jason on 19-Feb-18.
 */

public class DatabaseResponse {

    private AddressInformation addressInformation;
    private boolean error;
    private String errorMessage;

    public AddressInformation getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
