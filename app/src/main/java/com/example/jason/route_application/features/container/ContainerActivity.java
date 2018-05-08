package com.example.jason.route_application.features.container;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.FragmentDelegation;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.api.DriveRequest;
import com.example.jason.route_application.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application.features.container.addressListFragment.AddressListFragment;
import com.example.jason.route_application.features.container.routeListFragment.RouteListFragment;
import com.example.jason.route_application.features.container.mapFragment.MapFragment;
import org.greenrobot.eventbus.EventBus;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class ContainerActivity extends DaggerAppCompatActivity implements
        MvpContainer.View,
        RouteListFragment.RouteListListener,
        MapFragment.RouteMapListener {

    @Inject
    MvpContainer.Presenter presenter;

    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.private_completion)
    TextView privateCompletion;
    @BindView(R.id.business_completion)
    TextView businessCompletion;
    @BindView(R.id.route_end_time)
    TextView routeEndTime;

    private final String debugTag = "debugTag";

    private AlertDialog alertDialog;
    private EditText streetInput;
    private EditText postcodeLettersInput;
    private EditText postcodeNumbersInput;
    private EditText cityInput;
    private Button cancelDialogBtn;
    private Button addAddressBtn;

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
        Snackbar snackbar = Snackbar.make(viewPager, "Press again to exit", Snackbar.LENGTH_SHORT);
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
        setupAddressInputDialog();
        presenter.getContainer(new Session(this));
    }

    @Override
    public void setupFragments(RouteInfoHolder routeInfoHolder) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("routeInfoHolder", routeInfoHolder);

        Fragment addressListFragment = new AddressListFragment();
        Fragment MapFragment = new MapFragment();
        Fragment routeListFragment = new RouteListFragment();

        addressListFragment.setArguments(bundle);
        MapFragment.setArguments(bundle);
        routeListFragment.setArguments(bundle);

        ContainerSectionPagerAdapter containerSectionPagerAdapter = new ContainerSectionPagerAdapter(getSupportFragmentManager());
        containerSectionPagerAdapter.addFragment(addressListFragment);
        containerSectionPagerAdapter.addFragment(MapFragment);
        containerSectionPagerAdapter.addFragment(routeListFragment);

        viewPager.setAdapter(containerSectionPagerAdapter);
    }

    @OnClick(R.id.fa_btn)
    public void showAddressList(){
        viewPager.setCurrentItem(0);
    }

    @OnClick(R.id.fb_btn)
    public void showMap(){
        viewPager.setCurrentItem(1);
    }

    @OnClick(R.id.fa_btn)
    public void showRouteList(){
        viewPager.setCurrentItem(2);
    }

    private void setupAddressInputDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ContainerActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_address_input, null);

        streetInput = view.findViewById(R.id.street_input);
        postcodeNumbersInput = view.findViewById(R.id.postcode_numbers_input);
        postcodeLettersInput = view.findViewById(R.id.postcode_letters_input);
        cityInput = view.findViewById(R.id.city_input);
        addAddressBtn = view.findViewById(R.id.add_address_btn);
        cancelDialogBtn = view.findViewById(R.id.cancel_dialog_btn);

        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateDeliveryCompletion(int[] deliveryCompletion) {
        privateCompletion.setText(String.valueOf(deliveryCompletion[0])+"/"+String.valueOf(deliveryCompletion[1])+" delivered");
        businessCompletion.setText(String.valueOf(deliveryCompletion[2])+"/"+String.valueOf(deliveryCompletion[3])+" delivered");
    }

    @Override
    public void showAddressInputDialog() {
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!streetInput.getText().toString().isEmpty()) {
                    Address address = new Address();
                    address.setStreet(streetInput.getText().toString());
                    address.setPostCode(postcodeNumbersInput.getText().toString()+" "+postcodeNumbersInput.getText().toString());
                    address.setCity(cityInput.getText().toString());
                    address.setCountry("Netherlands");
                    alertDialog.dismiss();
                    presenter.addAddress(address);
                }else{
                    showToast("Fill in a address");
                }
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void updateRouteEndTime(String endTime) {
        routeEndTime.setText(endTime);
    }

    @Override
    public void delegateRouteChange(FragmentDelegation delegation) {
        EventBus.getDefault().post(delegation);
    }

    @Override
    public void onMarkerSelected(DriveRequest request) {
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
