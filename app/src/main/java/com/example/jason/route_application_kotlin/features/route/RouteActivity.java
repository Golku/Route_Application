package com.example.jason.route_application_kotlin.features.route;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import okhttp3.Route;

public class RouteActivity extends DaggerAppCompatActivity implements
        MvpRoute.View,
        RouteListFragment.RouteListListener,
        RouteMapFragment.RouteMapListener{

    private final String logTag = "logDebugTag";

    @Inject MvpRoute.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        init();
    }

    private void init(){
        String routeCode = getIntent().getStringExtra("routeCode");

        Fragment routeMapFragment = new RouteMapFragment();
        Fragment routeListFragment = new RouteListFragment();

//        RouteListFragment routeListFragmentCaller = getSupportFragmentManager().findFragmentById(R.id.list);

//        routeMapFragment.setArguments();

        RouteSectionPagerAdapter routeSectionPagerAdapter = new RouteSectionPagerAdapter(getSupportFragmentManager());
        routeSectionPagerAdapter.addFragment("Route List", routeListFragment);
        routeSectionPagerAdapter.addFragment("Map", routeMapFragment);

        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(routeSectionPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        presenter.setRouteCode(routeCode);
//        presenter.getRouteFromApi();
    }

    @Override
    public void setUpAdapter(OrganizedRoute organizedRoute) {

    }

    @Override
    public void onMarkerClick() {

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
