package com.example.jason.route_application_kotlin.features.CommentInput;

import com.example.jason.route_application_kotlin.data.database.DatabasePresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

/**
 * Created by Jason on 2/10/2018.
 */

public interface MvpCommentInput {

    interface View{
        void updateTextViews(String employeeId, String date);
        void onAddCommentBtnClick();
        void onDatabaseResponse(String message);
        void onStartNetworkOperation();
        void onFinishNetworkOperation();
        void showToast(String message);
    }

    interface Presenter{
        void setUpInfo(String employeeId, FormattedAddress formattedAddress);
        void onAddCommentBtnClick(String comment);
    }

    interface Interactor{
        void addCommentToAddress(DatabasePresenterCallBack databasePresenterCallBack,
                                 FormattedAddress formattedAddress,
                                 String employeeId,
                                 String comment,
                                 String date
        );
    }
}
