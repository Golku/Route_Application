package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.data.pojos.api.UnOrganizedRoute;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Jason on 3/22/2018.
 */

public class RouteMapFragment extends Fragment implements MvpRouteMap.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;

    private MvpRouteMap.Presenter presenter;

    private RouteActivity routeActivityCallback;

    public interface RouteMapListener {
        void mapReady();
        void getDriveInformation(SingleDriveRequest request);
        void removeAddressFromRouteList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.routeActivityCallback = (RouteActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " routeActivityCallback error");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new RouteMapPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mapView = view.findViewById(R.id.mapView);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UnOrganizedRoute unOrganizedRoute){
        presenter.setMarkers(unOrganizedRoute);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setOnMarkerClickListener(this);

        routeActivityCallback.mapReady();
    }

    @Override
    public void addMarkersToMap(List<FormattedAddress> addresses) {
        Resources res = this.getResources();
        String iconName;

        //get phone location
        Marker originMarker = googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(52.008234, 4.312999))
                        .title("My Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));

        originMarker.setTag("origin");

        if (addresses != null) {

            for (FormattedAddress address : addresses) {

                String formattedAddress = address.getFormattedAddress();
                double lat = address.getLat();
                double lng = address.getLng();

                if (address.getIsBusiness()) {
                    iconName = "ic_business_address";
                } else {
                    iconName = "ic_private_address2";
                }

//                iconName = "ic_"+String.valueOf(i+1);

                int resID = res.getIdentifier(iconName, "drawable", getContext().getPackageName());

                Marker marker = googleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .title(formattedAddress)
                                .icon(BitmapDescriptorFactory.fromResource(resID)));
                marker.setTag(true);
            }
        }

        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(52.008234, 4.312999)).zoom(9f).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        presenter.processMarker(marker);
        return false;
    }

    @Override
    public void getDriveInformation(SingleDriveRequest request) {
        routeActivityCallback.getDriveInformation(request);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
