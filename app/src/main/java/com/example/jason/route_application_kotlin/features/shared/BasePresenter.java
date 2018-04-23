package com.example.jason.route_application_kotlin.features.shared;

import com.example.jason.route_application_kotlin.data.pojos.Session;

public abstract class BasePresenter {

    private final String debugTag = "debugTag";

    private final long TIME_OUT = 600000; //10 min
//    private final long TIME_OUT = 30000; //30 sec
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
        boolean timeOut;

        Long timeOutTime = session.getLoginTime() + TIME_OUT;

        timeOut = currentTime > timeOutTime;

        return timeOut;
    }

}
