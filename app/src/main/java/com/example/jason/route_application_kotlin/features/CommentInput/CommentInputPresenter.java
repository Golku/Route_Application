package com.example.jason.route_application_kotlin.features.CommentInput;

import com.example.jason.route_application_kotlin.data.database.DatabasePresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.DatabaseResponse;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

/**
 * Created by Jason on 19-Feb-18.
 */

public class CommentInputPresenter implements MvpCommentInput.Presenter, DatabasePresenterCallBack{

    private final MvpCommentInput.View view;
    private MvpCommentInput.Interactor interactor;

    private FormattedAddress formattedAddress;
    private CommentInformation commentInformation;

    private String employeeId;
    private String date;

    @Inject
    public CommentInputPresenter(MvpCommentInput.View view, MvpCommentInput.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setUpInfo(String employeeId, FormattedAddress formattedAddress){
        this.employeeId = employeeId;
        this.formattedAddress = formattedAddress;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.date = sdf.format(System.currentTimeMillis());
        view.updateTextViews(employeeId, date);
    }

    @Override
    public void onAddCommentBtnClick(String comment) {
        view.onStartNetworkOperation();
        interactor.addCommentToAddress(this, formattedAddress, employeeId, comment, date);
    }

    @Override
    public void processDatabaseResponse(DatabaseResponse databaseResponse) {
        view.onFinishNetworkOperation();

        if(databaseResponse.isError()){
            view.onDatabaseResponse("Fail to add comment");
        }else{
            view.onDatabaseResponse("Comment was added");
        }

    }
}