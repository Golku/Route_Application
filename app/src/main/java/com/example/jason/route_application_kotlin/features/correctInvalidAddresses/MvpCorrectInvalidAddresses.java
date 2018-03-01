package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import com.example.jason.route_application_kotlin.data.api.ApiPresenterCallBack;

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
    }

    interface Presenter{
        void setUpInvalidAddressesList(ArrayList<String> invalidAddressesList);
        void setUpRecyclerView();
        void onRemoveAddressButtonClick(int position);
        void onItemClick(int position);
        void correctAddress(int position, String correctedAddress);
        void submitCorrectedAddresses();
    }

    interface Interactor{
        void submitAddresses(ApiPresenterCallBack apiPresenterCallBack, ArrayList<String> correctedAddresses);
    }

}
