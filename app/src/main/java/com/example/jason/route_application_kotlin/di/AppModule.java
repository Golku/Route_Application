package com.example.jason.route_application_kotlin.di;

import android.app.Application;
import android.content.Context;

import com.example.jason.route_application_kotlin.RouteApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jason on 2/9/2018.
 */

@Module
public class AppModule {

    @AppScope
    @Provides
    Context provideContext(RouteApplication application) {
        return application.getApplicationContext();
    }
}
