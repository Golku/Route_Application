package com.example.jason.route_application_kotlin.interactors;

import android.util.Log;

import com.example.jason.route_application_kotlin.data.database.DatabasePresenterCallBack;
import com.example.jason.route_application_kotlin.data.database.DatabaseService;
import com.example.jason.route_application_kotlin.data.pojos.DatabaseResponse;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.features.commentInput.MvpCommentInput;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 19-Feb-18.
 */

public class CommentInputInteractor implements MvpCommentInput.Interactor{

    private DatabaseService databaseService;

    @Inject
    public CommentInputInteractor(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void addCommentToAddress(final DatabasePresenterCallBack databasePresenterCallBack, FormattedAddress formattedAddress, String employeeId, String comment, String date) {

        Call<DatabaseResponse> call = databaseService.addCommentToAddress(
                formattedAddress.getStreet(),
                formattedAddress.getPostCode(),
                formattedAddress.getCity(),
                employeeId,
                comment,
                date
        );

        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(Call<DatabaseResponse> call, Response<DatabaseResponse> response) {
                Log.d("commentInputLogTag", "Responded");
                databasePresenterCallBack.processDatabaseResponse(response.body());
            }

            @Override
            public void onFailure(Call<DatabaseResponse> call, Throwable t) {
                Log.d("commentInputLogTag", "Failure");
                Log.d("commentInputLogTag", "Throwable: " + t.toString());
                Log.d("commentInputLogTag", "call: " + call.toString());
                databasePresenterCallBack.onApiResponseFailure();
            }
        });

    }
}
