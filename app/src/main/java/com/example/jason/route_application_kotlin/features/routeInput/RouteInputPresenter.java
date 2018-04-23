package com.example.jason.route_application_kotlin.features.routeInput;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.api.RouteRequest;

import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteInputPresenter implements MvpRouteInput.Presenter, ApiCallback.RouteSubmitCallback{

    private MvpRouteInput.View view;
    private MvpRouteInput.Interactor interactor;
    private ArrayList<String> listOfAddresses;

    @Inject
    public RouteInputPresenter(MvpRouteInput.View view, MvpRouteInput.Interactor interactor) {
        this.view =  view;
        this.interactor = interactor;
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
    public void submitRoute() {
        RouteRequest request = new RouteRequest("username", listOfAddresses);
        interactor.routeRequest(request, this);
    }

    @Override
    public void onRouteSubmitResponse(boolean submitted) {

    }

    @Override
    public void onRouteSubmitResponseFailure() {

    }
}
