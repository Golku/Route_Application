package com.example.jason.route_application.features.commentInput;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.pojos.Address;

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

        void setUpInfo(String employeeId, Address address);

        void onAddCommentBtnClick(String comment);

    }

    interface Interactor{
        void addCommentToAddress(DatabaseCallback.CommentInputCallBack callback,
                                 Address address,
                                 String employeeId,
                                 String comment,
                                 String date
        );
    }
}
