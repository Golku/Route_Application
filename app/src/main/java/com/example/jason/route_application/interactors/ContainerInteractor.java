package com.example.jason.route_application.interactors;

import com.example.jason.route_application.data.api.ApiCallback;
import com.example.jason.route_application.data.api.ApiService;
import com.example.jason.route_application.data.pojos.api.Container;
import com.example.jason.route_application.data.pojos.api.Route;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.features.container.MvpContainer;

import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 08-Feb-18.
 */

public class ContainerInteractor implements MvpContainer.Interactor{

    private ApiService apiService;

    @Inject
    public ContainerInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getContainer(String username, final ApiCallback.ContainerResponseCallback callback) {
        Call<Container> call = apiService.getContainer(username);

        call.enqueue(new Callback<Container>() {
            @Override
            public void onResponse(Call<Container> call, Response<Container> response) {
                callback.onContainerResponse(response.body());
            }

            @Override
            public void onFailure(Call<Container> call, Throwable t) {
                callback.onContainerResponseFailure();
            }
        });
    }

    @Override
    public void getRoute(String username, final ApiCallback.RouteResponseCallback callback) {
        Call<Route> call = apiService.getRoute(username);

        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                callback.onRouteResponse(response.body());
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                callback.onRouteResponseFailure();
            }
        });
    }

    @Override
    public void getDriveInformation(DriveRequest request, final ApiCallback.DriveResponseCallback callback) {
        Call<Drive> call = apiService.getDriveInformation(request);

        call.enqueue(new Callback<Drive>() {
            @Override
            public void onResponse(Call<Drive> call, Response<Drive> response) {
                callback.onSingleDriveResponse(response.body());
            }

            @Override
            public void onFailure(Call<Drive> call, Throwable t) {
                callback.onSingleDriveResponseFailure();
            }
        });
    }
}
