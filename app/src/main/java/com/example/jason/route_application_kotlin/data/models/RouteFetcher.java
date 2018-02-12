package com.example.jason.route_application_kotlin.data.models;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * Created by Jason on 07-Feb-18.
 */

@Singleton
public class RouteFetcher {

    @Inject
    public RouteFetcher(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    private Retrofit retrofit;


}
