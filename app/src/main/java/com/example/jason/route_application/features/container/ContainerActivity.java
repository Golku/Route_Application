package com.example.jason.route_application.features.container;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.FragmentEvent;
import com.example.jason.route_application.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application.features.container.addressListFragment.AddressListFragment;
import com.example.jason.route_application.features.container.routeListFragment.DriveListFragment;
import com.example.jason.route_application.features.container.mapFragment.MapFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class ContainerActivity extends DaggerAppCompatActivity implements MvpContainer.View{

    @Inject
    MvpContainer.Presenter presenter;

    @BindView(R.id.loading_screen)
    ConstraintLayout loadingScreen;

    @BindView(R.id.fragment_container)
    ViewPager fragmentContainer;
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
        Snackbar snackbar = Snackbar.make(fragmentContainer, "Press again to exit", Snackbar.LENGTH_SHORT);
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
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        loadingScreen.bringToFront();
        presenter.setSession(new Session(this));
        presenter.getContainer();
    }

    @OnClick(R.id.get_container_btn)
    public void getContainerBtnClick(){
        presenter.getContainer();
    }

    @Override
    public void setupFragments(RouteInfoHolder routeInfoHolder) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("routeInfoHolder", routeInfoHolder);

        Fragment addressListFragment = new AddressListFragment();
        Fragment MapFragment = new MapFragment();
        Fragment routeListFragment = new DriveListFragment();

        addressListFragment.setArguments(bundle);
        MapFragment.setArguments(bundle);
        routeListFragment.setArguments(bundle);

        ContainerSectionPagerAdapter containerSectionPagerAdapter = new ContainerSectionPagerAdapter(getSupportFragmentManager());
        containerSectionPagerAdapter.addFragment(addressListFragment);
        containerSectionPagerAdapter.addFragment(MapFragment);
        containerSectionPagerAdapter.addFragment(routeListFragment);

        fragmentContainer.setAdapter(containerSectionPagerAdapter);
        loadingScreen.setVisibility(View.GONE);
    }

    @OnClick(R.id.address_list_iv)
    public void showAddressList(){
        fragmentContainer.setCurrentItem(0);
    }

    @OnClick(R.id.map_iv)
    public void showMap(){
        fragmentContainer.setCurrentItem(1);
    }

    @OnClick(R.id.route_list_iv)
    public void showRouteList(){
        fragmentContainer.setCurrentItem(2);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateDeliveryCompletion(int[] deliveryCompletion) {
        privateCompletion.setText(String.valueOf(deliveryCompletion[0])+"/"+String.valueOf(deliveryCompletion[1])+" delivered");
        businessCompletion.setText(String.valueOf(deliveryCompletion[2])+"/"+String.valueOf(deliveryCompletion[3])+" delivered");
    }

    @Override
    public void updateRouteEndTimeTv(String endTime) {
        routeEndTime.setText(endTime);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentEvent(FragmentEvent fragmentEvent){
        presenter.fragmentEvent(fragmentEvent);
    }

    @Override
    public void sendActivityEvent(ActivityEvent activityEvent) {
        EventBus.getDefault().post(activityEvent);
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
        EventBus.getDefault().unregister(this);
        finish();
    }
}