package com.example.jason.route_application_kotlin.data.pojos.database;

public class AddressInformationResponse {

    private boolean informationAvailable;
    private AddressInformation addressInformation;

    public boolean isInformationAvailable() {
        return informationAvailable;
    }

    public void setInformationAvailable(boolean informationAvailable) {
        this.informationAvailable = informationAvailable;
    }

    public AddressInformation getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }
}
