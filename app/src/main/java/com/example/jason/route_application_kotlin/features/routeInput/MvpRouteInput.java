package com.example.jason.route_application_kotlin.features.routeInput;

import com.example.jason.route_application_kotlin.data.pojos.AddressItem;

/**
 * Created by Jason on 07-Feb-18.
 */

interface MvpRouteInput {

    interface View{

        void addAddressToList();

        void showAddressDetails(AddressItem addressItem);

        void beginRoute();

    }

    interface Presenter{

        void onItemClick(AddressItem addressItem);

    }

    interface Interactor{

    }

}
