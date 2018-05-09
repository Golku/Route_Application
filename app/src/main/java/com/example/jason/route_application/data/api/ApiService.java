package com.example.jason.route_application.data.api;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.api.AddressRequest;
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

    @GET("containerrequest/{username}")
    Call<Container> containerRequest(@Path("username")String username);

    @GET("routerequest/{username}")
    Call<Route> routeRequest(@Path("username")String username);

    @POST("addressrequest")
    Call<Address> addressRequest(@Body AddressRequest address);

    @POST("driverequest")
    Call<Drive> driveRequest(@Body DriveRequest request);
}
