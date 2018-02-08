package com.example.jason.route_application_kotlin.di.network;

import com.example.jason.route_application_kotlin.di.AppScope;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.MediaType;

/**
 * Created by Jason on 08-Feb-18.
 */

@Module
public class GsonModule {

//    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Provides
    @AppScope
    public Gson provideGson (){
        Gson gson = new Gson();
        return gson;
    }

}
