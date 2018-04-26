package com.example.jason.route_application_kotlin.di.utilities;

import com.example.jason.route_application_kotlin.data.models.AddressFormatter;
import com.example.jason.route_application_kotlin.di.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jason on 08-Feb-18.
 */

@Module
public class AddressFormatterModule {

    @Provides
    @AppScope
    public AddressFormatter provideAddressFormatter(){
        return new AddressFormatter();
    }

}
