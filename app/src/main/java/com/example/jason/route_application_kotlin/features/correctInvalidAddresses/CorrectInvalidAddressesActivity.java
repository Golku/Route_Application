package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class CorrectInvalidAddressesActivity extends DaggerAppCompatActivity implements MvpCorrectInvalidAddresses.View, CorrectInvalidAddressesAdapter.AddressListFunctions{

    private final String log_tag = "correctAddressesLogTag";

    @Inject MvpCorrectInvalidAddresses.Presenter presenter;

    @BindView(R.id.recView)
    RecyclerView recyclerView;
    @BindView(R.id.messageToUserTextView)
    TextView messageToUserTextView;

    private CorrectInvalidAddressesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_invalid_addresses);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        messageToUserTextView.setText("This addresses are invalid. Here you can remove then from the route or correct them.");
        ArrayList<String> invalidAddresses = getIntent().getStringArrayListExtra("invalidAddresses");
        presenter.setUpInvalidAddressesList(invalidAddresses);
        presenter.setUpRecyclerView();
    }

    @Override
    public void setUpAdapter(ArrayList<String> invalidAddressesList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CorrectInvalidAddressesAdapter(invalidAddressesList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRemoveAddressButtonClick() {
        Log.d(log_tag, "Remove address");
    }

    @Override
    public void onCorrectAddressButtonClick() {
        Log.d(log_tag, "Correct address");
    }
}
