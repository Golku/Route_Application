package com.example.jason.route_application.model;

import android.util.Log;

import com.example.jason.route_application.controller.AddressDetailsActivityController;
import com.example.jason.route_application.controller.CommentInputActivityController;
import com.example.jason.route_application.model.pojos.DatabaseResponse;
import com.example.jason.route_application.model.pojos.FormattedAddress;
import com.example.jason.route_application.model.pojos.SingleComment;
import com.example.jason.route_application.view.AddressDetailsActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DatabaseQueries {

    private final String log_tag = "AddressDetailsLog";

    private AddressDetailsActivityController addressActivityController;
    private CommentInputActivityController commentInputActivityController;

    private OkHttpClient okHttpClient;

    private final String root_url = "http://217.103.231.118/map/v1/";

    private final String url_addAddressComment = root_url + "addAddressComment.php";

    private final String url_getAddressInfo = root_url + "getAddressInfo.php";

    public DatabaseQueries(AddressDetailsActivityController controller) {
        this.addressActivityController = controller;
    }

    public DatabaseQueries(CommentInputActivityController controller) {
        this.commentInputActivityController = controller;
    }

    public void queryDbForAddressInfo(FormattedAddress formattedAddress) {

        final Gson gson = new Gson();

        okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("street_name", formattedAddress.getStreet())
                .add("post_code", formattedAddress.getPostCode())
                .add("city", formattedAddress.getCity()).build();

        Request request = new Request.Builder()
                .url(url_getAddressInfo)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(log_tag, "Request failed: " +e.getMessage());
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseString = response.body().string();

//                Log.d(log_tag, responseString);

                if (response.isSuccessful()) {

                    if (addressActivityController.addressDetailsActivity.active) {
                        DatabaseResponse databaseResponse = gson.fromJson(responseString, DatabaseResponse.class);
                        addressActivityController.setupDatabaseResponse(databaseResponse);
                    }

//                    Log.d(log_tag, "Error: "+databaseResponse.getError());
//                    Log.d(log_tag, "Error message: "+databaseResponse.getErrorMessage());
//
//                    Log.d(log_tag, "Id: "+databaseResponse.getSingleAddressDbInformation().getId());
//                    Log.d(log_tag, "Business: "+databaseResponse.getSingleAddressDbInformation().getBusiness());
//                    Log.d(log_tag, "Comments count: "+databaseResponse.getSingleAddressDbInformation().getCommentsCount());
//
//                    for(int i = 0; i<databaseResponse.getSingleAddressDbInformation().getCommentsCount(); i++){
//                        Log.d(log_tag, " ");
//                        Log.d(log_tag, "employeedId: "+databaseResponse.getSingleAddressDbInformation().getEmployeeId().get(i));
//                        Log.d(log_tag, "comment: "+databaseResponse.getSingleAddressDbInformation().getComments().get(i));
//                        Log.d(log_tag, "date: "+ databaseResponse.getSingleAddressDbInformation().getDates().get(i));
//                    }

                }else{
                    Log.d(log_tag, "Response Failed");
                }

            }

        });

    }

    public void addCommentToAddress(SingleComment singleComment){

        final Gson gson = new Gson();

        okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("street_name", singleComment.getStreet())
                .add("post_code", singleComment.getPostCode())
                .add("city", singleComment.getCity())
                .add("employee_id", singleComment.getEmployedId())
                .add("message", singleComment.getComment())
                .add("date", singleComment.getDate()).build();

        Request request = new Request.Builder()
                .url(url_addAddressComment)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(log_tag, "Request failed: " +e.getMessage());

            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseString = response.body().string();

                //Log.d(log_tag, "Responded");

                if (response.isSuccessful()) {

                    if (commentInputActivityController.commentInputActivity.active) {
                        DatabaseResponse databaseResponse = gson.fromJson(responseString, DatabaseResponse.class);
                        commentInputActivityController.setupDatabaseResponse(databaseResponse);
                    }

                }else{
                    Log.d(log_tag, "Response Failed");
                }

            }

        });
    }

}
