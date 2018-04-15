package com.example.jason.route_application_kotlin.features.route;

import android.location.Location;

import com.example.jason.route_application_kotlin.data.api.ApiCallback;
import com.example.jason.route_application_kotlin.data.pojos.RouteInfoHolder;
import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.Route;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveResponse;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RoutePresenter implements
        MvpRoute.Presenter,
        ApiCallback.SingleDriveResponseCallback {

    private final String log_tag = "debugTag";

    private MvpRoute.View view;
    private MvpRoute.Interactor interactor;

    private Route route;
    private List<SingleDrive> routeList;

    private int[] deliveryCompletion;
    private int deliveredPrivate;
    private int deliveredBusiness;

    private SimpleDateFormat sdf;

    @Inject
    public RoutePresenter(MvpRoute.View view, MvpRoute.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
        deliveryCompletion = new int[4];
        deliveredPrivate = 0;
        deliveredBusiness = 0;
        sdf = new SimpleDateFormat("kk:mm");
    }

    @Override
    public void initializeRoute(Route route, Location location) {
        this.route = route;

        RouteInfoHolder routeInfoHolder = new RouteInfoHolder();

        routeInfoHolder.setAddressList(route.getAddressList());

        if (route.getRouteList() != null && !route.getRouteList().isEmpty()) {
            routeList = route.getRouteList();
            routeInfoHolder.setOrganized(true);
        } else {
            routeList = new ArrayList<>();
            routeInfoHolder.setOrganized(false);
        }

        routeInfoHolder.setUserLocation(new LatLng(location.getLatitude(), location.getLongitude()));
        routeInfoHolder.setRouteList(routeList);

        deliveryCompletion[0] = deliveredPrivate;
        deliveryCompletion[1] = this.route.getPrivateAddressCount();
        deliveryCompletion[2] = deliveredBusiness;
        deliveryCompletion[3] = this.route.getBusinessAddressCount();

        view.updateDeliveryCompletion(deliveryCompletion);
        view.setupFragments(routeInfoHolder);
    }

    @Override
    public void getDriveInformation(SingleDriveRequest request) {
        interactor.getDriveInformation(this, request);
    }

    private void addDeliveryTime(SingleDrive singleDrive) {
        long deliveryTime;
        long driveTime = singleDrive.getDriveDurationInSeconds() * 1000;
        long PACKAGE_DELIVERY_TIME = 120000;

        if (routeList.size() > 1) {
            SingleDrive previousDrive = routeList.get(routeList.indexOf(singleDrive) - 1);
            deliveryTime = previousDrive.getDeliveryTimeInMillis() + driveTime + PACKAGE_DELIVERY_TIME;
        } else {
            long date = System.currentTimeMillis();
            deliveryTime = date + driveTime + PACKAGE_DELIVERY_TIME;
        }

        String deliveryTimeString = sdf.format(deliveryTime);

        singleDrive.setDeliveryTimeInMillis(deliveryTime);
        singleDrive.setDeliveryTimeHumanReadable(deliveryTimeString);

        getRouteEndTime();

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("add");
        delegation.setPosition(routeList.indexOf(singleDrive));
        view.delegateRouteChange(delegation);
    }

    @Override
    public void markerDeselected() {
        int position = routeList.size() - 1;
        routeList.remove(position);

        getRouteEndTime();

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("remove");
        delegation.setPosition(position);
        view.delegateRouteChange(delegation);
    }

    @Override
    public void multipleMarkersDeselected(String destination) {
        int position = -1;

        for (SingleDrive singleDrive : routeList) {
            String driveDestination = singleDrive.getDestinationFormattedAddress().getFormattedAddress();

            if (destination.equals(driveDestination)) {
                position = routeList.indexOf(singleDrive);
                break;
            }
        }

        routeList.subList(position, routeList.size()).clear();

        getRouteEndTime();

        RouteListFragmentDelegation delegation = new RouteListFragmentDelegation();
        delegation.setOperation("multipleRemove");
        delegation.setPosition(position);
        view.delegateRouteChange(delegation);
    }

    private void getRouteEndTime() {
        if (routeList.size() > 0) {
            SingleDrive finalDrive = routeList.get(routeList.size() - 1);
            view.updateRouteEndTime(finalDrive.getDeliveryTimeHumanReadable());
        } else {
            view.updateRouteEndTime("");
        }
    }

    @Override
    public void onListItemClick(String address) {
        view.showAddressDetails(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        view.navigateToDestination(address);
    }

//        If the server has an error and sends back a routeResponse with a html page
//        the response processing will fail! FIX THIS!!!
    @Override
    public void onSingleDriveResponse(SingleDriveResponse response) {
        if (response.getSingleDrive() != null) {
            routeList.add(response.getSingleDrive());
            addDeliveryTime(response.getSingleDrive());
        }
    }

    @Override
    public void onSingleDriveResponseFailure() {
        view.showToast("Unable to fetch drive information from api");
    }
}
