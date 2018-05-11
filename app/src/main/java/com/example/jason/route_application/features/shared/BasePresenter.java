package com.example.jason.route_application.features.shared;

import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.features.container.MvpContainer;

import android.util.Log;

import java.util.List;

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

    protected void createActivityEvent(List<Address> addressList, List<Drive> driveList, MvpContainer.Presenter callback){
        ActivityEvent activityEvent = new ActivityEvent();
        activityEvent.setEvent("routeUpdated");
        activityEvent.setAddressList(addressList);
        activityEvent.setDriveList(driveList);
        callback.delegateActivityEvent(activityEvent);
    }

    protected void createActivityEvent(String event, Address address, int position, MvpContainer.Presenter callback){
        ActivityEvent activityEvent = new ActivityEvent();
        activityEvent.setEvent(event);
        activityEvent.setAddress(address);
        activityEvent.setPosition(position);
        callback.delegateActivityEvent(activityEvent);
    }

    protected void createActivityEvent(String event, int position, MvpContainer.Presenter callback){
        ActivityEvent activityEvent = new ActivityEvent();
        activityEvent.setEvent(event);
        activityEvent.setPosition(position);
        callback.delegateActivityEvent(activityEvent);
    }
}
