package com.example.jason.route_application_kotlin.interactors;

import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.CorrectedAddresses;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import com.example.jason.route_application_kotlin.features.correctInvalidAddresses.MvpCorrectInvalidAddresses;

import java.util.ArrayList;
import java.util.Map;

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

    @Override
    public void submitRoute(final ApiPresenterCallBack apiPresenterCallBack, OutGoingRoute outGoingRoute) {

        Call<ApiResponse> call = apiService.submitRoute(outGoingRoute);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("RouteInteractor", "Responded");
                apiPresenterCallBack.processApiResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("RouteInteractor", "Failure");
                Log.d("RouteInteractor", "Throwable: " + t.toString());
                Log.d("RouteInteractor", "call: " + call.toString());
                apiPresenterCallBack.onApiResponseFailure();
            }
        });

    }

    @Override
    public void getInvalidAddresses(final ApiPresenterCallBack apiPresenterCallBack, String routeCode) {

        Call<ApiResponse> call = apiService.getInvalidAddresses(routeCode);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("RouteInteractor", "Responded");
                apiPresenterCallBack.processApiResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("RouteInteractor", "Failure");
                Log.d("RouteInteractor", "Throwable: " + t.toString());
                Log.d("RouteInteractor", "call: " + call.toString());
                apiPresenterCallBack.onApiResponseFailure();
            }
        });

    }

    @Override
    public void submitCorrectedAddresses(final ApiPresenterCallBack apiPresenterCallBack, CorrectedAddresses correctedAddresses) {

        Call<ApiResponse> call = apiService.submitCorrectedAddresses(correctedAddresses);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("RouteInteractor", "Responded");
                apiPresenterCallBack.processApiResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("RouteInteractor", "Failure");
                Log.d("RouteInteractor", "Throwable: " + t.toString());
                Log.d("RouteInteractor", "call: " + call.toString());
                apiPresenterCallBack.onApiResponseFailure();
            }
        });

    }


}
