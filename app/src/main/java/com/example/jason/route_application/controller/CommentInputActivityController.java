package com.example.jason.route_application.controller;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.example.jason.route_application.model.DatabaseQueries;
import com.example.jason.route_application.model.pojos.DatabaseResponse;
import com.example.jason.route_application.model.pojos.SingleComment;
import com.example.jason.route_application.view.CommentInputActivity;

public class CommentInputActivityController {

    public CommentInputActivity commentInputActivity;
    private DatabaseQueries databaseQueries;
    private DatabaseResponse databaseResponse;

    public CommentInputActivityController(CommentInputActivity commentInputActivity) {
        this.commentInputActivity = commentInputActivity;
        this.databaseQueries = new DatabaseQueries(this);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            commentInputActivity.showCommentInputStatus(databaseResponse);
        }

    };

    public void displayMessage(String type, boolean visible, String message){

        if(type == "loadingBar"){
            if(visible){
                commentInputActivity.showLoadingBar();
            }else{
                commentInputActivity.hideLoadingBar();
            }
        } else if (type == "toast"){
            commentInputActivity.showToast(message);
        }

    }

    public void addCommentToAddress(SingleComment singleComment){
        databaseQueries.addCommentToAddress(singleComment);
    }

    public void setupDatabaseResponse(DatabaseResponse databaseResponse){
        this.databaseResponse = databaseResponse;
        handler.sendEmptyMessage(0);
    }

}
