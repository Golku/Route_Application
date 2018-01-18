package com.example.jason.route_application.model.pojos;

public class DatabaseResponse {

    private SingleAddressDbInformation singleAddressDbInformation;
    private boolean error;
    private String errorMessage;

    public SingleAddressDbInformation getSingleAddressDbInformation() {
        return singleAddressDbInformation;
    }

    public void setSingleAddressDbInformation(SingleAddressDbInformation singleAddressDbInformation) {
        this.singleAddressDbInformation = singleAddressDbInformation;
    }

    public boolean getError() {
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
