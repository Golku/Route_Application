package com.example.jason.route_application_kotlin.interactors;

import com.example.jason.route_application_kotlin.data.models.RouteFetcher;
import com.example.jason.route_application_kotlin.features.route.MvpRoute;

import javax.inject.Inject;

/**
 * Created by Jason on 08-Feb-18.
 */

public class RouteInteractor implements MvpRoute.Interactor {

    private RouteFetcher routeFetcher;

    @Inject
    public RouteInteractor(RouteFetcher routeFetcher) {
        this.routeFetcher = routeFetcher;
    }

    @Override
    public void getRoute() {

    }

}
