package com.example.jason.route_application_kotlin.features.route.listFragment;

import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public class RouteListPresenter implements MvpRouteList.Presenter{

    private MvpRouteList.View view;

    RouteListPresenter(MvpRouteList.View view) {
        this.view = view;
    }

    @Override
    public void initializeAdapter(List<SingleDrive> routeList) {
        view.setupAdapter(routeList);
    }

    @Override
    public void onDelegation(RouteListFragmentDelegation delegation) {
        String operation = delegation.getOperation();
        int position = delegation.getPosition();

        switch (operation) {
            case "add" : addDrive(position);
                break;
            case "remove" : removeDrive(position);
                break;
        }
    }

    private void addDrive(int position){
        view.addDriveToList(position);
    }

    private void removeDrive(int position){
        view.removeDriveFromList(position);
    }
}
