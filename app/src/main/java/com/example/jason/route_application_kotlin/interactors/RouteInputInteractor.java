package com.example.jason.route_application_kotlin.interactors;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteRequest;
import com.example.jason.route_application_kotlin.features.routeInput.MvpRouteInput;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteInputInteractor implements MvpRouteInput.Interactor {

    private ApiService apiService;

    @Inject
    public RouteInputInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void routeRequest(RouteRequest request, final ApiCallback.RouteSubmitCallback callback) {
        Call<Void> call = apiService.submitRoute(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onRouteSubmitResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onRouteSubmitResponseFailure();
            }
        });
    }
}
