package com.example.jason.route_application.model;


import android.os.AsyncTask;

import com.example.jason.route_application.model.pojos.ApiResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InformationFetcher extends AsyncTask<Void, Void, ApiResponse> {

    private OkHttpClient okHttpClient;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");



    @Override
    protected ApiResponse doInBackground(Void... params) {

        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

        final Gson gson = new Gson();

        ApiResponse apiResponse;

        String url = "http://217.103.231.118:8080/webapi/myresource";

        okHttpClient = new OkHttpClient();

        RequestBody body = RequestBody
                .create(JSON, gson.toJson(outGoingRoute));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                requestError = true;
                errorMessage = "Request Failed: " +e.getMessage();
                handler.sendEmptyMessage(0);
//                Log.d(log_tag, "No answer from api: " +e.getMessage());
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String routeApiResponse = response.body().string();

//                Log.d(log_tag, routeApiResponse);

                if (response.isSuccessful()) {

                    apiResponse = gson.fromJson(routeApiResponse, ApiResponse.class);

//                    Log.d(log_tag, singleOrganizedRoute.getRouteCode());
//                    Log.d(log_tag, String.valueOf(singleOrganizedRoute.getPrivateAddressesCount()));
//                    Log.d(log_tag, String.valueOf(singleOrganizedRoute.getBusinessAddressesCount()));
//                    Log.d(log_tag, String.valueOf(singleOrganizedRoute.getWrongAddressesCount()));
//                    Log.d(log_tag, "");

//                    for(int i=0; i<singleOrganizedRoute.getRouteList().size(); i++) {
//                        Log.d(log_tag, singleOrganizedRoute.getRouteList().get(i).getOriginFormattedAddress().getCompletedAddress());
//                        Log.d(log_tag, singleOrganizedRoute.getRouteList().get(i).getDestinationFormattedAddress().getCompletedAddress());
//                        Log.d(log_tag, singleOrganizedRoute.getRouteList().get(i).getDriveDurationHumanReadable());
//                        Log.d(log_tag, "");
//                    }

                    handler.sendEmptyMessage(0);
                }else{
                    requestError = true;
                    errorMessage = "Response was not successful";
                    handler.sendEmptyMessage(0);
//                    Log.d("RouteCalculator", "Response is not successful: " +routeApiResponse);
                }

            }

        });

        return null;
    }


}