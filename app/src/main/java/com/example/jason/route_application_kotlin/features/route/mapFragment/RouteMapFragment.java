package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

/**
 * Created by Jason on 3/22/2018.
 */

public class RouteMapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mapView;
    View view;

    OrganizedRoute organizedRoute;

    public RouteMapFragment() {
        //Required empty constructor
    }

    public interface RouteMapListener{
        void onMarkerClick();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_route_map, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.organizedRoute = bundle.getParcelable("organizedRoute");
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

        this.mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //get phone location
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(52.008234, 4.312999))
                        .title("My Location").icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker2)));

        Resources res = this.getResources();
        String iconName = "";

        if(organizedRoute != null) {
            for (int i=0; i<organizedRoute.getRouteList().size(); i++) {
                String address = organizedRoute.getRouteList().get(i).getDestinationFormattedAddress().getFormattedAddress();
                double lat = organizedRoute.getRouteList().get(i).getDestinationFormattedAddress().getLat();
                double lng = organizedRoute.getRouteList().get(i).getDestinationFormattedAddress().getLng();

//                if(organizedRoute.getRouteList().get(i).getDestinationIsABusiness()){
//                    iconName = "box";
//                }else{
//                    iconName = "box";
//                }

                iconName = "ic_"+String.valueOf(i+1);

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
    }
}
