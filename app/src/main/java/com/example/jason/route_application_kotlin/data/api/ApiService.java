package com.example.jason.route_application_kotlin.data.api;

import com.example.jason.route_application_kotlin.data.pojos.api.CorrectedAddressesRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Jason on 14-Feb-18.
 */

public interface ApiService {

    @GET("getcontainer/{username}")
    Call<ContainerResponse> getContainer(@Path("username")String username);

    @POST("submitroute")
    Call<ContainerResponse> submitRoute(@Body RouteRequest routeRequest);

    @POST("correctedaddressessubmition")
    Call<ContainerResponse> submitCorrectedAddresses(@Body CorrectedAddressesRequest correctedAddressesRequest);

    @POST("getdriveinformation")
    Call<SingleDriveResponse> getDriveInformation(@Body SingleDriveRequest request);
}
