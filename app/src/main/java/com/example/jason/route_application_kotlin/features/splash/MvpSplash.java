package com.example.jason.route_application_kotlin.features.splash;

import com.example.jason.route_application_kotlin.data.pojos.Session;

public interface MvpSplash {

    interface View{

        Session getSession();

        void showContainer();

        void showLogin();

        void closeActivity();

    }

    interface Presenter{

        void redirectUser();

    }

}
