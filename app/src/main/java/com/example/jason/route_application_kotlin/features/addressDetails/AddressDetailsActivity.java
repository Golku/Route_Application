package com.example.jason.route_application_kotlin.features.addressDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.AddressInformation;
import com.example.jason.route_application_kotlin.features.route.RouteAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class AddressDetailsActivity extends DaggerAppCompatActivity implements MvpAddressDetails.View, AddressDetailsAdapter.CommentListFunctions{

    @Inject MvpAddressDetails.Presenter presenter;

    @BindView(R.id.addressCommentsList)
    RecyclerView recyclerView;

    private AddressDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);
        ButterKnife.bind(this);
        init();
    }

    private void init(){

    }

    @Override
    public void setUpAdapter(AddressInformation addressInformation) {


        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressDetailsAdapter(addressInformation,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(ArrayList<String> commentInformation) {

    }
}
