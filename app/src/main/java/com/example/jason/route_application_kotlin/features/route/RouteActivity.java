package com.example.jason.route_application_kotlin.features.route;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jason.route_application_kotlin.R;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class RouteActivity extends DaggerAppCompatActivity implements MvpRoute.View{

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);
        init();
    }

    public void init(){

        ArrayList<String> inputtedAddressesList =  getIntent().getStringArrayListExtra("addressesList");

        presenter.getData();

    }

    @Override
    public void setUpAdapter() {

    }
}
