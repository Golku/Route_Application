package com.example.jason.route_application_kotlin.data.pojos;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private final String ACTIVE = "active";
    private final String USERNAME = "username";
    private final String TIME_STAMP = "timeStamp";

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = cntx.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    public void setActive(boolean active){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ACTIVE, active).apply();
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USERNAME, username).apply();
    }

    public void setLoginTime(Long currentTime){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(TIME_STAMP, currentTime).apply();
    }

    public boolean getActive(){
        return prefs.getBoolean(ACTIVE, false);
    }

    public String getUsername() {
        return prefs.getString(USERNAME, "");
    }

    public Long getLoginTime(){
        return prefs.getLong(TIME_STAMP, 0);
    }

}
