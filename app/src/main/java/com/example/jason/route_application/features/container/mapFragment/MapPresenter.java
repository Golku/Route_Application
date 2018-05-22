package com.example.jason.route_application.features.container.mapFragment;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.Event;
import com.example.jason.route_application.data.pojos.MarkerInfo;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.features.shared.BasePresenter;
import com.example.jason.route_application.features.shared.MvpBasePresenter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public class MapPresenter extends BasePresenter implements
        MvpBasePresenter,
        MvpMap.Presenter,
        GoogleMap.OnMarkerClickListener{

    private final String debugTag = "debugTag";

    private MvpMap.View view;

    private GoogleMap googleMap;

    private LatLng userLocation;

    private List<Address> addressList;
    private List<Marker> markers;
    private List<Marker> selectedMarkers;

    private Marker previousSelectedMarker;

    MapPresenter(MvpMap.View view, List<Address> addressList, List<String> routeOrder) {
        this.view = view;
        this.addressList = addressList;
        this.markers = new ArrayList<>();
        this.selectedMarkers = new ArrayList<>();
        this.previousSelectedMarker = null;
    }

    @Override
    public void setMapData(GoogleMap googleMap) {
        this.googleMap = googleMap;

        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setOnMarkerClickListener(this);

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                infoWindowClick(marker);
            }
        });

        setAddressListMarkers();

        moveMapCamera(52.008234, 4.312999);
    }

    private void setAddressListMarkers(){
        //get phone location
        Address origin = new Address();

        origin.setAddress("My location");
        origin.setUserLocation(true);
        origin.setLat(52.008234);
        origin.setLng(4.312999);

        Marker originMarker = googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(origin.getLat(), origin.getLng()))
                        .title(origin.getAddress()));

        view.changeMarkerIcon(originMarker, "ic_marker_origin");

        for(Address address : addressList){
            if(address.isValid()){
                markAddress(address);
            }
        }
    }

    private void markAddress(Address address) {
        MarkerInfo markerInfo = new MarkerInfo();
        markerInfo.setAddress(address);
        Marker marker = googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(address.getLat(), address.getLng()))
                        .title(address.getAddress()));
        marker.setTag(markerInfo);

        String iconName;

        if(address.isBusiness()){
            iconName = "ic_marker_business";
        }else{
            iconName = "ic_marker_private";
        }

        markers.add(marker);
        view.changeMarkerIcon(marker, iconName);
    }

    private void moveMapCamera(double lat, double lng) {
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(10f).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void infoWindowClick(Marker marker) {
        for(Address address: addressList){
            if(marker.getTitle().equals(address.getAddress())){
                createEvent("container", "itemClick", address, this);
                break;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.getTitle().equals("My location")) {
            view.showToast("origin");
            return false;
        }

        MarkerInfo markerInfo = (MarkerInfo) marker.getTag();

        String origin = null;
        String destination = null;

        LatLng start = null;
        LatLng end = null;

        if (previousSelectedMarker != null) {

            if (marker.equals(previousSelectedMarker)) {

                selectedMarkers.remove(marker);

                markerInfo.setSelected(false);

                String iconName;

                if(markerInfo.getAddress().isBusiness()){
                    iconName = "ic_marker_business";
                }else{
                    iconName = "ic_marker_private";
                }

                int routeSize = selectedMarkers.size();

                if (routeSize > 0) {
                    int lastMarkerIndex = routeSize - 1;
                    previousSelectedMarker = selectedMarkers.get(lastMarkerIndex);
                } else {
                    previousSelectedMarker = null;
                }

                view.removePolyLine();
                view.changeMarkerIcon(marker, iconName);
                deselectMarker();

            } else {

                if (markerInfo.isSelected()) {

                    view.showSnackBar(selectedMarkers.indexOf(marker));
                }else{

                    origin = previousSelectedMarker.getTitle();
                    destination = marker.getTitle();
                    start = previousSelectedMarker.getPosition();
                    end = marker.getPosition();

                    markerInfo.setSelected(true);

                    selectedMarkers.add(marker);
                    previousSelectedMarker = marker;
                    view.changeMarkerIcon(marker, "ic_marker_selected");
                }
            }
        } else {
            //use phone location for origin.
            origin = "Vrij-Harnasch 21, Den Hoorn";
            destination = marker.getTitle();
            start = new LatLng(52.008234,4.312999);
            end = marker.getPosition();

            markerInfo.setSelected(true);

            selectedMarkers.add(marker);
            previousSelectedMarker = marker;
            view.changeMarkerIcon(marker, "ic_marker_selected");
        }

        if(origin != null && destination != null) {
            DriveRequest request = new DriveRequest();
            request.setOrigin(origin);
            request.setDestination(destination);
            view.getPolylineToMarker(start, end);
            markerSelected(request);
        }

        marker.showInfoWindow();
        return true;
    }

    private void markerSelected(DriveRequest request){
        createEvent("container", "getDrive", request, this);
    }

    private void deselectMarker(){
        createEvent("driveFragment","removeDrive",this);
    }

    @Override
    public void multipleMarkersDeselected(int markerPosition) {

        String address = selectedMarkers.get(markerPosition).getTitle();

        for(int i = markerPosition; i< selectedMarkers.size(); i++){
            Marker marker = selectedMarkers.get(i);
            MarkerInfo markerInfo = (MarkerInfo) marker.getTag();
            String iconName;

            markerInfo.setSelected(false);

            if(markerInfo.getAddress().isBusiness()){
                iconName = "ic_marker_business";
            }else{
                iconName = "ic_marker_private";
            }

            view.changeMarkerIcon(marker, iconName);
        }

        selectedMarkers.subList(markerPosition, selectedMarkers.size()).clear();

        if (selectedMarkers.size() > 0) {
            previousSelectedMarker = selectedMarkers.get(selectedMarkers.size() -1);
        } else {
            previousSelectedMarker = null;
        }
        view.removePolyLine();

        createEvent("driveFragment", "RemoveMultipleDrive", address, this);
    }

    private void removeAllMarkersFromMap() {
        googleMap.clear();
    }

    @Override
    public void eventReceived(Event event) {

        if(!(event.getReceiver().equals("mapFragment") || event.getReceiver().equals("all"))){
            return;
        }

        Log.d(debugTag, "Event received on mapFragment: "+ event.getEventName());

        switch (event.getEventName()) {
            case "updateList" : updateMarkers(event.getAddressList());
                break;
            case "showMarker" : showMarker(event.getAddress());
                break;
            case "markAddress" : addMarkerToMap(event.getAddress());
                break;
            case "removeMarker" : removeMarkerFromMap(event.getAddress());
                break;
        }
    }

    private void showMarker(Address address){
        for(Marker marker: markers){
            if(marker.getTitle().equals(address.getAddress())){
                moveMapCamera(address.getLat(), address.getLng());
                marker.showInfoWindow();
                break;
            }
        }
    }

    private void updateMarkers(List<Address> addressList) {
        this.addressList = addressList;
        removeAllMarkersFromMap();
        markers.clear();
        selectedMarkers.clear();
        previousSelectedMarker = null;
        setAddressListMarkers();
    }

    private void addMarkerToMap(Address address) {
        if(address.isValid()){
            markAddress(address);
            moveMapCamera(address.getLat(), address.getLng());
        }
    }

    private void removeMarkerFromMap(Address address) {

        for(Marker marker : markers){

            if(marker.getTitle().equals(address.getAddress())){

                MarkerInfo markerInfo = (MarkerInfo) marker.getTag();

                if(markerInfo.isSelected()){
                    multipleMarkersDeselected(selectedMarkers.indexOf(marker));
                }

                marker.remove();
                markers.remove(marker);

                break;
            }
        }
    }

    @Override
    public void publishEvent(Event event) {
        view.postEvent(event);
    }
}