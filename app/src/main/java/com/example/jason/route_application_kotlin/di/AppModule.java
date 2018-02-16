package com.example.jason.route_application_kotlin.di;

import android.content.Context;

import com.example.jason.route_application_kotlin.RouteApplication;
import com.example.jason.route_application_kotlin.di.network.NetworkModule;
import com.example.jason.route_application_kotlin.di.utilities.AddressFormatterModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jason on 2/9/2018.
 */

@Module(includes = {NetworkModule.class, AddressFormatterModule.class})
public class AppModule {

    @AppScope
    @Provides
    public Context provideContext(RouteApplication application) {
        return application.getApplicationContext();
    }
}
