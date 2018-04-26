package com.example.jason.route_application.interactors;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.database.DatabaseService;
import com.example.jason.route_application.data.pojos.database.CommentInputResponse;
import com.example.jason.route_application.data.pojos.FormattedAddress;
import com.example.jason.route_application.features.commentInput.MvpCommentInput;
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
    public void addCommentToAddress(final DatabaseCallback.CommentInputCallBack callback, FormattedAddress formattedAddress, String employeeId, String comment, String date) {

        Call<CommentInputResponse> call = databaseService.addCommentToAddress(
                formattedAddress.getStreet(),
                formattedAddress.getPostCode(),
                formattedAddress.getCity(),
                employeeId,
                comment,
                date
        );

        call.enqueue(new Callback<CommentInputResponse>() {
            @Override
            public void onResponse(Call<CommentInputResponse> call, Response<CommentInputResponse> response) {
                callback.onCommentInputResponse(response.body());
            }

            @Override
            public void onFailure(Call<CommentInputResponse> call, Throwable t) {
                callback.onCommentInputResponseFailure();
            }
        });

    }
}
