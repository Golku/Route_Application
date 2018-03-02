package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.OutGoingRoute;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class CorrectInvalidAddressesActivity extends DaggerAppCompatActivity implements MvpCorrectInvalidAddresses.View, CorrectInvalidAddressesAdapter.AddressListFunctions{

    private final String log_tag = "correctAddressesLogTag";

    @Inject MvpCorrectInvalidAddresses.Presenter presenter;

    @BindView(R.id.recView)
    RecyclerView recyclerView;
    @BindView(R.id.instructionTextView)
    TextView instructionTextView;
    @BindView(R.id.messageToUserTextView)
    TextView messageToUserTextView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.submitAddressesBtn)
    Button submitAddressesBtn;

    private CorrectInvalidAddressesAdapter adapter;

    private AlertDialog.Builder alertDialogBuilder;
    private View view;
    private AlertDialog alertDialog;

    private EditText correctedAddressEditText;
    private Button cancelDialogBtn;
    private Button changeAddressBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_invalid_addresses);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        String routeCode = getIntent().getStringExtra("routeCode");
        String origin = getIntent().getStringExtra("origin");
        ArrayList<String> inputtedAddressesList =  getIntent().getStringArrayListExtra("addressesList");

        OutGoingRoute outGoingRoute = new OutGoingRoute(
                routeCode,
                origin,
                inputtedAddressesList
        );

        presenter.submitRoute(outGoingRoute);
    }

    @Override
    public void setUpView() {
        instructionTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        submitAddressesBtn.setVisibility(View.VISIBLE);

        alertDialogBuilder = new AlertDialog.Builder(CorrectInvalidAddressesActivity.this);
        view = getLayoutInflater().inflate(R.layout.reform_address_dialog, null);

        correctedAddressEditText = view.findViewById(R.id.correctedAddressEditText);
        cancelDialogBtn = view.findViewById(R.id.cancelDialogBtn);
        changeAddressBtn = view.findViewById(R.id.changeAddressBtn);

        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
    }

    @Override
    public void setUpAdapter(ArrayList<String> invalidAddressesList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CorrectInvalidAddressesAdapter(invalidAddressesList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateList(int position) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void removeAddressFromList(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showReformAddressDialog(final int position, String address) {

        correctedAddressEditText.setText(address);

        changeAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!correctedAddressEditText.getText().toString().isEmpty()) {
                    String correctedAddress = correctedAddressEditText.getText().toString();
                    alertDialog.dismiss();
                    onDialogChangeAddressBtnClick(position, correctedAddress);
                }else{
                    showToast("Fill in a address");
                }
            }
        });

        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    @OnClick(R.id.submitAddressesBtn)
    public void onSubmitAddressesBtnClick(){
        presenter.submitCorrectedAddresses();
    }

    @Override
    public void onDialogChangeAddressBtnClick(int position, String correctedAddress){
        presenter.correctAddress(position, correctedAddress);
    }

    @Override
    public void onRemoveAddressButtonClick(int position) {
        presenter.onRemoveAddressButtonClick(position);
    }

    @Override
    public void onListItemClick(int position) {
        presenter.onItemClick(position);
    }

    @Override
    public void onStartNetworkOperation() {
        messageToUserTextView.setText("Validating the addresses");
    }

    @Override
    public void onFinishNetworkOperation() {
        messageToUserTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void startRouteActivity(String routeCode) {
        Intent intent = new Intent(this, RouteActivity.class);
        intent.putExtra("routeCode", routeCode);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
