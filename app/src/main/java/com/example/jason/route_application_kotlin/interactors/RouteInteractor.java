package com.example.jason.route_application_kotlin.interactors;

import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteResponse;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveResponse;
import com.example.jason.route_application_kotlin.features.route.MvpRoute;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 08-Feb-18.
 */

public class RouteInteractor implements MvpRoute.Interactor{

    private ApiService apiService;

    @Inject
    public RouteInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getRoute(final ApiCallback.RouteResponseCallback callback, String routeCode) {
        Call<RouteResponse> call = apiService.getRoute(routeCode);

        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                callback.onRouteResponse(response.body());
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                callback.onRouteResponseFailure();
            }
        });
    }

    @Override
    public void getDriveInformation(final ApiCallback.SingleDriveResponseCallback callback, SingleDriveRequest request) {
        Call<SingleDriveResponse> call = apiService.getDriveInformation(request);

        call.enqueue(new Callback<SingleDriveResponse>() {
            @Override
            public void onResponse(Call<SingleDriveResponse> call, Response<SingleDriveResponse> response) {
                callback.onSingleDriveResponse(response.body());
            }

            @Override
            public void onFailure(Call<SingleDriveResponse> call, Throwable t) {
                callback.onSingleDriveResponseFailure();
            }
        });
    }
}
