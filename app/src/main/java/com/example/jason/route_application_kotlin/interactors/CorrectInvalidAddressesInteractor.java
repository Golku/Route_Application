package com.example.jason.route_application_kotlin.interactors;

import android.support.annotation.NonNull;
import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.api.CorrectedAddresses;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteResponse;
import com.example.jason.route_application_kotlin.features.correctInvalidAddresses.MvpCorrectInvalidAddresses;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 23-Feb-18.
 */

public class CorrectInvalidAddressesInteractor implements MvpCorrectInvalidAddresses.Interactor{

    private ApiService apiService;
    private final String log_tag = "logTagDebug";

    @Inject
    public CorrectInvalidAddressesInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getInvalidAddresses(final ApiCallback.RouteResponseCallback callback, String routeCode) {
        Call<RouteResponse> call = apiService.getRoute(routeCode);

        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(@NonNull Call<RouteResponse> call, @NonNull Response<RouteResponse> response) {
                callback.onRouteResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RouteResponse> call, @NonNull Throwable t) {
                callback.onRouteResponseFailure();
            }
        });
    }

    @Override
    public void submitCorrectedAddresses(final ApiCallback.RouteResponseCallback callback, CorrectedAddresses correctedAddresses) {
        Call<RouteResponse> call = apiService.submitCorrectedAddresses(correctedAddresses);

        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(@NonNull Call<RouteResponse> call, @NonNull Response<RouteResponse> response) {
                callback.onRouteResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RouteResponse> call, @NonNull Throwable t) {
                callback.onRouteResponseFailure();
            }
        });
    }
}
