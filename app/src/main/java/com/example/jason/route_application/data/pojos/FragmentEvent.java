package com.example.jason.route_application.data.pojos;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.api.AddressChangeRequest;
import com.example.jason.route_application.data.pojos.api.DriveRequest;

public class FragmentEvent {

    private String event;
    private String addressString;
    private Address address;
    private AddressChangeRequest addressChangeRequest;
    private DriveRequest driveRequest;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AddressChangeRequest getAddressChangeRequest() {
        return addressChangeRequest;
    }

    public void setAddressChangeRequest(AddressChangeRequest addressChangeRequest) {
        this.addressChangeRequest = addressChangeRequest;
    }

    public DriveRequest getDriveRequest() {
        return driveRequest;
    }

    public void setDriveRequest(DriveRequest driveRequest) {
        this.driveRequest = driveRequest;
    }
}
