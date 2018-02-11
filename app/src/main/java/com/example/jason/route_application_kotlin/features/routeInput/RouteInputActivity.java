package com.example.jason.route_application_kotlin.features.routeInput;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.models.AddressFormatter;
import com.example.jason.route_application_kotlin.data.pojos.AddressItem;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;
import com.example.jason.route_application_kotlin.features.shared.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteInputActivity extends BaseActivity implements MvpRouteInput.View{

    private final String Tag = "RouteActivity";

    @Inject RouteInputPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBasePresenterComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_input);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.addAddressToListBtn)
    @Override
    public void addAddressToList() {

    }

    @Override
    public void showAddressDetails(AddressItem addressItem) {
        Intent intent = new Intent(this, AddressDetailsActivity.class);
        intent.putExtra("address", (Parcelable) addressItem);
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.doSomething();
    }

    @OnClick(R.id.beginRouteBtn)
    @Override
    public void beginRoute() {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }
}
