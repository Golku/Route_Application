package com.example.jason.route_application_kotlin.interactors;

import android.support.annotation.NonNull;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteRequest;
import com.example.jason.route_application_kotlin.features.routeState.MvpRouteState;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 3/15/2018.
 */

public class RouteStateInteractor implements MvpRouteState.Interactor{

    private ApiService apiService;

    @Inject
    public RouteStateInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void sendRoute(final ApiCallback.RouteResponseCallback callback, RouteRequest routeRequest) {

        Call<ContainerResponse> call = apiService.submitRoute(routeRequest);

        call.enqueue(new Callback<ContainerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ContainerResponse> call, @NonNull Response<ContainerResponse> response) {
                callback.onRouteResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ContainerResponse> call, @NonNull Throwable t) {
                callback.onRouteResponseFailure();
            }
        });

    }

    @Override
    public void getRouteState(final ApiCallback.RouteResponseCallback callback, String routeCode) {

        Call<ContainerResponse> call = apiService.getRoute(routeCode);

        call.enqueue(new Callback<ContainerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ContainerResponse> call, @NonNull Response<ContainerResponse> response) {
                callback.onRouteResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ContainerResponse> call, @NonNull Throwable t) {
                callback.onRouteResponseFailure();
            }
        });

    }


}
