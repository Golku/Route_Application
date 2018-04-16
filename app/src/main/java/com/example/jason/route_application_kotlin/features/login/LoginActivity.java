package com.example.jason.route_application_kotlin.features.login;

import com.example.jason.route_application_kotlin.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity implements MvpLogin.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
