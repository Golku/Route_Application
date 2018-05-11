package com.example.jason.route_application.features.container.routeListFragment;

import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.data.pojos.FragmentEvent;

import android.util.Log;

import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public class DriveListPresenter implements MvpDriveList.Presenter, DriveListAdapter.AdapterCallback {

    private final String debugTag = "debugTag";

    private MvpDriveList.View view;

    private List<Drive> driveList;
    private DriveListAdapter adapter;

    DriveListPresenter(MvpDriveList.View view, List<Drive> driveList) {
        this.view = view;
        this.driveList = driveList;
    }

    @Override
    public void showDriveList() {
        adapter = new DriveListAdapter(this, driveList);
        view.setupAdapter(adapter);
    }

    @Override
    public void itemClick(String address) {
        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setEvent("itemClick");
        fragmentEvent.setAddressString(address);
        view.sendFragmentEvent(fragmentEvent);
    }

    @Override
    public void goButtonClick(String address) {
        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setEvent("driveDirections");
        fragmentEvent.setAddressString(address);
        view.sendFragmentEvent(fragmentEvent);
    }

    @Override
    public void activityEvent(ActivityEvent activityEvent) {

        String event = activityEvent.getEvent();
        int position = activityEvent.getPosition();

        switch (event) {
            case "routeUpdated" : updateDriveList(activityEvent.getDriveList());
                break;
            case "driveAdded" : addItemToList(position);
                break;
            case "driveRemoved" : removeItemFromList(position);
                break;
            case "multipleDriveRemoved" : removeMultipleItemsFromList();
                break;
        }
    }

    private void updateDriveList(List<Drive> driveList) {
        this.driveList = driveList;
        showDriveList();
        view.scrollToItem(driveList.size());
    }

    private void addItemToList(int position){
        adapter.notifyItemInserted(position);
        view.scrollToItem(driveList.size());
    }

    private void removeItemFromList(int position){
        adapter.notifyItemRemoved(position);
        view.scrollToItem(driveList.size());
    }

    private void removeMultipleItemsFromList(){
        showDriveList();
        view.scrollToItem(driveList.size());
    }
}