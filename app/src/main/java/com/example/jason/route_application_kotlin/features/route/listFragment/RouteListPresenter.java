package com.example.jason.route_application_kotlin.features.route.listFragment;

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
    public void singleDriveReceive(SingleDrive singleDrive) {
        routeList.add(singleDrive);
        int position = routeList.indexOf(singleDrive);
        view.addDriveToList(position);
    }
}
