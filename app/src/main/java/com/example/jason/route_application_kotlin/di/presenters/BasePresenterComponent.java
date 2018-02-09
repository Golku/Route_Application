package com.example.jason.route_application_kotlin.di.presenters;

import com.example.jason.route_application_kotlin.RouteApplication;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputActivity;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by Jason on 2/9/2018.
 */

@Subcomponent(modules = BasePresenterModule.class)
public interface BasePresenterComponent {

    void inject(RouteInputActivity routeInputActivity);

}
