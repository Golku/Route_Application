package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.TravelInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/22/2018.
 */

public class RouteMapFragment extends Fragment implements MvpRouteMap.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mGoogleMap;
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

        List<FormattedAddress> addressesLIst = presenter.getAddressesList();

        this.mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //get phone location
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(52.008234, 4.312999))
                        .title("My Location").icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker2)));

        Resources res = this.getResources();
        String iconName;

        if(addressesLIst != null) {

            for (int i=0; i<addressesLIst.size(); i++) {

                String address = addressesLIst.get(i).getFormattedAddress();
                double lat = addressesLIst.get(i).getLat();
                double lng = addressesLIst.get(i).getLng();

                if(addressesLIst.get(i).getIsBusiness()){
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

        this.mGoogleMap.setOnMarkerClickListener(this);
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
