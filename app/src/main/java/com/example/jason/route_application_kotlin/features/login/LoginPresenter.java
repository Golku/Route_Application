package com.example.jason.route_application_kotlin.features.login;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;
import com.example.jason.route_application_kotlin.data.pojos.database.LoginResponse;

import javax.inject.Inject;

public class LoginPresenter implements MvpLogin.Presenter, DatabaseCallback.LoginCallBack{

    private final String debugTag = "debugTag";

    private MvpLogin.View view;
    private MvpLogin.Interactor interactor;

    @Inject
    LoginPresenter(MvpLogin.View view, MvpLogin.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onLoginResponse(LoginResponse response) {

    }

    @Override
    public void onLoginResponseFailure() {

    }
}
