package com.example.jason.route_application_kotlin.di;

import com.example.jason.route_application_kotlin.RouteApplication;
import com.example.jason.route_application_kotlin.di.network.GsonModule;
import com.example.jason.route_application_kotlin.di.network.NetworkModule;
import com.example.jason.route_application_kotlin.di.utilities.AddressFormatterModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Jason on 07-Feb-18.
 */
@AppScope
@Component(modules = {AndroidSupportInjectionModule.class, ActivityBuilder.class, AppModule.class, GsonModule.class, NetworkModule.class, AddressFormatterModule.class})
public interface  AppComponent extends AndroidInjector<RouteApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(RouteApplication application);

        AppComponent build();
    }
}