package com.example.jason.route_application_kotlin.data.database;

import com.example.jason.route_application_kotlin.data.pojos.DatabaseResponse;

/**
 * Created by Jason on 19-Feb-18.
 */

public interface DatabasePresenterCallBack {
    void processDatabaseResponse(DatabaseResponse databaseResponse);
}

