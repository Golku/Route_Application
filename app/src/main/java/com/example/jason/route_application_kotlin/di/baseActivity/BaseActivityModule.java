package com.example.jason.route_application_kotlin.di.baseActivity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jason on 2/9/2018.
 */

@Module
public class BaseActivityModule {

    private final FragmentActivity activity;

    public BaseActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    Context provideContext(){
        return activity;
    }

    @Provides
    Activity provideActivity(){
        return activity;
    }

}
