package com.example.jason.route_application.features.container.driveListFragment;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.Event;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.features.shared.BasePresenter;
import com.example.jason.route_application.features.shared.MvpBasePresenter;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public class DriveListPresenter extends BasePresenter implements
        MvpBasePresenter,
        MvpDriveList.Presenter,
        DriveListAdapter.AdapterCallback {

    private final String debugTag = "debugTag";

    private MvpDriveList.View view;

    private List<Drive> completeDriveList;
    private List<Drive> driveList;
    private DriveListAdapter adapter;

    DriveListPresenter(MvpDriveList.View view, List<Drive> completeDriveList, List<Drive> driveList) {
        this.view = view;
        this.driveList = driveList;
        this.completeDriveList = completeDriveList;
    }

    @Override
    public void showDriveList() {
        adapter = new DriveListAdapter(this, driveList);
        view.setupAdapter(adapter);
    }

    @Override
    public void itemClick(Address address) {
        createEvent("container", "itemClick", address,this);
    }

    @Override
    public void goButtonClick(String address) {
        createEvent("container", "driveDirections", address,this);
    }

    @Override
    public void driveCompleted(Drive drive) {
        drive.setDone(1);
        adapter.notifyItemRemoved(driveList.indexOf(drive));
        driveList.remove(drive);

//        createEvent("container","updateEndTime",this);
    }

    @Override
    public void publishEvent(Event event) {
        view.postEvent(event);
    }

    @Override
    public void eventReceived(Event event) {

        if(!(event.getReceiver().equals("driveFragment") || event.getReceiver().equals("all"))){
            return;
        }

        Log.d(debugTag, "Event received on driveFragment: "+ event.getEventName());

        switch (event.getEventName()) {
            case "addressTypeChange" : addressTypeChange(event.getAddress());
                break;
            case "updateList" : updateList(event.getDriveList());
                break;
            case "addDrive" : addDriveToList(event.getDrive());
                break;
            case "removeDrive" : removeDriveFromList();
                break;
            case "RemoveMultipleDrive" : RemoveMultipleDrive(event.getAddressString());
                break;
        }
    }

    private void addressTypeChange(Address address){

        for(Drive drive: driveList){

            if(drive.getDestinationAddress().getAddress().equals(address.getAddress())){

                if(address.isBusiness()){
                    drive.setDestinationIsABusiness(true);
                }else{
                    drive.setDestinationIsABusiness(false);
                }

                showDriveList();
                break;
            }
        }
    }

    private void updateList(List<Drive> driveList) {
        this.driveList = driveList;
        showDriveList();
    }

    private void addDriveToList(Drive drive){
        adapter.notifyItemInserted(driveList.indexOf(drive));
        view.scrollToItem(driveList.size());
    }

    private void removeDriveFromList(){
        int position = driveList.size() - 1;
        driveList.remove(position);
        adapter.notifyItemRemoved(position);
        view.scrollToItem(driveList.size());

        createEvent("container","updateEndTime",this);
    }

    private void RemoveMultipleDrive(String address){

        for (Drive drive : driveList) {
            if (address.equals(drive.getDestinationAddress().getAddress())) {
                driveList.subList(driveList.indexOf(drive), driveList.size()).clear();
                break;
            }
        }

        showDriveList();
        view.scrollToItem(driveList.size());

        createEvent("container", "updateEndTime", this);
    }
}