package com.example.jason.route_application.data.api;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.api.Container;
import com.example.jason.route_application.data.pojos.api.Route;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.data.pojos.api.DriveRequest;

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

    @GET("getroute/{username}")
    Call<Route> getRoute(@Path("username")String username);

    @POST("getdriveinformation")
    Call<Drive> getDriveInformation(@Body DriveRequest request);

    @POST("addaddress")
    Call<Address> addAddress(@Body Address address);
}
