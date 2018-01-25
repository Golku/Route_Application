package com.example.jason.route_application.model;


import android.os.AsyncTask;
import android.util.Log;

import com.example.jason.route_application.model.pojos.ApiResponse;
import com.example.jason.route_application.model.pojos.OutGoingRoute;
import com.example.jason.route_application.model.pojos.SingleOrganizedRoute;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InformationFetcher extends AsyncTask<OutGoingRoute, String, SingleOrganizedRoute> {

    private final String log_tag = "InformationFetcher";

    private final String submitRouteUrl = "http://217.103.231.118:8080/webapi/myresource";
    private final String getRouteUrl = "http://217.103.231.118:8080/webapi/myresource/";

    private OkHttpClient okHttpClient;

    private final Gson gson = new Gson();
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String routeCode;

    private boolean resubmitRoute;
    private boolean organizingRoute;

    private SingleOrganizedRoute singleOrganizedRoute;

    @Override
    protected SingleOrganizedRoute doInBackground(OutGoingRoute... outGoingRoute) {

        okHttpClient = new OkHttpClient();

        this.routeCode = outGoingRoute[0].getRouteCode();

        //keeps submitting the route for organizing.
        do{
            sendUnorganizedRoute(outGoingRoute[0]);
            //add a time break here. retry every 10 second
            //if here with counter to stop after 3 try
        }while(resubmitRoute);

        //check if route was submitted and show appropriated message
        publishProgress("Route received by the server and it's being organized");

        //keeps asking for the organized route until it's received
        do{
            askForOrganizedRoute();
            //add a time break here. retry every 10 second
            //if here with counter to stop after 3 try
        }while(organizingRoute);

        return singleOrganizedRoute;
    }

    private void sendUnorganizedRoute(OutGoingRoute outGoingRoute){

        ApiResponse apiResponse = null;

        RequestBody requestBody = RequestBody
                .create(JSON, gson.toJson(outGoingRoute));

        Request request = new Request.Builder()
                .url(submitRouteUrl)
                .post(requestBody)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
            apiResponse = gson.fromJson(response.body().string(), ApiResponse.class);
        } catch (IOException e) {
            resubmitRoute = true;
            e.printStackTrace();
        }

        if(apiResponse != null){
            if(apiResponse.isOrganizingRoute()){
                resubmitRoute = false;
            }
        }else{
            resubmitRoute = true;
        }

    }

    private void askForOrganizedRoute(){

        ApiResponse apiResponse = null;

        String url = getRouteUrl+routeCode;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
            apiResponse = gson.fromJson(response.body().string(), ApiResponse.class);
        } catch (IOException e) {
            organizingRoute = true;
            e.printStackTrace();
        }

        if(apiResponse != null){
            if(apiResponse.isOrganizingRoute()){
                organizingRoute = true;
            }else{
                singleOrganizedRoute = apiResponse.getOrganizedRoute();
                organizingRoute = false;
            }
        }else{
            organizingRoute = false;
        }

    }


}