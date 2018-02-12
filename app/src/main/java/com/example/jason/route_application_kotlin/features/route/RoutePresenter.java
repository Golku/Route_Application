package com.example.jason.route_application_kotlin.features.route;

import android.app.Activity;
import android.util.Log;

import com.example.jason.route_application_kotlin.interactors.RouteInteractor;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements MvpRoute.Presenter {

    private final MvpRoute.View view;

    private MvpRoute.Interactor routeInteractor;

    @Inject
    public RoutePresenter(MvpRoute.View view, MvpRoute.Interactor interactor) {
        this.view = view;
        this.routeInteractor = interactor;
    }

    @Override
    public void getData() {

        routeInteractor.getRoute();

    }
}
