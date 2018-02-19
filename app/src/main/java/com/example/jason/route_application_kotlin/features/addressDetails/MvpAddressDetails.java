package com.example.jason.route_application_kotlin.features.addressDetails;

import com.example.jason.route_application_kotlin.data.database.DatabasePresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.AddressInformation;
import com.example.jason.route_application_kotlin.data.pojos.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpAddressDetails {

    interface View{
        void updateTextViews(FormattedAddress formattedAddress);
        void setUpAdapter(AddressInformation addressInformation);
        void onGoogleLinkClick();
        void onAddCommentButtonClick();
        void showAddressInGoogle(FormattedAddress formattedAddress);
        void showCommentDisplay(CommentInformation commentInformation);
        void showCommentInput(FormattedAddress formattedAddress);
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
        void getAddressInformation(DatabasePresenterCallBack databasePresenterCallBack, FormattedAddress formattedAddress);
        void addCommentToAddress();
    }

}
