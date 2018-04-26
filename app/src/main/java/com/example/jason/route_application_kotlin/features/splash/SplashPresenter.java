package com.example.jason.route_application_kotlin.features.splash;

import com.example.jason.route_application_kotlin.data.pojos.Session;
import com.example.jason.route_application_kotlin.features.shared.BasePresenter;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter implements MvpSplash.Presenter {

    private MvpSplash.View view;

    @Inject
    public SplashPresenter(MvpSplash.View view) {
        this.view = view;
    }

    @Override
    public void redirectUser(Session session) {

        if(verifySession(session) && verifySessionTimeOut(session)){
            view.showContainer();
        }else{
            session.setActive(false);
            view.showLogin();
        }

        view.closeActivity();
    }
}
