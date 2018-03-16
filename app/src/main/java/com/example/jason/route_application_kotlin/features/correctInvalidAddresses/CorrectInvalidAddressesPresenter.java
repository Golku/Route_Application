package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.os.Handler;
import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.CorrectedAddresses;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Jason on 23-Feb-18.
 */

public class CorrectInvalidAddressesPresenter implements MvpCorrectInvalidAddresses.Presenter, ApiPresenterCallBack{

    private final String log_tag = "logTagDebug";

    private ArrayList<String> invalidAddressesList;
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
        networkFetchAttempts++;
        interactor.getInvalidAddresses(this, routeCode);
    }

    @Override
    public void onItemClick(int position) {
        view.showReformAddressDialog(position, invalidAddressesList.get(position));
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

    private void onHasInvalidAddresses(ArrayList<String> invalidAddressesList){

        view.onFinishNetworkOperation();

        if(invalidAddressesList != null){
            this.invalidAddressesList = invalidAddressesList;
            networkFetchAttempts = 0;
            this.correctedAddresses = new CorrectedAddresses();
            view.setUpView();
            view.showScreenElements();
            view.setUpAdapter(this.invalidAddressesList);
        }else {
            view.showToast("Api din't send the addresses properly. Please try again.");
        }

    }

    @Override
    public void onApiResponse(ApiResponse apiResponse) {

//        If the server has an error and sends back a apiResponse with a html page
//        the response processing will fail! FIX THIS!!!

        String routeState = apiResponse.getRouteState();

        switch (routeState) {
            case "validatingAddresses": onValidatingAddresses();
                break;
            case "hasInvalidAddresses": onHasInvalidAddresses(apiResponse.getInvalidAddresses());
                break;
            default: view.closeActivity();
        }

    }

    @Override
    public void onApiResponseFailure() {
        view.onFinishNetworkOperation();
        view.showToast("Connection failed");
        view.closeActivity();
    }

}
