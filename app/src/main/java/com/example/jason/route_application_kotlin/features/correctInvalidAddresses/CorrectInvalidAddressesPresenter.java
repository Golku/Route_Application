package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.os.Handler;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.ApiResponse;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Jason on 23-Feb-18.
 */

public class CorrectInvalidAddressesPresenter implements MvpCorrectInvalidAddresses.Presenter, ApiPresenterCallBack{

    private ArrayList<String> invalidAddressesList;

    private MvpCorrectInvalidAddresses.View view;
    private MvpCorrectInvalidAddresses.Interactor interactor;

    private String routeCode;
    private int routeFetchAttempt;
    private final Handler handler = new Handler();


    @Inject
    public CorrectInvalidAddressesPresenter(MvpCorrectInvalidAddresses.View view, MvpCorrectInvalidAddresses.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
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
    public void submitRoute(OutGoingRoute outGoingRoute) {
        this.routeCode = outGoingRoute.getRouteCode();
        view.onStartNetworkOperation();
        interactor.submitRoute(this, outGoingRoute);
    }

    @Override
    public void getRouteFromApi() {
        routeFetchAttempt++;
        interactor.getInvalidAddresses(this, routeCode);
    }

    @Override
    public void submitCorrectedAddresses() {
        interactor.submitCorrectedAddresses(this, invalidAddressesList);
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
                            getRouteFromApi();
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
                        view.setUpAlertDialog();
                        this.invalidAddressesList = apiResponse.getInvalidAddresses();
                        view.setUpAdapter(this.invalidAddressesList);
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

}
