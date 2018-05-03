package com.example.jason.route_application.features.container.listFragment;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application.data.pojos.api.Drive;

import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public class ContainerListPresenter implements MvpContainerList.Presenter,
        ContainerAddressesAdapter.AddressAdapterFunctions,
        ContainerRouteAdapter.RouteAdapterFunctions {

    private MvpContainerList.View view;

    private List<Address> addressList;
    private List<Drive> routeList;

    private ContainerAddressesAdapter addressAdapter;
    private ContainerRouteAdapter routeAdapter;

    ContainerListPresenter(MvpContainerList.View view) {
        this.view = view;
    }

    @Override
    public void setupList(List<Address> addressList, List<Drive> routeList) {
        this.addressList = addressList;
        this.routeList = routeList;

        showAddressList();
    }

    @Override
    public void showAddressList() {
        addressAdapter = new ContainerAddressesAdapter(this, addressList);
        view.setupAddressAdapter(addressAdapter);
    }

    @Override
    public void showRouteList() {
        routeAdapter = new ContainerRouteAdapter(this, routeList);
        view.setupRouteAdapter(routeAdapter);
    }

    @Override
    public void onDelegation(RouteListFragmentDelegation delegation) {

        String operation = delegation.getOperation();
        String listIdentifier = delegation.getListIdentifier();

        int position = delegation.getPosition();

        switch (operation) {
            case "add" : add(listIdentifier, position);
                break;
            case "remove" : remove(listIdentifier, position);
                break;
            case "multipleRemove" : removeMultiple(position);
                break;
        }
    }

    private void add(String listIdentifier, int position){
        if(listIdentifier.equals("addressList")){
            addressAdapter.notifyItemInserted(position);
        }else if(listIdentifier.equals("routeList")){
            routeAdapter.notifyItemInserted(position);
        }
        view.scrollToLastItem(position);
    }

    private void remove(String listIdentifier, int position){
        if(listIdentifier.equals("addressList")){
            addressAdapter.notifyItemRemoved(position);
        }else if(listIdentifier.equals("routeList")){
            routeAdapter.notifyItemRemoved(position);
        }
        view.scrollToLastItem(position);
    }

    private void removeMultiple(int position){
        routeAdapter.notifyDataSetChanged();
        view.scrollToLastItem(position);
    }

    @Override
    public void addressAdapterItemClick(String address) {
        view.listItemClick(address);
    }

    @Override
    public void routeAdapterItemClick(String address) {
        view.listItemClick(address);
    }

    @Override
    public void routeAdapterGoButtonClick(String address) {
        view.goButtonClick(address);
    }
}
