package com.example.jason.route_application_kotlin.features.addressDetails;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;
import com.example.jason.route_application_kotlin.data.models.AddressFormatter;
import com.example.jason.route_application_kotlin.data.pojos.database.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.database.DatabaseResponse;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class AddressDetailsPresenter implements MvpAddressDetails.Presenter, DatabaseCallback {

    @Inject
    AddressFormatter addressFormatter;

    private final String log_tag = "addressDetailsLogTag";

    private final MvpAddressDetails.View view;
    private MvpAddressDetails.Interactor interactor;

    private FormattedAddress formattedAddress;

    @Inject
    AddressDetailsPresenter(MvpAddressDetails.View view, MvpAddressDetails.Interactor interactor) {
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
    public void onDatabaseResponse(DatabaseResponse databaseResponse) {

        if(!view.isActive()){
            return;
        }

        view.onFinishNetworkOperation();

        if(databaseResponse.isError()){
            view.showToast(databaseResponse.getErrorMessage());
        }else{

            if(databaseResponse.isInformationAvailable()){

                if(databaseResponse.getAddressInformation() == null){

                    view.updateBusinessImageView("");
                    view.updateMessageToUserTextView( true,"There are no comments");

                }else{

                    if(databaseResponse.getAddressInformation().getBusiness() == 1) {
                        view.updateBusinessImageView("yes");
                    }else{
                        view.updateBusinessImageView("");
                    }

                    if(databaseResponse.getAddressInformation().getCommentsCount()>0){
                        view.updateMessageToUserTextView(false, "");
                    }else{
                        view.updateMessageToUserTextView(true, "There are no comments");
                    }

                    view.setUpAdapter(databaseResponse.getAddressInformation());

                }

            }else{
                view.updateBusinessImageView("");
                view.updateMessageToUserTextView( true,"There are no comments");
            }
        }
    }

    @Override
    public void onApiResponseFailure() {
        if(!view.isActive()){
            return;
        }
        view.showToast("Unable to connect to the database");
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
