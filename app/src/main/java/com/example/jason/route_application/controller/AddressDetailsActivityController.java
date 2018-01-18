package com.example.jason.route_application.controller;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.example.jason.route_application.model.DatabaseQueries;
import com.example.jason.route_application.model.pojos.DatabaseResponse;
import com.example.jason.route_application.model.pojos.FormattedAddress;
import com.example.jason.route_application.view.AddressDetailsActivity;

import java.util.ArrayList;

public class AddressDetailsActivityController {

    private final String log_tag = "AddressDetails";

    public AddressDetailsActivity addressDetailsActivity;
    private DatabaseQueries databaseQueries;

    private DatabaseResponse databaseResponse;

    public AddressDetailsActivityController(AddressDetailsActivity addressDetailsActivity){
        this.addressDetailsActivity = addressDetailsActivity;
        this.databaseQueries = new DatabaseQueries(this);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            addressDetailsActivity.updateView(databaseResponse);
        }

    };

    public void displayMessage(String type, boolean visible, String message){

        if(type == "loadingBar"){
            if(visible){
                addressDetailsActivity.showLoadingBar();
            }else{
                addressDetailsActivity.hideLoadingBar();
            }
        } else if (type == "toast"){
            addressDetailsActivity.showToast(message);
        }

    }

    public void getAddressInfo(FormattedAddress formattedAddress){
        databaseQueries.queryDbForAddressInfo(formattedAddress);
    }

    public void onAddCommentButtonClick(){
        addressDetailsActivity.startCommentInputActivity();
    }

    public void onListItemClick(ArrayList<String> commentInformation){

        addressDetailsActivity.startCommentDisplayActivity(commentInformation);

    }

    public void setupDatabaseResponse(DatabaseResponse databaseResponse){
        this.databaseResponse = databaseResponse;
        handler.sendEmptyMessage(0);
    }

}
