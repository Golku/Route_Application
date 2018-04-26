package com.example.jason.route_application.features.container.listFragment;

import com.example.jason.route_application.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application.data.pojos.api.SingleDrive;

import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public interface MvpContainerList {

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
