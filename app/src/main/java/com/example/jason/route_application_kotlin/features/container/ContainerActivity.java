package com.example.jason.route_application_kotlin.features.container;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.RouteInfoHolder;
import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.Route;
import com.example.jason.route_application_kotlin.data.pojos.Session;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDriveRequest;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.container.listFragment.ContainerListFragment;
import com.example.jason.route_application_kotlin.features.container.mapFragment.ContainerMapFragment;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class ContainerActivity extends DaggerAppCompatActivity implements
        MvpContainer.View,
        ContainerListFragment.RouteListListener,
        ContainerMapFragment.RouteMapListener {

    @Inject
    MvpContainer.Presenter presenter;

    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.private_completion)
    TextView privateCompletion;
    @BindView(R.id.business_completion)
    TextView businessCompletion;
    @BindView(R.id.route_end_time)
    TextView routeEndTime;

    private final String debugTag = "debugTag";

    private boolean backPress = false;

    @Override
    public void onBackPressed() {
        if(backPress){
            closeActivity();
        }else{
            backPress = true;
            onBackPressSnackbar();
        }
    }

    private void onBackPressSnackbar(){
        Snackbar snackbar = Snackbar.make(viewPager, "press again to exit", Snackbar.LENGTH_SHORT);
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                backPress = false;
            }
        });
        snackbar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        presenter.getContainer(new Session(this));
    }

    @Override
    public void setupFragments(RouteInfoHolder routeInfoHolder) {
        Bundle bundle = new Bundle();
        Fragment routeMapFragment = new ContainerMapFragment();
        Fragment routeListFragment = new ContainerListFragment();

        bundle.putParcelable("routeInfoHolder", routeInfoHolder);

        routeMapFragment.setArguments(bundle);
        routeListFragment.setArguments(bundle);

        ContainerSectionPagerAdapter containerSectionPagerAdapter = new ContainerSectionPagerAdapter(getSupportFragmentManager());
        containerSectionPagerAdapter.addFragment("Map", routeMapFragment);
        containerSectionPagerAdapter.addFragment("Route", routeListFragment);

        viewPager.setAdapter(containerSectionPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateDeliveryCompletion(int[] deliveryCompletion) {
        privateCompletion.setText(String.valueOf(deliveryCompletion[0])+"/"+String.valueOf(deliveryCompletion[1])+" delivered");
        businessCompletion.setText(String.valueOf(deliveryCompletion[2])+"/"+String.valueOf(deliveryCompletion[3])+" delivered");
    }

    @Override
    public void updateRouteEndTime(String endTime) {
        routeEndTime.setText(endTime);
    }

    @Override
    public void delegateRouteChange(RouteListFragmentDelegation delegation) {
        EventBus.getDefault().post(delegation);
    }

    @Override
    public void getDriveInformation(SingleDriveRequest request) {
        presenter.getDriveInformation(request);
    }

    @Override
    public void onDeselectMarker() {
        presenter.markerDeselected();
    }

    @Override
    public void onDeselectMultipleMarkers(String destination) {
        presenter.multipleMarkersDeselected(destination);
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
