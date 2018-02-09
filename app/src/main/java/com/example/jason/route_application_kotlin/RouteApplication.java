package com.example.jason.route_application_kotlin;

import android.app.Activity;
import android.app.Application;
import com.example.jason.route_application_kotlin.di.AppComponent;
import com.example.jason.route_application_kotlin.di.DaggerAppComponent;

/**
 * Created by Jason on 08-Feb-18.
 */

public class RouteApplication extends Application {

    private AppComponent component;

    public static RouteApplication get(Activity activity) {
        return (RouteApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().build();
    }

    public AppComponent getComponent() {
        return component;
    }
}