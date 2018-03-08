package com.example.jason.route_application_kotlin.features.routeInput;

import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteInputPresenter implements MvpRouteInput.Presenter{

    private final MvpRouteInput.View view;
    private ArrayList<String> listOfAddresses;

    @Inject
    public RouteInputPresenter(MvpRouteInput.View view) {
        this.view =  view;
        listOfAddresses = new ArrayList<>();
    }

    @Override
    public void setUpView() {
        view.setUpAdapter(listOfAddresses);
        view.updateListSizeTextView(listOfAddresses.size());
    }

    @Override
    public void addAddressToList(String address) {
        listOfAddresses.add(address);
        view.addAddressToList(listOfAddresses.size());
        view.updateListSizeTextView(listOfAddresses.size());
    }

    @Override
    public void onListItemSwiped(int position) {
        listOfAddresses.remove(position);
        view.removeAddressFromList(position);
        view.updateListSizeTextView(listOfAddresses.size());
    }

    @Override
    public void onListItemClick(String address) {
        view.showAddressDetails(address);
    }

    @Override
    public void submitRouteRoute() {
        view.startRoute(listOfAddresses);
    }
}
