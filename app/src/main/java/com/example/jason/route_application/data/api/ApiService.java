package com.example.jason.route_application.data.api;

import com.example.jason.route_application.data.pojos.api.Container;
import com.example.jason.route_application.data.pojos.api.CorrectedAddressesRequest;
import com.example.jason.route_application.data.pojos.api.RouteRequest;
import com.example.jason.route_application.data.pojos.api.SingleDrive;
import com.example.jason.route_application.data.pojos.api.SingleDriveRequest;

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
    Call<Container> getContainer(@Path("username")String username);

    @POST("submitroute")
    Call<Void> submitRoute(@Body RouteRequest routeRequest);

    @POST("getdriveinformation")
    Call<SingleDrive> getDriveInformation(@Body SingleDriveRequest request);

    @POST("correctedaddressessubmition")
    Call submitCorrectedAddresses(@Body CorrectedAddressesRequest correctedAddressesRequest);
}
