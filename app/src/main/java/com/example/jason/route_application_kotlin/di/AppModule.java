package com.example.jason.route_application_kotlin.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jason on 2/9/2018.
 */

@Module
public class AppModule {

    private final Application routeApplication;

    public AppModule(Application routeApplication) {
        this.routeApplication = routeApplication;
    }

    @Provides
    @AppScope
    Application provideApplication(){
        return routeApplication;
    }

}
