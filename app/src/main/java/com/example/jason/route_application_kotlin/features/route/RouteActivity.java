package com.example.jason.route_application_kotlin.features.route;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.features.shared.BaseActivity;

import javax.inject.Inject;

public class RouteActivity extends BaseActivity {

    @Inject RoutePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBasePresenterComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

    }

}
