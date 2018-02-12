package com.example.jason.route_application_kotlin.features.routeInput;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class RouteInputActivity extends DaggerAppCompatActivity implements MvpRouteInput.View, RouteInputAdapter.AddressListFunctions{

    private final String Tag = "RouteActivity";

    @Inject
    MvpRouteInput.Presenter presenter;

    @BindView(R.id.routeCodeInputEditText)
    EditText routeCodeInputEditText;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.addressTextView)
    TextView addressTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.attTextView)
    TextView attTextView;
    @BindView(R.id.recView)
    RecyclerView recyclerView;

    private RouteInputAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_input);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        presenter.setUpView();
    }

    @Override
    public void setUpAdapter(ArrayList<String> listOfAddresses) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteInputAdapter(listOfAddresses, this);
        adapter.addTouchHelper(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.addAddressToListBtn)
    @Override
    public void onAddAddressButtonClick() {
        presenter.addAddressToList(String.valueOf(autoCompleteTextView.getText()));
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

    @OnClick(R.id.startRouteBtn)
    @Override
    public void onStartRouteButtonClick() {
        presenter.startRoute();
    }

    @Override
    public void beginRoute(ArrayList<String> listOfAddresses) {
        Intent intent = new Intent(this, RouteActivity.class);
        intent.putStringArrayListExtra("addressesList", listOfAddresses);
        startActivity(intent);
    }
}
