package com.example.jason.route_application.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application.R;
import com.example.jason.route_application.controller.MainActivityController;
import com.example.jason.route_application.model.PlaceArrayAdapter;
import com.example.jason.route_application.model.pojos.FormattedAddress;
import com.example.jason.route_application.model.pojos.SingleAddress;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private static final String log_tag = "MainActivity";

    private MainActivityController controller;

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private EditText routeCodeInput;
    private TextView mAddressTextView;
    private TextView mPhoneTextView;
    private TextView mAttTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private AutocompleteFilter filter;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, 4.477733), new LatLng(52.070498, 4.300700));
    private List<SingleAddress> listOfAddresses;
    private SingleAddress newAddressItem;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter addressesAdapter;

    private ArrayList addressList;

    private TextView listSizeTextView;

    private String currentLocation;

    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        filter = new AutocompleteFilter.Builder()
                .setCountry("NL")
                .build();

        routeCodeInput = findViewById(R.id.routeCodeInput);
        mAutocompleteTextView = findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mAddressTextView = findViewById(R.id.address);
        mPhoneTextView = findViewById(R.id.phone);
        mAttTextView = findViewById(R.id.att);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, filter);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        recyclerView = findViewById(R.id.recView);
        layoutInflater = getLayoutInflater();
        controller = new MainActivityController(this);
        newAddressItem = new SingleAddress();

        currentLocation = "";

        addressList = new ArrayList();

        listSizeTextView = findViewById(R.id.listSizeTextView);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);

            mAutocompleteTextView.setText(item.primaryText.toString());

            final String placeId = String.valueOf(item.placeId);

//            Log.i(log_tag, "Selected: " + item.primaryText);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
//            Log.i(log_tag, "Fetching details for ID: " + item.placeId);
        }

    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {

        @Override
        public void onResult(PlaceBuffer places) {

            if (!places.getStatus().isSuccess()) {

                Log.e(log_tag, "Place query did not complete. Error: " + places.getStatus().toString());

                return;
            }

            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            newAddressItem.setAddress(place.getAddress().toString());
            mAddressTextView.setText(place.getAddress().toString());
            mPhoneTextView.setText(place.getPhoneNumber().toString());

            if (attributions != null) {
                mAttTextView.setText(attributions.toString());
            }else{
                mAttTextView.setText("No attr");
            }

            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(log_tag, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(log_tag, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(log_tag, "Google Places API connection suspended.");
    }

    public void startAddressDetailsActivity(String address) {
        Intent i = new Intent (this, AddressDetailsActivity.class);
        FormattedAddress formattedAddress = controller.formatAddress(address);
        i.putExtra("formattedAddress", formattedAddress);
        startActivity(i);
    }

    public void startRouteActivity(View view){

        Intent i = new Intent (this, RouteActivity.class);

        i.putExtra("routeCode", routeCodeInput.getText().toString());


        if(currentLocation == ""){
            i.putExtra("origin", "Vrij-Harnasch 21, Den Hoorn");
        }else{
            i.putExtra("origin", currentLocation);
        }

        i.putStringArrayListExtra("addressesList", addressList);
        startActivity(i);

    }

    public void changeStartAddress(View view){
        currentLocation = newAddressItem.getAddress();
    }

    public void addNewAddressItem(View view){

        SingleAddress newListAddressItem = new SingleAddress();
        newListAddressItem.setAddress(newAddressItem.getAddress());

        addressList.add(newAddressItem.getAddress());
        listOfAddresses.add(newListAddressItem);
        int endOfList = listOfAddresses.size() - 1;
        addressesAdapter.notifyItemInserted(endOfList);
        recyclerView.smoothScrollToPosition(endOfList);
        resetTextViews();
        listSizeTextView.setText(String.valueOf(listOfAddresses.size()));

        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void deleteAddressListItemAt(int position) {
        listOfAddresses.remove(position);
        addressList.remove(position);
        addressesAdapter.notifyItemRemoved(position);
        listSizeTextView.setText(String.valueOf(listOfAddresses.size()));
    }

    public void setUpAdapterAndView(List<SingleAddress> listOfAddresses) {
        this.listOfAddresses = listOfAddresses;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressesAdapter = new CustomAdapter();
        recyclerView.setAdapter(addressesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void resetTextViews(){
        mAutocompleteTextView.setText("");
        mAddressTextView.setText("");
        mPhoneTextView.setText("");
        mAttTextView.setText("");
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.single_address, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {

            SingleAddress currentAddress = listOfAddresses.get(position);
            holder.address.setText(currentAddress.getAddress());
        }

        @Override
        public int getItemCount() {
            return listOfAddresses.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView address;
            private ViewGroup container;

            public CustomViewHolder(View itemView) {
                super(itemView);
                this.address = itemView.findViewById(R.id.address);
                this.container = itemView.findViewById(R.id.root_layout);
                this.container.setOnClickListener(this);
            }

            public void onClick(View v) {
                SingleAddress addressItem = listOfAddresses.get(this.getAdapterPosition());
                controller.onListAddressClick(addressItem);
            }

        }

    }

    private ItemTouchHelper.Callback createHelperCallback(){

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                controller.onListItemSwiped(
                        position,
                        listOfAddresses.get(position)
                );
            }
        };

        return simpleItemTouchCallback;
    }
}