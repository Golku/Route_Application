package com.example.jason.route_application_kotlin.features.shared;

import android.support.v7.app.AppCompatActivity;
import com.example.jason.route_application_kotlin.RouteApplication;
import com.example.jason.route_application_kotlin.di.baseActivity.BaseActivityComponent;
import com.example.jason.route_application_kotlin.di.baseActivity.BaseActivityModule;

/**
 * Created by Jason on 2/9/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivityComponent getBasePresenterComponent(){
        return ((RouteApplication)getApplication())
                .getAppComponent()
                .basePresenterComponent(new BaseActivityModule(this));
    }
}
