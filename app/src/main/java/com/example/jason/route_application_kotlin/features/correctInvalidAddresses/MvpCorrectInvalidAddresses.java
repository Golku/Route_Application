package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.api.CorrectedAddresses;
import java.util.List;

/**
 * Created by Jason on 23-Feb-18.
 */

public interface MvpCorrectInvalidAddresses {

    interface View{

        void setupAdapter(List<String> invalidAddressesList);

        void setupAlertDialog();

        void showAlertDialog(int position, String address);

        void updateList(int position);

        void removeAddressFromList(int position);

        void onStartNetworkOperation();

        void onFinishNetworkOperation();

        void showScreenElements();

        void hideScreenElements();

        void showToast(String message);

        void closeActivity();

    }

    interface Presenter{

        void setRouteCode(String routeCode);

        void checkForInvalidAddresses();

        void onItemClick(int position);

        void onRemoveAddressButtonClick(int position);

        void correctAddress(int position, String correctedAddress);

        void submitCorrectedAddresses();

    }

    interface Interactor{

        void getInvalidAddresses(ApiCallback.RouteResponseCallback callback, String routeCode);

        void submitCorrectedAddresses(ApiCallback.RouteResponseCallback callback, CorrectedAddresses correctedAddresses);

    }

}
