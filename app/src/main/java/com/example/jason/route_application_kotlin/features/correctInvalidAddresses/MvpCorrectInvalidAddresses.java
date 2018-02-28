package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import java.util.ArrayList;

/**
 * Created by Jason on 23-Feb-18.
 */

public interface MvpCorrectInvalidAddresses {

    interface View{
        void setUpAdapter(ArrayList<String> invalidAddressesList);
        void removeAddressFromList(int position);
        void showReformAddressDialog();
    }

    interface Presenter{
        void setUpInvalidAddressesList(ArrayList<String> invalidAddressesList);
        void setUpRecyclerView();
        void onRemoveAddressButtonClick(int position);
        void onItemClick(int position);
    }

    interface Interactor{

    }

}
