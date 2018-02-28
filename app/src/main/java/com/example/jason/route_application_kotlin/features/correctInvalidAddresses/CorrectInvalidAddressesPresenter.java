package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Jason on 23-Feb-18.
 */

public class CorrectInvalidAddressesPresenter implements MvpCorrectInvalidAddresses.Presenter {

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
        view.showReformAddressDialog();
    }
}
