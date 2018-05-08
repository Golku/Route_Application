package com.example.jason.route_application.features.container.mapFragment;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.MarkerInfo;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.features.container.ContainerActivity;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/22/2018.
 */

public class MapFragment extends Fragment implements
        MvpMap.View,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        RoutingListener {

    private final String debugTag = "debugTag";

    private MvpMap.Presenter presenter;

    private GoogleMap googleMap;

    private ContainerActivity containerActivityCallback;

    private List<Polyline> polylines;

    private FrameLayout rootLayout;

    private static final int[] COLORS = new int[]{R.color.colorAccent};

    public interface RouteMapListener {

        void onMarkerSelected(DriveRequest request);

        void onDeselectMarker();

        void onDeselectMultipleMarkers(String destination);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.containerActivityCallback = (ContainerActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " containerActivityCallback error");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteInfoHolder routeInfoHolder = getArguments().getParcelable("routeInfoHolder");

        presenter = new MapPresenter(this,
                routeInfoHolder.getAddressList(),
                routeInfoHolder.getRouteOrder());

        polylines = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        this.rootLayout = view.findViewById(R.id.root_layout);
        return view;
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
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setOnMarkerClickListener(this);

        presenter.setMarkers();
    }

    @Override
    public void addMarkersToMap(List<Address> addresses) {
        //get phone location
        Marker originMarker = googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(52.008234, 4.312999))
                        .title("My Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_origin)));
        originMarker.setTag("origin");

        if (addresses != null) {
            for (Address address : addresses) {

                if(!address.isValid()){
                    return;
                }

                MarkerInfo markerInfo = new MarkerInfo();

                markerInfo.setSelected(false);

                if (address.isBusiness()) {
                    markerInfo.setBusiness(true);
                    markerInfo.setIconType("business");
                } else {
                    markerInfo.setBusiness(false);
                    markerInfo.setIconType("private");
                }

                Marker marker = googleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(address.getLat(), address.getLng()))
                                .title(address.getAddress()));
                marker.setTag(markerInfo);

                changeMarkerIcon(marker);
            }
        }

        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(52.008234, 4.312999)).zoom(9f).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        presenter.processMarker(marker);
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void changeMarkerIcon(Marker marker) {
        MarkerInfo markerInfo = (MarkerInfo) marker.getTag();

        Resources res = this.getResources();
        String iconName = null;

        switch (markerInfo.getIconType()) {
            case "private":
                iconName = "ic_marker_private";
                break;
            case "business":
                iconName = "ic_marker_business";
                break;
            case "selected":
                iconName = "ic_marker_selected";
                break;
        }

//        iconName = "ic_" + String.valueOf(i + 1);

        int resID = res.getIdentifier(iconName, "drawable", getContext().getPackageName());

        marker.setIcon(BitmapDescriptorFactory.fromResource(resID));
    }

    @Override
    public void getDriveInformation(DriveRequest request) {
        containerActivityCallback.onMarkerSelected(request);
    }

    @Override
    public void deselectMarker() {
        containerActivityCallback.onDeselectMarker();
    }

    @Override
    public void deselectMultipleMarker(String destination) {
        containerActivityCallback.onDeselectMultipleMarkers(destination);
    }

    @Override
    public void showSnackBar(final int markerPosition) {
        Snackbar snackbar = Snackbar.make(rootLayout, "Deselect until here?", Snackbar.LENGTH_LONG);
        snackbar.setAction("yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.multipleMarkersDeselected(markerPosition);
            }
        });
        snackbar.show();
    }

    @Override
    public void getPolylineToMarker(LatLng start, LatLng end) {
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(start, end)
                .build();
        routing.execute();
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
//            showToast(e.getMessage());
            showToast("polyline error");
        } else {
            showToast("Routing failed");
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = googleMap.addPolyline(polyOptions);
            polylines.add(polyline);

//            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void removePolyLine() {
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
        polylines.clear();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
