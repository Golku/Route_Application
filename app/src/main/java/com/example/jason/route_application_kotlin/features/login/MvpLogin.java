package com.example.jason.route_application_kotlin.features.login;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;

public interface MvpLogin {

    interface View{

    }

    interface Presenter{

    }

    interface Interactor{
        void login(DatabaseCallback.LoginCallBack callBack);
    }

}
