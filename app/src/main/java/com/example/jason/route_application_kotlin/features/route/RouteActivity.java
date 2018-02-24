package com.example.jason.route_application_kotlin.features.route;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.correctInvalidAddresses.CorrectInvalidAddressesActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class RouteActivity extends DaggerAppCompatActivity implements MvpRoute.View, RouteAdapter.RouteListFunctions{

    @Inject MvpRoute.Presenter presenter;

    @BindView(R.id.routeRecView)
    RecyclerView recyclerView;
    @BindView(R.id.privateAddressesTextView)
    TextView privateAddressesTextView;
    @BindView(R.id.businessAddressesTextView)
    TextView businessAddressesTextView;
    @BindView(R.id.wrongAddressesTextView)
    TextView wrongAddressesTextView;
    @BindView(R.id.messageToUserTextView)
    TextView messageToUserTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private RouteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);
        init();
    }

    private void init(){

        String action = getIntent().getStringExtra("action");
        String routeCode = getIntent().getStringExtra("routeCode");

        presenter.setRouteCode(routeCode);

        if(action.equals("get")){

            presenter.getRouteFromApi();

        }else if(action.equals("send")){

            String origin = getIntent().getStringExtra("origin");
            ArrayList<String> inputtedAddressesList =  getIntent().getStringArrayListExtra("addressesList");

            OutGoingRoute outGoingRoute = new OutGoingRoute(
                    routeCode,
                    origin,
                    inputtedAddressesList
            );

            presenter.sendRouteToApi(outGoingRoute);
        }

    }

    @Override
    public void setUpAdapter(OrganizedRoute organizedRoute) {
        privateAddressesTextView.setText(String.valueOf(organizedRoute.getPrivateAddressesCount()));
        businessAddressesTextView.setText(String.valueOf(organizedRoute.getBusinessAddressesCount()));
        wrongAddressesTextView.setText(String.valueOf(organizedRoute.getWrongAddressesCount()));

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteAdapter(organizedRoute, this);
        recyclerView.setAdapter(adapter);
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
    public void showInvalidAddresses(ArrayList<String> invalidAddresses) {
        Intent i = new Intent (this, CorrectInvalidAddressesActivity.class);
        i.putStringArrayListExtra("invalidAddresses", invalidAddresses);
        startActivity(i);
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
    public void onStartNetworkOperation() {
        messageToUserTextView.setText("Calculating Route...");
    }

    @Override
    public void onFinishNetworkOperation() {
        messageToUserTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
