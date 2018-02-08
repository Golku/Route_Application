package com.example.jason.route_application_kotlin.di.network;

import com.google.gson.Gson;
import dagger.Module;
import okhttp3.MediaType;

/**
 * Created by Jason on 08-Feb-18.
 */

@Module
public class GsonModule {

    private final Gson gson = new Gson();
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


}
