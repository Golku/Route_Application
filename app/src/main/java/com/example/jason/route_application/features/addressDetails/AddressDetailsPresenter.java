package com.example.jason.route_application.features.addressDetails;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.models.AddressFormatter;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.CommentInformation;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.database.AddressInformationResponse;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class AddressDetailsPresenter implements MvpAddressDetails.Presenter, DatabaseCallback.AddressInformationCallBack {

    private final MvpAddressDetails.View view;
    private MvpAddressDetails.Interactor interactor;

    private Session session;
    private Address address;

    @Inject
    AddressDetailsPresenter(MvpAddressDetails.View view, MvpAddressDetails.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setInfo(Session session, Address address) {
        this.session = session;
        this.address = address;
    }

    @Override
    public void getAddressInformation() {
        view.onStartNetworkOperation();
        interactor.getAddressInformation(this, address);
    }

    @Override
    public void changeAddressType() {
        interactor.changeAddressType(session.getUsername(), address);
    }

    @Override
    public void googleLinkClick() {
        view.showAddressInGoogle(address);
    }

    @Override
    public void addCommentButtonClick() {
        view.showCommentInput(address);
    }

    @Override
    public void onAddressInformationResponse(AddressInformationResponse response) {
        view.onFinishNetworkOperation();
        view.updateMessageToUserTextView(response.getMessage());
        if(response.isInformationAvailable()){
            if(response.getAddressInformation() != null){
                if(response.getAddressInformation().getCommentsCount()>0){
                    view.setUpAdapter(response.getAddressInformation());
                }
            }
        }
    }

    @Override
    public void onAddressInformationResponseFailure() {
        view.showToast("Unable to connect to the database");
    }
}
