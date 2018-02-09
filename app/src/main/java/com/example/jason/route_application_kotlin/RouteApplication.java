package com.example.jason.route_application_kotlin;

import android.app.Activity;
import android.app.Application;
import com.example.jason.route_application_kotlin.di.AppComponent;
import com.example.jason.route_application_kotlin.di.AppModule;
import com.example.jason.route_application_kotlin.di.DaggerAppComponent;

/**
 * Created by Jason on 08-Feb-18.
 */

public class RouteApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        getAppComponent().inject(this);
        super.onCreate();
    }

    public AppComponent getAppComponent(){
        if(component == null){
            component = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return component;
    }
}