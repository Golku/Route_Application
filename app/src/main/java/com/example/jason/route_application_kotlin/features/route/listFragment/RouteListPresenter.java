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

    private List<SingleDrive> routeList;

    RouteListPresenter(MvpRouteList.View view) {
        this.view = view;
        this.routeList = new ArrayList<>();
    }

    @Override
    public void setupFragment() {
        view.setupAdapter(this.routeList);
    }

    @Override
    public void onDelegation(RouteListFragmentDelegation delegation) {
        String operation = delegation.getOperation();

        switch (operation) {
            case "add" : addAddress(delegation.getSingleDrive());
                break;
            case "remove" : removeAddress(delegation.getDestination());
                break;
            case "removeMultiple" : removeMultipleAddress(delegation.getDestination());
                break;
        }
    }

    private void addAddress(SingleDrive singleDrive){
        routeList.add(singleDrive);
        view.addAddress(routeList.indexOf(singleDrive));
    }

    private void removeAddress(String destination){
        int position = -1;

        for(SingleDrive singleDrive : routeList){

            String driveDestination = singleDrive.getDestinationFormattedAddress().getFormattedAddress();

            if(destination.equals(driveDestination)){
                position = routeList.indexOf(singleDrive);
                routeList.remove(singleDrive);
                break;
            }
        }

        if(position != -1) {
            view.removeAddress(position);
        }
    }

    private void removeMultipleAddress(String destination){

        for(SingleDrive singleDrive : routeList){

            String driveDestination = singleDrive.getDestinationFormattedAddress().getFormattedAddress();

            if(destination.equals(driveDestination)){
                int position = routeList.indexOf(singleDrive)+1;

                for(int i=position; i<routeList.size(); i++){
                    routeList.remove(i);
                }

                break;
            }
        }

        view.removeMultipleAddress();
    }

}
