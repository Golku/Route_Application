package com.example.jason.route_application_kotlin.features.shared;

import com.example.jason.route_application_kotlin.data.pojos.Session;

import android.util.Log;

public abstract class BasePresenter {

    private final String debugTag = "debugTag";

    private final long TIME_OUT = 3600000; //60 min
//    private final long TIME_OUT = 120000; //120 sec
//    private final long TIME_OUT = 60000; //60 sec
    private Long currentTime;

    protected BasePresenter() {
        this.currentTime = System.currentTimeMillis();
    }

    protected void setSession(String username, Session session){
        session.setActive(true);
        session.setUsername(username);
        session.setLoginTime(currentTime);
    }

    protected boolean verifySession(Session session){
        return session.getActive();
    }

    protected boolean verifySessionTimeOut(Session session){
        Log.d(debugTag, "Remaining seconds: "+ String.valueOf(((session.getLoginTime() + TIME_OUT) - currentTime)/1000));
        return currentTime < (session.getLoginTime() + TIME_OUT);
    }
}
