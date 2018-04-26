package com.example.jason.route_application_kotlin.interactors;

import android.support.annotation.NonNull;
import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.api.CorrectedAddressesRequest;
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

    @Inject
    public CorrectInvalidAddressesInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

//    @Override
//    public void getInvalidAddresses(final ApiCallback.RouteResponseCallback callback, String routeCode) {
//        Call<ContainerResponse> call = apiService.getRoute(routeCode);
//
//        call.enqueue(new Callback<ContainerResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<ContainerResponse> call, @NonNull Response<ContainerResponse> response) {
//                callback.onRouteResponse(response.body());
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ContainerResponse> call, @NonNull Throwable t) {
//                callback.onRouteResponseFailure();
//            }
//        });
//    }
//
//    @Override
//    public void submitCorrectedAddresses(final ApiCallback.RouteResponseCallback callback, CorrectedAddressesRequest correctedAddressesRequest) {
//        Call<ContainerResponse> call = apiService.submitCorrectedAddresses(correctedAddressesRequest);
//
//        call.enqueue(new Callback<ContainerResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<ContainerResponse> call, @NonNull Response<ContainerResponse> response) {
//                callback.onRouteResponse(response.body());
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ContainerResponse> call, @NonNull Throwable t) {
//                callback.onRouteResponseFailure();
//            }
//        });
//    }
}
