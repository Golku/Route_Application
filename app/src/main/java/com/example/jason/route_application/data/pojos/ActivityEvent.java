package com.example.jason.route_application.data.pojos;

import com.example.jason.route_application.data.pojos.api.Drive;

import java.util.List;

public class ActivityEvent {

    private String event;
    private int fragment;
    private Address address;
    private int position;
    private List<Address> addressList;
    private List<Drive> driveList;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getFragment() {
        return fragment;
    }

    public void setFragment(int fragment) {
        this.fragment = fragment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Drive> getDriveList() {
        return driveList;
    }

    public void setDriveList(List<Drive> driveList) {
        this.driveList = driveList;
    }
}
