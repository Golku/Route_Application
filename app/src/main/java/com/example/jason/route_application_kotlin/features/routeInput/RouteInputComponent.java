package com.example.jason.route_application_kotlin.features.routeInput;

import com.example.jason.route_application_kotlin.data.models.AddressFormatter;
import com.example.jason.route_application_kotlin.di.AppComponent;
import com.google.gson.Gson;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Jason on 08-Feb-18.
 */

@RouteInputScope
@Component(modules = RouteInputModule.class, dependencies = AppComponent.class)
public interface RouteInputComponent {

    Retrofit getRetrofit();
    Gson getGson();
    AddressFormatter getAddressFormatter();

}
