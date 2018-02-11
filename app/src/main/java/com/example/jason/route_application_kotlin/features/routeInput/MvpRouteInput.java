package com.example.jason.route_application_kotlin.features.routeInput;

import com.example.jason.route_application_kotlin.data.pojos.SingleAddress;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRouteInput {

    interface View{

        void addAddressToList();

        void showAddressDetails(SingleAddress singleAddress);

        void beginRoute();

    }

    interface Presenter{

        void onItemClick(SingleAddress singleAddress);

    }

    interface Interactor {



    }

}
