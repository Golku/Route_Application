package com.example.jason.route_application_kotlin.features.routeInput;

import android.app.Activity;
import android.util.Log;

import com.example.jason.route_application_kotlin.data.models.AddressFormatter;
import com.example.jason.route_application_kotlin.data.pojos.SingleAddress;

import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteInputPresenter implements MvpRouteInput.Presenter{

    private final RouteInputActivity view;

//    waarom werkt dit?
//    @Inject AddressFormatter addressFormatter;

    private AddressFormatter addressFormatter;

    @Inject
    public RouteInputPresenter(Activity view, AddressFormatter addressFormatter) {
        this.view = (RouteInputActivity) view;
        this.addressFormatter = addressFormatter;
    }

    public void doSomething(){
        Log.d("Dagger", "AddressFormatter: " + addressFormatter);
    }

    @Override
    public void onItemClick(SingleAddress singleAddress) {

    }

}
