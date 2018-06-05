package com.example.jason.route_application.data.models;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.example.jason.route_application.data.pojos.Address;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class CustomClusterRenderer extends DefaultClusterRenderer<Address> {

    private final String debugTag = "debugTag";
    private final Context context;
    private List<Address> routeOrder;
    private Calendar calendar;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<Address> clusterManager, List<Address> routeOrder) {
        super(context, map, clusterManager);
        this.context = context;
        this.routeOrder = routeOrder;
        calendar = Calendar.getInstance();
    }

    @Override
    protected void onClusterItemRendered(Address address, Marker marker) {
        changeMarkerIcon(address);
    }

    public void changeMarkerIcon(Address address){

        String iconName;
        Marker marker = getMarker(address);

        int currentTimeInMinutes = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);

//        Log.d(debugTag, "Hour of day: " + calendar.get(Calendar.HOUR_OF_DAY));
//        Log.d(debugTag, "current time: " + currentTimeInMinutes);
//        Log.d(debugTag, "address closing time: " + address.getClosingTime());

        if(address.isFetchingDriveInfo()){
            iconName = "ic_hourglass";
        }else{
            if(address.isSelected()){
                iconName = "ic_marker_"+String.valueOf(routeOrder.indexOf(address)+1);
            }else{
                if(address.isBusiness()){
                    iconName = "ic_marker_business";
                }else{
                    iconName = "ic_marker_private";
                }
            }
        }

        if(address.isUserLocation()){
            iconName = "ic_marker_origin";
        }

        Resources res = context.getResources();
        int resID = res.getIdentifier(iconName, "drawable", context.getPackageName());
        marker.setIcon(BitmapDescriptorFactory.fromResource(resID));
    }
}