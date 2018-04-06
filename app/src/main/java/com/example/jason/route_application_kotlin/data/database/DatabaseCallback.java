package com.example.jason.route_application_kotlin.data.database;

import com.example.jason.route_application_kotlin.data.pojos.database.DatabaseResponse;

/**
 * Created by Jason on 19-Feb-18.
 */

public interface DatabaseCallback {
    void onDatabaseResponse(DatabaseResponse databaseResponse);
    void onApiResponseFailure();
}

