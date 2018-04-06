package com.example.jason.route_application_kotlin.features.addressDetails;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;
import com.example.jason.route_application_kotlin.data.pojos.database.AddressInformation;
import com.example.jason.route_application_kotlin.data.pojos.database.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpAddressDetails {

    interface View{
        void setUpAddressInformation(FormattedAddress formattedAddress);
        void setUpAdapter(AddressInformation addressInformation);
        void onGoogleLinkClick();
        void onAddCommentButtonClick();
        void showAddressInGoogle(FormattedAddress formattedAddress);
        void showCommentDisplay(CommentInformation commentInformation);
        void showCommentInput(FormattedAddress formattedAddress);
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
        void getAddressInformation(DatabaseCallback databaseCallback, FormattedAddress formattedAddress);
    }

}
