package com.example.jason.route_application.features.container.routeListFragment;

import com.example.jason.route_application.data.pojos.FragmentDelegation;
import com.example.jason.route_application.data.pojos.api.Drive;
import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public class RouteListPresenter implements MvpRouteList.Presenter, RouteListAdapter.AdapterCallback {

    private MvpRouteList.View view;

    private List<Drive> routeList;
    private RouteListAdapter adapter;

    RouteListPresenter(MvpRouteList.View view, List<Drive> routeList) {
        this.view = view;
        this.routeList = routeList;
    }

    @Override
    public void showRouteList() {
        adapter = new RouteListAdapter(this, routeList);
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
            case "multipleRemove" : removeMultiple(position);
                break;
        }
    }

    @Override
    public void onItemClick(String address) {
        view.listItemClick(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        view.goButtonClick(address);
    }

    private void addItemToList(int position){
        adapter.notifyItemInserted(position);
        view.scrollToLastItem(position);
    }

    private void removeItemFromList(int position){
        adapter.notifyItemRemoved(position);
        view.scrollToLastItem(position);
    }

    private void removeMultiple(int position){
        adapter.notifyDataSetChanged();
        view.scrollToLastItem(position);
    }
}
