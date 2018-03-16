package com.example.jason.route_application_kotlin.interactors;

import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
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
    private final String log_tag = "logTagDebug";

    @Inject
    public RouteStateInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void sendRoute(final ApiPresenterCallBack apiPresenterCallBack, OutGoingRoute outGoingRoute) {

        Call<ApiResponse> call = apiService.submitRoute(outGoingRoute);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                apiPresenterCallBack.onApiResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(log_tag, "Failure");
                Log.d(log_tag, "Throwable: " + t.toString());
                Log.d(log_tag, "call: " + call.toString());
                apiPresenterCallBack.onApiResponseFailure();
            }
        });

    }

    @Override
    public void getRouteState(final ApiPresenterCallBack apiPresenterCallBack, String routeCode) {

        Call<ApiResponse> call = apiService.getRoute(routeCode);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                apiPresenterCallBack.onApiResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(log_tag, "Failure");
                Log.d(log_tag, "Throwable: " + t.toString());
                Log.d(log_tag, "call: " + call.toString());
                apiPresenterCallBack.onApiResponseFailure();
            }
        });

    }


}
