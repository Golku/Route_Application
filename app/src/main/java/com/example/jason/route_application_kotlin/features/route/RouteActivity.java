package com.example.jason.route_application_kotlin.features.route;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.models.FragmentCommunication;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.TravelInformationRequest;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.route.listFragment.RouteListFragment;
import com.example.jason.route_application_kotlin.features.route.mapFragment.RouteMapFragment;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteActivity extends DaggerAppCompatActivity implements
        MvpRoute.View,
        RouteListFragment.RouteListListener,
        RouteMapFragment.RouteMapListener{

    private final String logTag = "logDebugTag";

    @Inject MvpRoute.Presenter presenter;

    @BindView(R.id.privateAddressesTextView)
    TextView privateAddressesTextView;
    @BindView(R.id.businessAddressesTextView)
    TextView businessAddressesTextView;
    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        String routeCode = getIntent().getStringExtra("routeCode");
        presenter.setRouteCode(routeCode);
        presenter.getRouteFromApi();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateRouteInformation(Map<String, Integer> counters) {
        String privateCurrentSize = String.valueOf(counters.get("privateCurrentSize"));
        String privateMaxSize = String.valueOf(counters.get("privateMaxSize"));
        String businessCurrentSize = String.valueOf(counters.get("businessCurrentSize"));
        String businessMaxSize = String.valueOf(counters.get("businessMaxSize"));
        privateAddressesTextView.setText(privateCurrentSize+"/"+privateMaxSize);
        businessAddressesTextView.setText(businessCurrentSize+"/"+businessMaxSize);
    }

    @Override
    public void setupFragments(UnOrganizedRoute unOrganizedRoute) {

//        privateAddressesTextView.setText(String.valueOf(organizedRoute.getPrivateAddressesCount()));
//        businessAddressesTextView.setText(String.valueOf(organizedRoute.getBusinessAddressesCount()));

        Bundle organizedRouteBundle = new Bundle();
        organizedRouteBundle.putParcelable("unOrganizedRoute", unOrganizedRoute);

        Fragment routeMapFragment = new RouteMapFragment();
        Fragment routeListFragment = new RouteListFragment();

        routeMapFragment.setArguments(organizedRouteBundle);

        RouteSectionPagerAdapter routeSectionPagerAdapter = new RouteSectionPagerAdapter(getSupportFragmentManager());
        routeSectionPagerAdapter.addFragment("Map", routeMapFragment);
        routeSectionPagerAdapter.addFragment("Route List", routeListFragment);

        viewPager.setAdapter(routeSectionPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onMarkerClick(TravelInformationRequest travelInformationRequest) {
        presenter.getTravelInformation(travelInformationRequest);
    }

    @Override
    public void passSingleDrive(SingleDrive singleDrive) {
//        Log.d(logTag, singleDrive.getOriginFormattedAddress().getFormattedAddress());
//        Log.d(logTag, singleDrive.getDestinationFormattedAddress().getFormattedAddress());
        EventBus.getDefault().post(new FragmentCommunication(singleDrive));
    }

    @Override
    public void onListItemClick(String address) {
        presenter.onListItemClick(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        presenter.onGoButtonClick(address);
    }

    @Override
    public void showAddressDetails(String address) {
        Intent i = new Intent (this, AddressDetailsActivity.class);
        i.putExtra("address", address);
        startActivity(i);
    }

    @Override
    public void navigateToDestination(String address) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+address));
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void closeActivity() {
        finish();

    }
}
