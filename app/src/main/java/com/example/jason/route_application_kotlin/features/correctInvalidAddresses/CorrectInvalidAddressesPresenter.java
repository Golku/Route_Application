package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.os.Handler;
import android.util.Log;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.CorrectedAddresses;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Jason on 23-Feb-18.
 */

public class CorrectInvalidAddressesPresenter implements MvpCorrectInvalidAddresses.Presenter, ApiPresenterCallBack{

    private final String log_tag = "log_tag";

    private ArrayList<String> invalidAddressesList;
    private ArrayList<String> correctedAddressesList;
    private CorrectedAddresses correctedAddresses;

    private MvpCorrectInvalidAddresses.View view;
    private MvpCorrectInvalidAddresses.Interactor interactor;

    private String routeCode;
    private int routeFetchAttempt;
    private final Handler handler;

    @Inject
    public CorrectInvalidAddressesPresenter(MvpCorrectInvalidAddresses.View view, MvpCorrectInvalidAddresses.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.handler = new Handler();
        this.correctedAddresses = new CorrectedAddresses();
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



        correctedAddressesList.set(position, correctedAddress);
        view.updateList(position);
    }

    @Override
    public void submitCorrectedAddresses() {
        correctedAddresses.setRouteCode(routeCode);
        interactor.submitCorrectedAddresses(this, correctedAddresses);
    }

    @Override
    public void submitRoute(OutGoingRoute outGoingRoute) {
        this.routeCode = outGoingRoute.getRouteCode();
        view.onStartNetworkOperation();
        interactor.submitRoute(this, outGoingRoute);
    }

    @Override
    public void checkForInvalidAddresses() {
        routeFetchAttempt++;
        interactor.getInvalidAddresses(this, routeCode);
    }

    @Override
    public void processApiResponse(ApiResponse apiResponse) {

        if(apiResponse.getRouteIsNull()) {
            view.onFinishNetworkOperation();
            view.showToast("Route does not exist. Try resubmitting the route.");
        }else{

            if(apiResponse.getOrganizingInProgress()){

                if(routeFetchAttempt<5){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkForInvalidAddresses();
                        }
                    }, 5000);
                }else{
                    //message the user that the route was not fetch after x amount of attempts
                    //user can start the route fetching process again with a button click.
                    view.onFinishNetworkOperation();
                    view.showToast("Unable to fetch route after 5 attempts.");
                }

            }else{

                if(apiResponse.getRouteHasInvalidAddresses()){

                    view.onFinishNetworkOperation();

                    if(apiResponse.getInvalidAddresses() != null){
//                        remove the setUpView and setUadapter from here. Otherwise they will get call multiple times when
//                        the interactor.getInvalidAddresses return more invalid addresses after the interactor.submitCorrectedAddresses
//                        gets called
                        this.invalidAddressesList = apiResponse.getInvalidAddresses();
                        setUpCorrectedAddressesList();
                        view.setUpView();
                        view.setUpAdapter(this.correctedAddressesList);
                    }else{
                        view.showToast("Api din't send the route properly. Please try again.");
                    }

                }else{

                    view.onFinishNetworkOperation();
                    view.startRouteActivity(routeCode);

                }
            }

        }

    }

    @Override
    public void onApiResponseFailure() {
        view.onFinishNetworkOperation();
        view.showToast("Connection failed");
    }

    private void setUpCorrectedAddressesList(){
        for(int i=0; i<invalidAddressesList.size(); i++){
            correctedAddressesList.add(i, invalidAddressesList.get(i));
        }
    }

}
