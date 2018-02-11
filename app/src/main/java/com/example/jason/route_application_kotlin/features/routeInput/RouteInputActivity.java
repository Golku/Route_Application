package com.example.jason.route_application_kotlin.features.routeInput;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.SingleAddress;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;
import com.example.jason.route_application_kotlin.features.shared.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteInputActivity extends BaseActivity implements MvpRouteInput.View{

    private final String Tag = "RouteActivity";

    private RouteInputAdapter adapter;

    private List<SingleAddress> listOfAddresses;

    @BindView(R.id.recView)
    private RecyclerView recyclerView;

    @Inject RouteInputPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBasePresenterComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_input);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        setUpAdapter();
    }

    public void setUpAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteInputAdapter();
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.addAddressToListBtn)
    @Override
    public void addAddressToList() {
//        int endOfList = listOfAddresses.size() - 1;
//        adapter.notifyItemInserted(endOfList);
//        recyclerView.smoothScrollToPosition(endOfList);
    }

    @Override
    public void showAddressDetails(SingleAddress singleAddress) {
        Intent intent = new Intent(this, AddressDetailsActivity.class);
        intent.putExtra("address", (Parcelable) singleAddress);
        startActivity(intent);
    }

    @OnClick(R.id.beginRouteBtn)
    @Override
    public void beginRoute() {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }
}
