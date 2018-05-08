package com.example.jason.route_application.features.container.addressListFragment;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.FragmentDelegation;
import java.util.List;

public class AddressListPresenter implements MvpAddressList.Presenter, AddressListAdapter.AdapterCallback{

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
    public void onDelegation(FragmentDelegation delegation) {

        String operation = delegation.getOperation();

        int position = delegation.getPosition();

        switch (operation) {
            case "add" : addItemToList(position);
                break;
            case "remove" : removeItemFromList(position);
                break;
        }
    }

    @Override
    public void onItemClick(Address address) {
        if (address.isValid()) {
            view.listItemClick(address.getAddress());
        }else{

        }
    }

    private void addItemToList(int position){
        adapter.notifyItemInserted(position);
        view.scrollToLastItem(position);
    }

    private void removeItemFromList(int position){
        adapter.notifyItemRemoved(position);
        view.scrollToLastItem(position);
    }
}
