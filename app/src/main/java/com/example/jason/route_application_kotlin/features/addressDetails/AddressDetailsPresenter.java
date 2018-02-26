package com.example.jason.route_application_kotlin.features.addressDetails;

import android.util.Log;

import com.example.jason.route_application_kotlin.data.database.DatabasePresenterCallBack;
import com.example.jason.route_application_kotlin.data.models.AddressFormatter;
import com.example.jason.route_application_kotlin.data.pojos.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.DatabaseResponse;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class AddressDetailsPresenter implements MvpAddressDetails.Presenter, DatabasePresenterCallBack {

    @Inject
    AddressFormatter addressFormatter;

    private final String log_tag = "addressDetailsLogTag";

    private final MvpAddressDetails.View view;
    private MvpAddressDetails.Interactor interactor;

    private FormattedAddress formattedAddress;

    @Inject
    public AddressDetailsPresenter(MvpAddressDetails.View view, MvpAddressDetails.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void formatAddress(String address) {
        formattedAddress = addressFormatter.formatAddress(address);
    }

    @Override
    public void updateTextViews() {
        view.setUpAddressInformation(formattedAddress);
    }

    @Override
    public void getAddressInformation() {
        view.onStartNetworkOperation();
        interactor.getAddressInformation(this, formattedAddress);
    }

    @Override
    public void processDatabaseResponse(DatabaseResponse databaseResponse) {

        view.onFinishNetworkOperation();
        if(databaseResponse.isError()){
            view.showErrorMessageToUser(databaseResponse.getErrorMessage());
        }else{
            if(databaseResponse.getAddressInformation() == null){
                view.updateMessageToUserTextView( true,"There are no comments");
            }else{
                if(databaseResponse.getAddressInformation().getCommentsCount()>0){
                    view.updateMessageToUserTextView(false, "");
                }else{
                    view.updateMessageToUserTextView(true, "There are no comments");
                }
                if(databaseResponse.getAddressInformation().getBusiness() == 1) {
                    view.updateBusinessTextView("Yes");
                }
                view.setUpAdapter(databaseResponse.getAddressInformation());
            }
        }
    }

    @Override
    public void onApiResponseFailure() {
        view.onFinishNetworkOperation();
        view.updateMessageToUserTextView(false, "");
        view.showToast("Connection Failed");
    }

    @Override
    public void onGoogleLinkClick() {
        view.showAddressInGoogle(formattedAddress);
    }

    @Override
    public void onListItemClick(CommentInformation commentInformation) {
        view.showCommentDisplay(commentInformation);
    }

    @Override
    public void onAddCommentButtonClick() {
        view.showCommentInput(formattedAddress);
    }


}
