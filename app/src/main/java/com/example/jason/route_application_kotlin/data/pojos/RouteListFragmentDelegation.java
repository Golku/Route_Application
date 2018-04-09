package com.example.jason.route_application_kotlin.data.pojos;

import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;

public class RouteListFragmentDelegation {

    private String operation;
    private String destination;
    private SingleDrive singleDrive;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public SingleDrive getSingleDrive() {
        return singleDrive;
    }

    public void setSingleDrive(SingleDrive singleDrive) {
        this.singleDrive = singleDrive;
    }
}
