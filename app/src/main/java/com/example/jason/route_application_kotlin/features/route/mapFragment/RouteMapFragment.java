package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.TravelInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jason on 3/22/2018.
 */

public class RouteMapFragment extends Fragment implements MvpRouteMap.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap googleMap;
    MapView mapView;
    View view;

    private MvpRouteMap.Presenter presenter;

    private RouteActivity routeActivityCallback;

    private int tracker = 0;
    private String destination = "";

    public RouteMapFragment() {
        //Required empty constructor
    }

    public interface RouteMapListener{
        void onMarkerClick(TravelInformationRequest travelInformationRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            routeActivityCallback = (RouteActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " routeActivityCallback error");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.presenter = new RouteMapPresenter(this);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            UnOrganizedRoute unOrganizedRoute = bundle.getParcelable("unOrganizedRoute");
            presenter.setRoute(unOrganizedRoute);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_route_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapView);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        List<FormattedAddress> addressesList = presenter.getAddressesList();

        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Resources res = this.getResources();
        String iconName;

        //get phone location
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(52.008234, 4.312999))
                        .title("My Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));

        if(addressesList != null) {

            for (FormattedAddress formattedAddress : addressesList) {

                String address = formattedAddress.getFormattedAddress();
                double lat = formattedAddress.getLat();
                double lng = formattedAddress.getLng();

                if(formattedAddress.getIsBusiness()){
                    iconName = "ic_business_address";
                }else{
                    iconName = "ic_private_address2";
                }

//                iconName = "ic_"+String.valueOf(i+1);

                int resID = res.getIdentifier(iconName, "drawable", getContext().getPackageName());

                googleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .title(address)
                                .icon(BitmapDescriptorFactory.fromResource(resID)));
            }
        }

        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(52.008234, 4.312999)).zoom(9f).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        this.googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        TravelInformationRequest travelInformationRequest = new TravelInformationRequest();

        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

        if(tracker == 0) {
            travelInformationRequest.setOrigin("vrij harnasch 21 den hoorn");
            travelInformationRequest.setDestination(marker.getTitle());
            destination = marker.getTitle();
            tracker++;
        }else{
            travelInformationRequest.setOrigin(destination);
            travelInformationRequest.setDestination(marker.getTitle());
            destination = marker.getTitle();
        }

        routeActivityCallback.onMarkerClick(travelInformationRequest);

        return false;
    }
}
