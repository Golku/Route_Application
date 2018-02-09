package com.example.jason.route_application_kotlin.features.routeInput;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.RouteApplication;
import com.example.jason.route_application_kotlin.data.models.AddressFormatter;
import com.example.jason.route_application_kotlin.data.pojos.AddressItem;
import com.example.jason.route_application_kotlin.di.presenters.BasePresenterComponent;
import com.example.jason.route_application_kotlin.di.presenters.BasePresenterModule;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;
import com.example.jason.route_application_kotlin.features.shared.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteInputActivity extends BaseActivity implements MvpRouteInput.View{

    @Inject AddressFormatter addressFormatter;
    @Inject AddressFormatter addressFormatter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getBasePresenterComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_input);
        ButterKnife.bind(this);

        addressFormatter.doSomething();
        addressFormatter.doSomething();

        Log.d("Dagger", "First" + addressFormatter);
        Log.d("Dagger", "Second" + addressFormatter2);

    }

    @OnClick(R.id.addAddressToListBtn)
    @Override
    public void addAddressToList() {

    }

    @Override
    public void showAddressDetails(AddressItem addressItem) {
        Intent intent = new Intent(this, AddressDetailsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.beginRouteBtn)
    @Override
    public void beginRoute() {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }
}
