package com.example.jason.route_application_kotlin.data.database;

import com.example.jason.route_application_kotlin.data.pojos.database.AddressInformationResponse;
import com.example.jason.route_application_kotlin.data.pojos.database.CommentInputResponse;
import com.example.jason.route_application_kotlin.data.pojos.database.LoginResponse;

/**
 * Created by Jason on 19-Feb-18.
 */

public interface DatabaseCallback {

    interface LoginCallBack{
        void onLoginResponse(LoginResponse response);
        void onLoginResponseFailure();
    }

    interface AddressInformationCallBack{
        void onAddressInformationResponse(AddressInformationResponse response);
        void onAddressInformationResponseFailure();
    }

    interface CommentInputCallBack{
        void onCommentInputResponse(CommentInputResponse response);
        void onCommentInputResponseFailure();
    }
}

