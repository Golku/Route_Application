package com.example.jason.route_application_kotlin.interactors;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;
import com.example.jason.route_application_kotlin.data.database.DatabaseService;
import com.example.jason.route_application_kotlin.data.pojos.database.LoginResponse;
import com.example.jason.route_application_kotlin.features.login.MvpLogin;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor implements MvpLogin.Interactor{

    private DatabaseService databaseService;

    @Inject
    LoginInteractor(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void login(final DatabaseCallback.LoginCallBack callBack) {
        Call<LoginResponse> call = databaseService.login("", "");

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                callBack.onLoginResponse(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callBack.onLoginResponseFailure();
            }
        });
    }
}
