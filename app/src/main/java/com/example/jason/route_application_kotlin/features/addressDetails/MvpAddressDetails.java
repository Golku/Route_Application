package com.example.jason.route_application_kotlin.features.addressDetails;

import com.example.jason.route_application_kotlin.data.pojos.AddressInformation;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpAddressDetails {

    interface View{
        void setUpAdapter(AddressInformation addressInformation);
    }

    interface Presenter{
        void getAddressInformation();
    }

    interface Interactor{

    }

}
