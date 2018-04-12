package com.example.jason.route_application_kotlin.data.api;

import com.example.jason.route_application_kotlin.data.pojos.api.CorrectedAddresses;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteResponse;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveResponse;

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
    Call<RouteResponse> getRoute(@Path("routeCode")String routeCode);

    @POST("submitroute")
    Call<RouteResponse> submitRoute(@Body RouteRequest routeRequest);

    @POST("correctedaddressessubmition")
    Call<RouteResponse> submitCorrectedAddresses(@Body CorrectedAddresses correctedAddresses);

    @POST("getdriveinformation")
    Call<SingleDriveResponse> getDriveInformation(@Body SingleDriveRequest request);
}
