package com.example.jason.route_application_kotlin.features.commentInput;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

/**
 * Created by Jason on 2/10/2018.
 */

public interface MvpCommentInput {

    interface View{

        void updateTextViews(String employeeId, String date);

        void onAddCommentBtnClick();

        void closeActivity();

        void onStartNetworkOperation();

        void onFinishNetworkOperation();

        void showToast(String message);

    }

    interface Presenter{

        void setUpInfo(String employeeId, FormattedAddress formattedAddress);

        void onAddCommentBtnClick(String comment);

    }

    interface Interactor{
        void addCommentToAddress(DatabaseCallback databaseCallback,
                                 FormattedAddress formattedAddress,
                                 String employeeId,
                                 String comment,
                                 String date
        );
    }
}
