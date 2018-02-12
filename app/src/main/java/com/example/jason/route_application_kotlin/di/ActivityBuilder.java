package com.example.jason.route_application_kotlin.di;

import com.example.jason.route_application_kotlin.features.route.RouteActivity;
import com.example.jason.route_application_kotlin.features.route.RouteModule;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputActivity;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Jason on 12-Feb-18.
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = RouteInputModule.class)
    abstract RouteInputActivity bindRouteInput();


    @ContributesAndroidInjector(modules = RouteModule.class)
    abstract RouteActivity bindRoute();
}
