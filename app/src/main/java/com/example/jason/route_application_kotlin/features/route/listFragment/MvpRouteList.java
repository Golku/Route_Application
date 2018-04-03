package com.example.jason.route_application_kotlin.features.route.listFragment;

import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;

import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public interface MvpRouteList {

    interface View{
        void setupAdapter(List<SingleDrive> routeList);
    }

    interface Presenter{
        void setupList();
    }

}
