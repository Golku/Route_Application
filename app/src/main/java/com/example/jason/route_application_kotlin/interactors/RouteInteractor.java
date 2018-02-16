package com.example.jason.route_application_kotlin.interactors;

import android.util.Log;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.api.PresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
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
    public void submitRouteForOrganizing(final PresenterCallBack presenterCallBack, OutGoingRoute outGoingRoute) {

        Call<ApiResponse> call = apiService.submitRoute(outGoingRoute);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("RouteInteractor", "Responded");
                presenterCallBack.processApiResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("RouteInteractor", "Failure");
                Log.d("RouteInteractor", "Throwable: " + t.toString());
                Log.d("RouteInteractor", "call: " + call.toString());
            }
        });

    }
}
