package com.example.jason.route_application_kotlin.features.route.listFragment;

import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;

import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public interface MvpRouteList {

    interface View{

        void setupAdapter(List<SingleDrive> routeList);

        void addDriveToList(int position);

        void removeDriveFromList(int position);

        void removeMultipleDriveFromList(int position);

        void showToast(String message);

    }

    interface Presenter{

        void initializeAdapter(List<SingleDrive> routeList);

        void onDelegation(RouteListFragmentDelegation delegation);

    }

}
