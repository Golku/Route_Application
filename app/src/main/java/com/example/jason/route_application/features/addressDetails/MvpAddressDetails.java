package com.example.jason.route_application.features.addressDetails;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.database.AddressInformation;
import com.example.jason.route_application.data.pojos.CommentInformation;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpAddressDetails {

    interface View{
        void setUpAddressInformation(Address address);
        void setUpAdapter(AddressInformation addressInformation);
        void onGoogleLinkClick();
        void onAddCommentButtonClick();
        void showAddressInGoogle(Address address);
        void showCommentDisplay(CommentInformation commentInformation);
        void showCommentInput(Address address);
        void updateMessageToUserTextView(boolean visible, String message);
        void updateBusinessImageView(String business);
        void onStartNetworkOperation();
        void onFinishNetworkOperation();
        boolean isActive();
        void showToast(String message);
        void closeActivity();
    }

    interface Presenter{
        void formatAddress(String address);
        void updateTextViews();
        void getAddressInformation();
        void onGoogleLinkClick();
        void onListItemClick(CommentInformation commentInformation);
        void onAddCommentButtonClick();
    }

    interface Interactor{
        void getAddressInformation(DatabaseCallback.AddressInformationCallBack callBack, Address address);
    }

}
