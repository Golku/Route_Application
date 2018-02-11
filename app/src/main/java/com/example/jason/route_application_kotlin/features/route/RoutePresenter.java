package com.example.jason.route_application_kotlin.features.route;

import android.app.Activity;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements MvpRoute.Presenter {

    private final RouteActivity view;

    @Inject
    public RoutePresenter(Activity view) {
        this.view = (RouteActivity) view;
    }

}
