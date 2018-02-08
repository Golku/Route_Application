package com.example.jason.route_application_kotlin.di;

import com.example.jason.route_application_kotlin.di.network.GsonModule;
import com.example.jason.route_application_kotlin.di.network.NetworkModule;
import com.example.jason.route_application_kotlin.di.utilities.AddressFormaterModule;
import com.google.gson.Gson;

import dagger.Component;

/**
 * Created by Jason on 07-Feb-18.
 */

@Component(modules = {GsonModule.class, NetworkModule.class, AddressFormaterModule.class})
public interface AppComponent {

    Gson getGson();


}















