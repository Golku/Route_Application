package com.example.jason.route_application_kotlin.features.login;

import javax.inject.Inject;

public class LoginPresenter {

    private final String debugTag = "debugTag";

    private MvpLogin.View view;
    private MvpLogin.Interactor interactor;

    @Inject
    public LoginPresenter(MvpLogin.View view, MvpLogin.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

}
