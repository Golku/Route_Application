package com.example.jason.route_application_kotlin.features.addressDetails;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.database.AddressInformation;
import com.example.jason.route_application_kotlin.data.pojos.database.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.features.commentDisplay.CommentDisplayActivity;
import com.example.jason.route_application_kotlin.features.commentInput.CommentInputActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class AddressDetailsActivity extends DaggerAppCompatActivity implements MvpAddressDetails.View, AddressDetailsAdapter.CommentListFunctions {

    @Inject
    MvpAddressDetails.Presenter presenter;

    @BindView(R.id.addressCommentsList)
    RecyclerView recyclerView;
    @BindView(R.id.streetTextView)
    TextView streetTextView;
    @BindView(R.id.postcodeTextView)
    TextView postcodeTextView;
    @BindView(R.id.cityTextView)
    TextView cityTextView;
    @BindView(R.id.addressTypeImageView)
    ImageView addressTypeImageView;
    @BindView(R.id.messageToUserTextView)
    TextView messageToUserTextView;
    @BindView(R.id.addCommentBtn)
    FloatingActionButton addCommentBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private final String log_tag = "AddressDetails_logTag";
    private boolean returning;
    private boolean active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);
        ButterKnife.bind(this);
        active = true;
        returning = false;
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(returning){
            presenter.getAddressInformation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    private void init() {
        String address = getIntent().getStringExtra("address");

        //If this fails everything else fails in this view! FIX THIS!!
        //This fails when the inputted address does not have the right format : street, postcode city, country

        try {
            presenter.formatAddress(address);
            presenter.updateTextViews();
            presenter.getAddressInformation();
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            onFinishNetworkOperation();
            showToast("Invalid Address");
            closeActivity();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Ask Wesley why this doesn't work
        //after back button is press you still get the toast "connection fail" if connection fails
        finish();
    }

    @Override
    public void setUpAddressInformation(FormattedAddress formattedAddress) {
        streetTextView.setText(formattedAddress.getStreet());
        postcodeTextView.setText(formattedAddress.getPostCode());
        cityTextView.setText(formattedAddress.getCity());
    }

    @Override
    public void setUpAdapter(AddressInformation addressInformation) {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AddressDetailsAdapter adapter = new AddressDetailsAdapter(addressInformation, this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.googleSearchBtn)
    @Override
    public void onGoogleLinkClick() {
        presenter.onGoogleLinkClick();
    }

    @Override
    public void onListItemClick(CommentInformation commentInformation) {
        presenter.onListItemClick(commentInformation);
    }

    @OnClick(R.id.addCommentBtn)
    @Override
    public void onAddCommentButtonClick() {
        presenter.onAddCommentButtonClick();
    }

    @Override
    public void showAddressInGoogle(FormattedAddress formattedAddress) {
        String url = "http://www.google.com/search?q=" + formattedAddress.getStreet() + " " + formattedAddress.getCity();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void updateMessageToUserTextView(boolean visible, String message) {
        if (visible) {
            messageToUserTextView.setVisibility(View.VISIBLE);
        } else {
            messageToUserTextView.setVisibility(View.GONE);
        }
        messageToUserTextView.setText(message);
    }

    @Override
    public void updateBusinessImageView(String business) {
        addressTypeImageView.setVisibility(View.VISIBLE);
        if (business.equals("yes")) {
            addressTypeImageView.setImageResource(R.drawable.ic_business_address);
        }
    }

    @Override
    public void onStartNetworkOperation() {
        messageToUserTextView.setText(R.string.networkOperation_addressDetailsActivity);
    }

    @Override
    public void onFinishNetworkOperation() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showCommentDisplay(CommentInformation commentInformation) {
        returning = false;
        Intent i = new Intent(this, CommentDisplayActivity.class);
        i.putExtra("commentInformation", commentInformation);
        startActivity(i);
    }

    @Override
    public void showCommentInput(FormattedAddress formattedAddress) {
        returning = true;
        Intent i = new Intent(this, CommentInputActivity.class);
        i.putExtra("formattedAddress", formattedAddress);
        startActivity(i);
    }

    @Override
    public boolean isActive() {
        return active;
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
