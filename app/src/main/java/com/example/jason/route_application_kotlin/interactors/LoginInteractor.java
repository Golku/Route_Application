package com.example.jason.route_application_kotlin.interactors;

import com.example.jason.route_application_kotlin.data.database.DatabaseService;
import com.example.jason.route_application_kotlin.features.login.MvpLogin;

import javax.inject.Inject;

public class LoginInteractor implements MvpLogin.Interactor{

    private DatabaseService databaseService;

    @Inject
    public LoginInteractor(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

}
