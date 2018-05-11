package com.example.jason.route_application.features.container.mapFragment;

import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.MarkerInfo;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.data.pojos.FragmentEvent;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class MapPresenter implements MvpMap.Presenter {

    private final String logTag = "logTagDebug";

    private MvpMap.View view;

    private LatLng userLocation;

    private List<Address> addressList;
    private List<Marker> markersList;

    private Marker previousSelectedMarker;

    MapPresenter(MvpMap.View view, List<Address> addressList, List<String> routeOrder) {
        this.view = view;
        this.addressList = addressList;
        this.markersList = new ArrayList<>();
        this.previousSelectedMarker = null;
    }

    @Override
    public void setMarkers() {

        //get phone location
        Address origin = new Address();

        origin.setAddress("My location");
        origin.setUserLocation(true);
        origin.setLat(52.008234);
        origin.setLng(4.312999);

        view.markAddressOnMap(origin);

        for(Address address : addressList){
            if(address.isValid()){
                view.markAddressOnMap(address);
            }
        }

        view.moveMapCamera(52.008234, 4.312999);
    }

    @Override
    public void markerClick(Marker clickedMarker) {

        if(clickedMarker.getTitle().equals("My location")) {
            view.showToast("origin");
            return;
        }

        MarkerInfo markerInfo = (MarkerInfo) clickedMarker.getTag();

        String origin = null;
        String destination = null;

        LatLng start = null;
        LatLng end = null;

        if (previousSelectedMarker != null) {

            if (clickedMarker.equals(previousSelectedMarker)) {

                markersList.remove(clickedMarker);

                markerInfo.setSelected(false);

                if(markerInfo.isBusiness()){
                    markerInfo.setIconType("business");
                }else{
                    markerInfo.setIconType("private");
                }

                int routeSize = markersList.size();

                if (routeSize > 0) {
                    int lastMarkerIndex = routeSize - 1;
                    previousSelectedMarker = markersList.get(lastMarkerIndex);
                } else {
                    previousSelectedMarker = null;
                }

                view.removePolyLine();
                view.changeMarkerIcon(clickedMarker);
                deselectMarker();

            } else {

                if (markerInfo.isSelected()) {

                    view.showSnackBar(markersList.indexOf(clickedMarker));
                }else{

                    origin = previousSelectedMarker.getTitle();
                    destination = clickedMarker.getTitle();
                    start = previousSelectedMarker.getPosition();
                    end = clickedMarker.getPosition();

                    markerInfo.setSelected(true);
                    markerInfo.setIconType("selected");

                    markersList.add(clickedMarker);
                    previousSelectedMarker = clickedMarker;
                    view.changeMarkerIcon(clickedMarker);
                }
            }
        } else {
            //use phone location for origin.
            origin = "Vrij-Harnasch 21, Den Hoorn";
            destination = clickedMarker.getTitle();
            start = new LatLng(52.008234,4.312999);
            end = clickedMarker.getPosition();

            markerInfo.setSelected(true);
            markerInfo.setIconType("selected");

            markersList.add(clickedMarker);
            previousSelectedMarker = clickedMarker;
            view.changeMarkerIcon(clickedMarker);
        }

        if(origin != null && destination != null) {
            DriveRequest request = new DriveRequest();
            request.setOrigin(origin);
            request.setDestination(destination);
            view.getPolylineToMarker(start, end);
            markerSelected(request);
        }
    }

    private void markerSelected(DriveRequest request){
        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setEvent("getDrive");
        fragmentEvent.setDriveRequest(request);
        view.sendFragmentEvent(fragmentEvent);
    }

    private void deselectMarker(){
        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setEvent("removeDrive");
        view.sendFragmentEvent(fragmentEvent);
    }

    @Override
    public void infoWindowClick(Marker marker) {
        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setEvent("itemClick");
        fragmentEvent.setAddressString(marker.getTitle());
        view.sendFragmentEvent(fragmentEvent);
    }

    @Override
    public void multipleMarkersDeselected(int markerPosition) {

        String address = markersList.get(markerPosition).getTitle();

        for(int i = markerPosition; i< markersList.size(); i++){
            Marker marker = markersList.get(i);
            MarkerInfo markerInfo = (MarkerInfo) marker.getTag();

            markerInfo.setSelected(false);

            if(markerInfo.isBusiness()){
                markerInfo.setIconType("business");
            }else{
                markerInfo.setIconType("private");
            }

            view.changeMarkerIcon(marker);
        }

        markersList.subList(markerPosition, markersList.size()).clear();

        if (markersList.size() > 0) {
            previousSelectedMarker = markersList.get(markersList.size() -1);
        } else {
            previousSelectedMarker = null;
        }
        view.removePolyLine();

        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setEvent("removeMultipleDrive");
        fragmentEvent.setAddressString(address);
        view.sendFragmentEvent(fragmentEvent);
    }

    @Override
    public void activityEvent(ActivityEvent activityEvent) {

        String event = activityEvent.getEvent();
        Address address = activityEvent.getAddress();

        switch (event) {
            case "routeUpdated" : updateMarkers(activityEvent.getAddressList());
                break;
            case "addressAdded" : addMarkerToMap(address);
                break;
            case "addressRemoved" : removeMarkerFromMap(address);
                break;
        }
    }

    private void updateMarkers(List<Address> addressList) {
        this.addressList = addressList;
        view.removeAllMarkersFromMap();
        markersList.clear();
        setMarkers();
    }

    private void addMarkerToMap(Address address) {
        if(address.isValid()){
            view.markAddressOnMap(address);
            view.moveMapCamera(address.getLat(), address.getLng());
        }
    }

    private void removeMarkerFromMap(Address address) {
        for(Marker marker : markersList){
            if(marker.getTitle().equals(address.getAddress())){
                marker.remove();
                markersList.remove(marker);
                return;
            }
        }
    }
}