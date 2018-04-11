package com.example.jason.route_application_kotlin.features.routeState;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.api.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.api.OutGoingRoute;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;
import com.example.jason.route_application_kotlin.features.correctInvalidAddresses.CorrectInvalidAddressesActivity;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import java.util.List;

public class RouteStateActivity extends DaggerAppCompatActivity implements MvpRouteState.View {

    private final String log_tag = "logTagDebug";

    @Inject
    MvpRouteState.Presenter presenter;

    @BindView(R.id.routeStatusTextView)
    TextView routeStateTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.retryBtn)
    Button retryBtn;

    private boolean returning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_state);
        ButterKnife.bind(this);
        returning = false;
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        returning = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(returning){
            presenter.getRouteState();
        }
    }

    private void init(){

        String action = getIntent().getStringExtra("action");
        String routeCode = getIntent().getStringExtra("routeCode");

        if(routeCode != null && !routeCode.isEmpty()){
            presenter.setRouteCode(routeCode);
        }else{
            showToast("No route code provided");
            closeActivity();
            return;
        }

        if(action.equals("submitRoute")){

            String origin = getIntent().getStringExtra("origin");
            ArrayList<String> inputtedAddressesList =  getIntent().getStringArrayListExtra("addressesList");

            OutGoingRoute outGoingRoute = new OutGoingRoute(
                    routeCode,
                    origin,
                    inputtedAddressesList
            );

            presenter.submitRoute(outGoingRoute);

        }else if(action.equals("getRoute")){
            presenter.getRouteState();
        }

    }

    @Override
    public void updateRouteStateTextView(String routeState) {
        routeStateTextView.setText(routeState);
    }

    @Override
    public void startCorrectInvalidAddressesActivity(String routeCode) {
        Intent intent = new Intent(this, CorrectInvalidAddressesActivity.class);
        intent.putExtra("routeCode", routeCode);
        startActivity(intent);
    }

    @Override
    public void startRouteUnorganized(String routeCode, ArrayList<FormattedAddress> addressList) {
        Intent intent = new Intent(this, RouteActivity.class);
        intent.putExtra("routeCode", routeCode);
        intent.putExtra("organized", false);
        intent.putParcelableArrayListExtra("addressList", addressList);
        startActivity(intent);
        closeActivity();
    }

    @Override
    public void startRouteOrganized(String routeCode, ArrayList<SingleDrive> routeList) {
        Intent intent = new Intent(this, RouteActivity.class);
        intent.putExtra("routeCode", routeCode);
        intent.putExtra("organized", true);
        intent.putParcelableArrayListExtra("routeList", routeList);
        startActivity(intent);
        closeActivity();
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void closeActivity() {
        finish();
    }


}
