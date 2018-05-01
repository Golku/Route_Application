package com.example.jason.route_application.features.routeInput;

import com.example.jason.route_application.data.api.ApiCallback;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.RouteRequest;
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

        void updateListSizeTextView(int listSize);

        void showToast(String message);

        void closeActivity();

    }

    interface Presenter{

        void setUpView(Session session);

        void submitRoute();

        void addAddressToList(String address);

        void onListItemClick(String address);

        void onListItemSwiped(int position);

    }

    interface Interactor{

        void routeRequest(RouteRequest request, ApiCallback.RouteSubmitCallback callback);

    }

}
