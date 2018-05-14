package com.example.jason.route_application.features.container.addressListFragment;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.FragmentEvent;

import android.util.Log;

import java.util.List;

public class AddressListPresenter implements MvpAddressList.Presenter, AddressListAdapter.AdapterCallback{

    private final String debugTag = "debugTag";

    private MvpAddressList.View view;

    private List<Address> addressList;
    private AddressListAdapter adapter;

    AddressListPresenter(MvpAddressList.View view, List<Address> addressList) {
        this.view = view;
        this.addressList = addressList;
    }

    @Override
    public void showAddressList() {
        adapter = new AddressListAdapter(this, addressList);
        view.setupAdapter(adapter);
    }

    @Override
    public void showDialog(String title) {
        view.showAddressInputDialog(title);
    }

    @Override
    public void itemClick(Address address) {
        if (address.isValid()) {
            FragmentEvent fragmentEvent = new FragmentEvent();
            fragmentEvent.setEvent("itemClick");
            fragmentEvent.setAddressString(address.getAddress());
            view.sendFragmentEvent(fragmentEvent);
        }else{
            showDialog(address.getAddress());
        }
    }

    @Override
    public void addAddress(String address) {
        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setEvent("addAddress");
        fragmentEvent.setAddressString(address);
        view.sendFragmentEvent(fragmentEvent);
    }

    @Override
    public void activityEvent(ActivityEvent activityEvent) {

        String event = activityEvent.getEvent();
        int position = activityEvent.getPosition();

        switch (event) {
            case "showAddressDialog" : showDialog("New Address");
                break;
            case "routeUpdated" : updateAddressList(activityEvent.getAddressList());
                break;
            case "addressAdded" : addItemToList(position);
                break;
            case "addressRemoved" : removeItemFromList(position);
                break;
        }
    }


    private void updateAddressList(List<Address> addressList){
        this.addressList = addressList;
        showAddressList();
        view.scrollToItem(addressList.size());
    }

    private void addItemToList(int position){
        adapter.notifyItemInserted(position);
        view.scrollToItem(addressList.size());
    }

    private void removeItemFromList(int position){
        adapter.notifyItemRemoved(position);
    }
}
