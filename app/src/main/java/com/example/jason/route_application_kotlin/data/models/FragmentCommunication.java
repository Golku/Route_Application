package com.example.jason.route_application_kotlin.data.models;

import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;

/**
 * Created by Jason on 3/25/2018.
 */

public class FragmentCommunication {

    private SingleDrive singleDrive;

    public FragmentCommunication(SingleDrive singleDrive) {
        this.singleDrive = singleDrive;
    }

    public SingleDrive getSingleDrive() {
        return singleDrive;
    }

    public void setSingleDrive(SingleDrive singleDrive) {
        this.singleDrive = singleDrive;
    }
}
