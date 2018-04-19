package com.example.jason.route_application_kotlin.features.routeInput;

import java.util.ArrayList;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRouteInput {

    interface View{

        void setUpAdapter(ArrayList<String> listOfAddresses);

        void onAddAddressButtonClick();

        void onSubmitRouteButtonClick();

        void addAddressToList(int listSize);

        void removeAddressFromList(int position);

        void showAddressDetails(String address);

        void startRoute(ArrayList<String> listOfAddresses);

        void updateListSizeTextView(int listSize);

        void showToast(String message);

    }

    interface Presenter{

        void setUpView();

        void submitRouteRoute();

        void addAddressToList(String address);

        void onListItemClick(String address);

        void onListItemSwiped(int position);

    }

}
