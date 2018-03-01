package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Jason on 23-Feb-18.
 */

public class CorrectInvalidAddressesPresenter implements MvpCorrectInvalidAddresses.Presenter, ApiPresenterCallBack{

    private ArrayList<String> invalidAddressesList;

    private MvpCorrectInvalidAddresses.View view;
    private MvpCorrectInvalidAddresses.Interactor interactor;

    @Inject
    public CorrectInvalidAddressesPresenter(MvpCorrectInvalidAddresses.View view, MvpCorrectInvalidAddresses.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setUpInvalidAddressesList(ArrayList<String> invalidAddressesList) {
        this.invalidAddressesList = invalidAddressesList;
    }

    @Override
    public void setUpRecyclerView() {
        view.setUpAdapter(invalidAddressesList);
    }

    @Override
    public void onRemoveAddressButtonClick(int position) {
        invalidAddressesList.remove(position);
        view.removeAddressFromList(position);
    }

    @Override
    public void onItemClick(int position) {
        view.showReformAddressDialog(position, invalidAddressesList.get(position));
    }

    @Override
    public void correctAddress(int position, String correctedAddress) {
        invalidAddressesList.set(position, correctedAddress);
        view.updateList(position);
    }

    @Override
    public void submitCorrectedAddresses() {
        interactor.submitAddresses(this, invalidAddressesList);
    }

    @Override
    public void processApiResponse(ApiResponse apiResponse) {
        //do nothing?
        view.showToast("Responded");
    }

    @Override
    public void onApiResponseFailure() {
        view.showToast("Connection failed");
    }
}
