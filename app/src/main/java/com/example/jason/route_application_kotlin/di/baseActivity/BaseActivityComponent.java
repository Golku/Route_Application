package com.example.jason.route_application_kotlin.di.baseActivity;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputActivity;

import dagger.Subcomponent;

/**
 * Created by Jason on 2/9/2018.
 */

@Subcomponent(modules = BaseActivityModule.class)
public interface BaseActivityComponent {

    void inject(RouteInputActivity routeInputActivity);

    void inject(RouteActivity routeActivity);

}
