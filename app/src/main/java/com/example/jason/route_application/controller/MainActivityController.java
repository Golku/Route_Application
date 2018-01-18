package com.example.jason.route_application.controller;

import com.example.jason.route_application.model.AddressFormatter;
import com.example.jason.route_application.model.pojos.FormattedAddress;
import com.example.jason.route_application.model.pojos.SingleAddress;
import com.example.jason.route_application.view.MainActivity;

import java.util.ArrayList;

public class MainActivityController {

    private ArrayList<SingleAddress> listOfData;
    private MainActivity mainActivity;
    private AddressFormatter addressFormatter;

    public MainActivityController(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
        this.listOfData = new ArrayList<>();
        this.addressFormatter = new AddressFormatter();

        getListFromSource();
    }

    public void getListFromSource(){

        mainActivity.setUpAdapterAndView(listOfData);
    }

    public FormattedAddress formatAddress(String address){
        return addressFormatter.formatAddress(address);
    }

    public void onListAddressClick(SingleAddress addressItem){

        mainActivity.startAddressDetailsActivity(addressItem.getAddress());

    }

    public void onListItemSwiped(int position, SingleAddress addressItem){
        mainActivity.deleteAddressListItemAt(position);
    }
}
