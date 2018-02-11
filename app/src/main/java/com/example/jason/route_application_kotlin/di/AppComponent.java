package com.example.jason.route_application_kotlin.di;

import com.example.jason.route_application_kotlin.RouteApplication;
import com.example.jason.route_application_kotlin.di.baseActivity.BaseActivityModule;
import com.example.jason.route_application_kotlin.di.network.GsonModule;
import com.example.jason.route_application_kotlin.di.network.NetworkModule;
import com.example.jason.route_application_kotlin.di.baseActivity.BaseActivityComponent;
import com.example.jason.route_application_kotlin.di.utilities.AddressFormatterModule;

import dagger.Component;

/**
 * Created by Jason on 07-Feb-18.
 */
@AppScope
@Component(modules = {AppModule.class, GsonModule.class, NetworkModule.class, AddressFormatterModule.class})
public interface  AppComponent {

    void inject(RouteApplication routeApplication);

    BaseActivityComponent basePresenterComponent(BaseActivityModule baseActivityModule);

}















