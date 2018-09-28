package com.example.jason.route_application.data.models;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.features.container.driveListFragment.DriveListAdapter;
import com.example.jason.route_application.features.container.driveListFragment.DriveListPresenter;

import java.util.ArrayList;
import java.util.List;

public class DriveListHandler {

    private List<Drive> driveList;
    private List<Drive> completedDrives;
    private DriveListAdapter adapter;
    private DriveListPresenter callback;
    private boolean completeDrivesVisible;

    public DriveListHandler(List<Drive> driveList, DriveListPresenter callback) {
        this.driveList = driveList;
        this.callback = callback;
        completedDrives = new ArrayList<>();
        completeDrivesVisible = false;
    }

    private void createAdapter(){
        adapter = new DriveListAdapter(callback, driveList);
    }

    public DriveListAdapter getAdapter(){
        return adapter;
    }

    public void updateList(List<Drive> driveList) {
        this.driveList = driveList;
        createAdapter();
    }

    public int getListSize(){
        return driveList.size();
    }

    public void showCompletedDrives() {

        if(completeDrivesVisible){
            completeDrivesVisible = false;
        }else{
            completeDrivesVisible = true;
        }

    }

    public void addressTypeChange(Address address) {

        for (Drive drive : driveList) {

            if (drive.getDestinationAddress().getAddress().equals(address.getAddress())) {

                if (address.isBusiness()) {
                    drive.setDestinationIsABusiness(true);
                } else {
                    drive.setDestinationIsABusiness(false);
                }

                break;
            }
        }

        createAdapter();
    }

    public void addDriveToList(Drive drive) {
        adapter.notifyItemInserted(driveList.indexOf(drive));
    }

    public void driveCompleted(Drive drive){
        drive.setDone(1);
        completedDrives.add(drive);
        adapter.notifyItemRemoved(driveList.indexOf(drive));
        driveList.remove(drive);
    }

    public void removeDriveFromList() {
        int position = driveList.size() - 1;
        driveList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void removeMultipleDrive(String address) {

        for (Drive drive : driveList) {
            if (address.equals(drive.getDestinationAddress().getAddress())) {
                driveList.subList(driveList.indexOf(drive), driveList.size()).clear();
                break;
            }
        }

        createAdapter();
    }
}
