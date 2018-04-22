package com.example.jason.route_application_kotlin.interactors;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.api.ApiService;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.features.container.MvpContainer;

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
    public void getContainer(final ApiCallback.ContainerResponseCallback callback, String username) {
        Call<ContainerResponse> call = apiService.getContainer(username);

        call.enqueue(new Callback<ContainerResponse>() {
            @Override
            public void onResponse(Call<ContainerResponse> call, Response<ContainerResponse> response) {
                callback.onContainerResponse(response.body());
            }

            @Override
            public void onFailure(Call<ContainerResponse> call, Throwable t) {
                callback.onContainerResponseFailure();
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
