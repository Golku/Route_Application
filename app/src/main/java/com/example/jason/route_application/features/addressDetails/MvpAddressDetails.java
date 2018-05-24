package com.example.jason.route_application.features.addressDetails;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.database.AddressInformation;
import com.example.jason.route_application.data.pojos.CommentInformation;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpAddressDetails {

    interface View{

        void setUpAdapter(AddressInformation addressInformation);

        void updateMessageToUserTextView(String message);

        void changeAddressType(boolean isBusiness);

        void networkOperationStarted();

        void networkOperationFinish();

        void showAddressInGoogle(Address address);

        void showCommentDisplay(CommentInformation commentInformation);

        void showCommentInput(Address address);

        void showToast(String message);

        void closeActivity();
    }

    interface Presenter{

        void setInfo(Session session, Address address);

        void getAddressInformation();

        void changeAddressType();

        void googleLinkClick();

        void addCommentButtonClick();
    }

    interface Interactor{

        void getAddressInformation(Address address, DatabaseCallback.AddressInformationCallBack callback);

        void changeAddressType(String username, Address address, DatabaseCallback.AddressTypeChangeCallback callback);
    }

}
