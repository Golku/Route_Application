package com.example.jason.route_application.features.addressDetails;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.models.AddressFormatter;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.CommentInformation;
import com.example.jason.route_application.data.pojos.database.AddressInformationResponse;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class AddressDetailsPresenter implements MvpAddressDetails.Presenter, DatabaseCallback.AddressInformationCallBack {

    @Inject
    AddressFormatter addressFormatter;

    private final MvpAddressDetails.View view;
    private MvpAddressDetails.Interactor interactor;

    private Address address;

    @Inject
    AddressDetailsPresenter(MvpAddressDetails.View view, MvpAddressDetails.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void formatAddress(String address) {
        this.address = addressFormatter.formatAddress(address);
    }

    @Override
    public void updateTextViews() {
        view.setUpAddressInformation(address);
    }

    @Override
    public void getAddressInformation() {
        view.onStartNetworkOperation();
        interactor.getAddressInformation(this, address);
    }

    @Override
    public void onAddressInformationResponse(AddressInformationResponse response) {
        if(!view.isActive()){
            return;
        }

        view.onFinishNetworkOperation();

        if(response.isInformationAvailable()){
            if(response.getAddressInformation() != null){
                if(response.getAddressInformation().getBusiness() == 1) {
                    view.updateBusinessImageView("yes");
                }else{
                    view.updateBusinessImageView("");
                }

                if(response.getAddressInformation().getCommentsCount()>0){
                    view.updateMessageToUserTextView(false, "");
                }else{
                    view.updateMessageToUserTextView(true, "There are no comments");
                }

                view.setUpAdapter(response.getAddressInformation());
            }else{
                view.updateBusinessImageView("");
                view.updateMessageToUserTextView( true,"There are no comments");
            }
        }else{
            view.updateBusinessImageView("");
            view.updateMessageToUserTextView( true,"There are no comments");
        }
    }

    @Override
    public void onAddressInformationResponseFailure() {
        if(!view.isActive()){
            return;
        }
        view.showToast("Unable to connect to the database");
    }

    @Override
    public void onGoogleLinkClick() {
        view.showAddressInGoogle(address);
    }

    @Override
    public void onListItemClick(CommentInformation commentInformation) {
        view.showCommentDisplay(commentInformation);
    }

    @Override
    public void onAddCommentButtonClick() {
        view.showCommentInput(address);
    }


}
