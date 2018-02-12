package com.example.jason.route_application_kotlin.features.route;

import android.os.Bundle;
import android.util.Log;

import com.example.jason.route_application_kotlin.R;
import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RouteActivity extends DaggerAppCompatActivity implements MvpRoute.View{

    @Inject MvpRoute.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

    }

}
