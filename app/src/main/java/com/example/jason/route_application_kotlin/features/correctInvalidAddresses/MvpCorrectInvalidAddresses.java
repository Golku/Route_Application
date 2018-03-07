package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;
import com.example.jason.route_application_kotlin.data.pojos.CorrectedAddresses;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;

import java.util.ArrayList;

/**
 * Created by Jason on 23-Feb-18.
 */

public interface MvpCorrectInvalidAddresses {

    interface View{

        void setUpAdapter(ArrayList<String> invalidAddressesList);
        void removeAddressFromList(int position);
        void showReformAddressDialog(int position, String address);
        void showToast(String message);
        void onDialogChangeAddressBtnClick(int position, String correctedAddress);
        void updateList(int position);

        void onStartNetworkOperation();
        void onFinishNetworkOperation();
        void startRouteActivity(String routeCode);
        void setUpView();
        void closeActivity();
        void showScreenElements();
        void hideScreenElements();
    }

    interface Presenter{
        void onRemoveAddressButtonClick(int position);
        void onItemClick(int position);
        void correctAddress(int position, String correctedAddress);
        void submitRoute(OutGoingRoute outGoingRoute);
        void checkForInvalidAddresses();
        void submitCorrectedAddresses();
    }

    interface Interactor{
        void submitRoute(ApiPresenterCallBack apiPresenterCallBack, OutGoingRoute outGoingRoute);
        void getInvalidAddresses(ApiPresenterCallBack apiPresenterCallBack, String routeCode);
        void submitCorrectedAddresses(ApiPresenterCallBack apiPresenterCallBack, CorrectedAddresses correctedAddresses);
    }

}
