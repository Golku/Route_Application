package com.example.jason.route_application_kotlin.data.api;

import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface ApiService {

    @GET("getroute/{routeCode}")
    Call<ApiResponse> getRoute(@Path("routeCode")String routeCode);

    @POST("submitroute")
    Call<ApiResponse> submitRoute(@Body OutGoingRoute outGoingRoute);

    @POST("invalidaddressessubmition")
    Call<ApiResponse> submitCorrectedAddresses(@Body ArrayList<String> correctedAddresses);

}
