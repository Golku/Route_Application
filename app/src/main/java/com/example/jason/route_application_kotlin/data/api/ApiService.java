package com.example.jason.route_application_kotlin.data.api;

import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface ApiService {

    @GET("myresource")
    Call<ApiResponse> getRoute();

    @POST("myresource")
    Call<ApiResponse> submitRoute(@Body OutGoingRoute outGoingRoute);

}
