package com.example.jason.route_application.features.container.routeListFragment;

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

    private List<Drive> driveList;
    private DriveListAdapter adapter;
    private SimpleDateFormat sdf;

    DriveListPresenter(MvpDriveList.View view, List<Drive> driveList) {
        this.view = view;
        this.driveList = driveList;
        sdf = new SimpleDateFormat("kk:mm");
    }

    @Override
    public void showDriveList() {
        adapter = new DriveListAdapter(this, driveList);
        view.setupAdapter(adapter);
    }

    @Override
    public void itemClick(String address) {
        createEvent("container", "itemClick", this);
    }

    @Override
    public void goButtonClick(String address) {
        createEvent("container", "driveDirections", address,this);
    }

    @Override
    public void eventReceived(Event event) {

        if(!(event.getReceiver().equals("driveFragment") || event.getReceiver().equals("all"))){
            return;
        }

        Log.d(debugTag, "Event received on driveFragment: "+ event.getEventName());

        switch (event.getEventName()) {
            case "updateList" : updateList(event.getDriveList());
                break;
            case "addDrive" : addDrive(event.getDrive());
                break;
            case "removeDrive" : removeDrive();
                break;
            case "RemoveMultipleDrive" : RemoveMultipleDrive(event.getAddressString());
                break;
        }
    }

    private void updateList(List<Drive> driveList) {
        this.driveList = driveList;
        showDriveList();
    }

    private void addDrive(Drive drive){

        driveList.add(drive);

        long deliveryTime;
        long driveTime = drive.getDriveDurationInSeconds() * 1000;
        long PACKAGE_DELIVERY_TIME = 120000;

        if (driveList.size() > 1) {
            Drive previousDrive = driveList.get(driveList.indexOf(drive) - 1);
            deliveryTime = previousDrive.getDeliveryTimeInMillis() + driveTime + PACKAGE_DELIVERY_TIME;
        } else {
            long date = System.currentTimeMillis();
            deliveryTime = date + driveTime + PACKAGE_DELIVERY_TIME;
        }

        String deliveryTimeString = sdf.format(deliveryTime);

        drive.setDeliveryTimeInMillis(deliveryTime);
        drive.setDeliveryTimeHumanReadable(deliveryTimeString);

        adapter.notifyItemInserted(driveList.indexOf(drive));
        view.scrollToItem(driveList.size());

        createEvent("container", "updateEndTime", this);
    }

    private void removeDrive(){
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

    @Override
    public void publishEvent(Event event) {
        view.postEvent(event);
    }
}