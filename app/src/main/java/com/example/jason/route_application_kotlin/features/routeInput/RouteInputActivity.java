package com.example.jason.route_application_kotlin.features.routeInput;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class RouteInputActivity extends DaggerAppCompatActivity implements
        MvpRouteInput.View,
        RouteInputAdapter.AddressListFunctions,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    @Inject
    MvpRouteInput.Presenter presenter;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.addressTextView)
    TextView addressTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.listSizeTextView)
    TextView listSizeTextView;
    @BindView(R.id.recView)
    RecyclerView recyclerView;

    private final String debugTag = "debugTag";
    private RouteInputAdapter adapter;

    private final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, 4.477733), new LatLng(52.070498, 4.300700));
    private GoogleApiClient mGoogleApiClient;
    private RouteInputPlaceArrayAdapter mPlaceArrayAdapter;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_input);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        presenter.setUpView();

        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        int GOOGLE_API_CLIENT_ID = 0;

        mGoogleApiClient = new GoogleApiClient.Builder(RouteInputActivity.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setCountry("NL")
                .build();

        mPlaceArrayAdapter = new RouteInputPlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, filter);
        autoCompleteTextView.setAdapter(mPlaceArrayAdapter);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final RouteInputPlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);

            if (item != null) {
                autoCompleteTextView.setText(item.primaryText.toString());
            }else{
                showToast("place array adapter null");
            }

            final String placeId = String.valueOf(item.placeId);

//            Log.i(log_tag, "Selected: " + item.primaryText);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
//            Log.i(log_tag, "Fetching details for ID: " + item.placeId);
        }

    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {

        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if (!places.getStatus().isSuccess()) {

                Log.e(debugTag, "Place query did not complete. Error: " + places.getStatus().toString());

                return;
            }

            // Selecting the first object buffer.
            final Place place = places.get(0);

            addressTextView.setText(place.getAddress().toString());
            phoneTextView.setText(place.getPhoneNumber().toString());

            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(debugTag, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(debugTag, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(debugTag, "Google Places API connection suspended.");
    }

    @Override
    public void setUpAdapter(ArrayList<String> listOfAddresses) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteInputAdapter(listOfAddresses, this);
        adapter.addTouchHelper(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateListSizeTextView(int listSize) {
        listSizeTextView.setText(String.valueOf(listSize));
    }

    @OnClick(R.id.addAddressToListBtn)
    @Override
    public void onAddAddressButtonClick() {
        //blablabla niet gebruiken als invalid address (blablabla, Au Coinat 27, 2915 Bure, Zwitserland)
        presenter.addAddressToList(addressTextView.getText().toString());
        autoCompleteTextView.setText("");
    }

    @Override
    public void addAddressToList(int listSize) {
        int endOfList = listSize - 1;
        adapter.notifyItemInserted(endOfList);
        recyclerView.smoothScrollToPosition(endOfList);
    }

    @Override
    public void onListItemClick(String address) {
        presenter.onListItemClick(address);
    }

    @Override
    public void onListItemSwipe(int position) {
        presenter.onListItemSwiped(position);
    }

    @Override
    public void removeAddressFromList(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showAddressDetails(String address) {
        Intent intent = new Intent(this, AddressDetailsActivity.class);
        intent.putExtra("address", address);
        startActivity(intent);
    }

    @OnClick(R.id.submitRouteBtn)
    @Override
    public void onSubmitRouteButtonClick() {
        presenter.submitRoute();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivity(){
        finish();
    }
}