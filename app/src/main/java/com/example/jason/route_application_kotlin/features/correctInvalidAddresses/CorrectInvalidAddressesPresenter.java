package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.os.Handler;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.api.CorrectedAddresses;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Jason on 23-Feb-18.
 */

public class CorrectInvalidAddressesPresenter implements MvpCorrectInvalidAddresses.Presenter, ApiCallback.RouteResponseCallback {

    private final String log_tag = "logTagDebug";

    private List<String> invalidAddressesList;
    private CorrectedAddresses correctedAddresses;

    private MvpCorrectInvalidAddresses.View view;
    private MvpCorrectInvalidAddresses.Interactor interactor;

    private String routeCode;
    private int networkFetchAttempts;
    private final Handler handler;

    @Inject
    public CorrectInvalidAddressesPresenter(MvpCorrectInvalidAddresses.View view, MvpCorrectInvalidAddresses.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.handler = new Handler();
    }

    @Override
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    @Override
    public void checkForInvalidAddresses() {
        view.onStartNetworkOperation();
        networkFetchAttempts++;
        interactor.getInvalidAddresses(this, routeCode);
    }

    @Override
    public void onItemClick(int position) {
        view.showAlertDialog(position, invalidAddressesList.get(position));
    }

    @Override
    public void onRemoveAddressButtonClick(int position) {
        invalidAddressesList.remove(position);
        view.removeAddressFromList(position);
    }

    @Override
    public void correctAddress(int position, String correctedAddress) {
        invalidAddressesList.set(position, correctedAddress);
        view.updateList(position);
    }

    @Override
    public void submitCorrectedAddresses() {
        view.hideScreenElements();
        view.onStartNetworkOperation();
        correctedAddresses.setRouteCode(routeCode);
        correctedAddresses.setCorrectedAddressesList(invalidAddressesList);
        interactor.submitCorrectedAddresses(this, correctedAddresses);
    }

    private void onValidatingAddresses(){
        if(networkFetchAttempts<5){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkForInvalidAddresses();
                }
            }, 5000);
        }else{
            view.showToast("Still validating after 5 fetch attempts");
        }
    }

    private void onHasInvalidAddresses(List<String> invalidAddressesList) {
        view.onFinishNetworkOperation();

        if(invalidAddressesList != null){
            this.invalidAddressesList = invalidAddressesList;
            networkFetchAttempts = 0;
            this.correctedAddresses = new CorrectedAddresses();
            view.setupAlertDialog();
            view.showScreenElements();
            view.setupAdapter(this.invalidAddressesList);
        }else {
            view.showToast("Api din't send the addresses properly. Please try again.");
        }
    }

    @Override
    public void onRouteResponse(RouteResponse response) {
        int routeState = response.getRouteState();

        switch (routeState) {
            case 1 : onValidatingAddresses();
                break;
            case 4 : onHasInvalidAddresses(response.getInvalidAddresses());
                break;
            default: view.closeActivity();
        }
    }

    @Override
    public void onRouteResponseFailure() {
        view.showToast("Unable to connect to the api");
        view.closeActivity();
    }

}
