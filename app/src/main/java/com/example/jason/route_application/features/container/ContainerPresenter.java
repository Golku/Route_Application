package com.example.jason.route_application.features.container;

import com.example.jason.route_application.data.api.ApiCallback;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.api.AddressRequest;
import com.example.jason.route_application.data.pojos.api.Container;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.data.pojos.FragmentEvent;
import com.example.jason.route_application.features.shared.BasePresenter;

import android.util.Log;

import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 07-Feb-18.
 */

public class ContainerPresenter extends BasePresenter implements
        MvpContainer.Presenter,
        ApiCallback.ContainerResponseCallback,
        ApiCallback.DriveResponseCallback,
        ApiCallback.AddAddressCallback {

    private final String debugTag = "debugTag";

    private MvpContainer.View view;

    private MvpContainer.Interactor interactor;

    private Session session;

    private Container container;

    private List<Address> addressList;

    private List<Drive> driveList;

    private int[] deliveryCompletion;

    private int deliveredPrivate;

    private int deliveredBusiness;

    private SimpleDateFormat sdf;

    @Inject
    public ContainerPresenter(MvpContainer.View view, MvpContainer.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        deliveryCompletion = new int[4];
        deliveredPrivate = 0;
        deliveredBusiness = 0;
        sdf = new SimpleDateFormat("kk:mm");
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void getContainer() {
        interactor.containerRequest(session.getUsername(), this);
    }

    private void getAddress(String address) {
        AddressRequest request = new AddressRequest(session.getUsername(), address);
        interactor.addressRequest(request, this);
    }

    private void getDrive(DriveRequest request) {
        interactor.driveRequest(request, this);
    }

    private void setupContainer(Container container) {

        if (this.container != null) {
            updateContainer(container);
            return;
        } else {
            this.container = container;
        }

        setupAddressList();
        setupDriveList();
        updateContainerInfo();

        RouteInfoHolder routeInfoHolder = new RouteInfoHolder();
        routeInfoHolder.setAddressList(addressList);
        routeInfoHolder.setDriveList(driveList);

        view.setupFragments(routeInfoHolder);
    }

    private void updateContainer(Container container) {
        this.container = container;

        setupAddressList();
        setupDriveList();
        updateContainerInfo();
        updateRouteEndTime();

        createActivityEvent(addressList, driveList, this);
    }

    private void setupAddressList() {
        addressList = new ArrayList<>();

        if (container.getUserAddressList() != null) {
            addressList.addAll(container.getUserAddressList());
        }

        if (container.getRouteAddressList() != null) {
            addressList.addAll(container.getRouteAddressList());
        }
    }

    private void setupDriveList() {

        driveList = new ArrayList<>();

        if (container.getDriveList() != null) {
            driveList.addAll(container.getDriveList());
        }

//        List<String> routeOrder = new ArrayList<>();
//        for (Drive drive : driveList) {
//            routeOrder.add(drive.getDestinationAddress().getAddress());
//        }
    }

    private void updateContainerInfo() {

        int privateAddressCount = container.getPrivateAddressCount();
        int businessAddressCount = container.getBusinessAddressCount();
        int invalidAddressCount = container.getInvalidAddressCount();

//        In this method update the container by loading the values that are displayed in the top information bar

//        deliveryCompletion[0] = deliveredPrivate;
//        deliveryCompletion[2] = deliveredBusiness;
//        deliveryCompletion[1] = route.getPrivateAddressCount();
//        deliveryCompletion[3] = route.getBusinessAddressCount();
//        view.updateDeliveryCompletion(deliveryCompletion);
    }

    @Override
    public void fragmentEvent(FragmentEvent fragmentEvent) {
        String event = fragmentEvent.getEvent();

        switch (event) {
            case "itemClick":
                view.showAddressDetails(fragmentEvent.getAddressString());
                break;
            case "addAddress":
                getAddress(fragmentEvent.getAddressString());
                break;
            case "removeAddress":
                removeAddress(fragmentEvent.getAddress());
                break;
            case "getDrive":
                getDrive(fragmentEvent.getDriveRequest());
                break;
            case "removeDrive":
                removeDrive();
                break;
            case "removeMultipleDrive":
                removeMultipleDrive(fragmentEvent.getAddressString());
                break;
            case "driveDirections":
                view.navigateToDestination(fragmentEvent.getAddressString());
                break;
        }
    }

    private void addAddress(Address address) {
        addressList.add(address);
        createActivityEvent("addressAdded", address, addressList.indexOf(address), this);
    }

    private void removeAddress(Address address) {

        int position = addressList.indexOf(address);

        addressList.remove(address);

        createActivityEvent("addressRemoved", address, position, this);
    }

    private void addDrive(Drive drive) {
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

        updateRouteEndTime();

        createActivityEvent("driveAdded", driveList.indexOf(drive), this);
    }

    private void removeDrive() {
        int position = driveList.size() - 1;
        driveList.remove(position);

        updateRouteEndTime();

        createActivityEvent("driveRemoved", position, this);
    }

    private void removeMultipleDrive(String destination) {
        int position = 0;

        for (Drive drive : driveList) {
            String driveDestination = drive.getDestinationAddress().getAddress();

            if (destination.equals(driveDestination)) {
                position = driveList.indexOf(drive);
                break;
            }
        }

        driveList.subList(position, driveList.size()).clear();

        updateRouteEndTime();

        createActivityEvent("multipleDriveRemoved", position, this);
    }

    @Override
    public void delegateActivityEvent(ActivityEvent activityEvent) {
        view.sendActivityEvent(activityEvent);
    }

    private void updateRouteEndTime() {
        if (driveList.size() > 0) {
            Drive finalDrive = driveList.get(driveList.size() - 1);
            view.updateRouteEndTimeTv(finalDrive.getDeliveryTimeHumanReadable());
        } else {
            view.updateRouteEndTimeTv("");
        }
    }

//        If the server has an error and sends back a routeResponse with a html page
//        the response processing will fail! FIX THIS!!!

    @Override
    public void containerResponse(Container response) {
        if (response != null) {
            setupContainer(response);
        }
    }

    @Override
    public void containerResponseFailure() {
        view.showToast("Unable to fetch container from api");
    }

    @Override
    public void addressResponse(Address response) {
        if (response != null) {
            addAddress(response);
        }
    }

    @Override
    public void addressResponseFailure() {
        view.showToast("Unable to fetch address from api");
    }

    @Override
    public void driveResponse(Drive response) {
        if (response != null) {
            addDrive(response);
        }
    }

    @Override
    public void driveResponseFailure() {
        view.showToast("Unable to fetch drive information from api");
    }
}