package com.example.jason.route_application_kotlin.features.addressDetails;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.AddressInformation;
import com.example.jason.route_application_kotlin.data.pojos.CommentInformation;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.features.CommentDisplay.CommentDisplayActivity;
import com.example.jason.route_application_kotlin.features.CommentInput.CommentInputActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class AddressDetailsActivity extends DaggerAppCompatActivity implements MvpAddressDetails.View, AddressDetailsAdapter.CommentListFunctions{

    @Inject MvpAddressDetails.Presenter presenter;

    @BindView(R.id.addressCommentsList)
    RecyclerView recyclerView;
    @BindView(R.id.streetTextView)
    TextView streetTextView;
    @BindView(R.id.postcodeTextView)
    TextView postcodeTextView;
    @BindView(R.id.cityTextView)
    TextView cityTextView;
    @BindView(R.id.businessTextView)
    TextView businessTextView;
    @BindView(R.id.messageToUserTextView)
    TextView messageToUserTextView;
    @BindView(R.id.googleAddressLinkTextView)
    TextView googleAddressLinkTextView;
    @BindView(R.id.addCommentBtn)
    FloatingActionButton addCommentBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private AddressDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        String address = getIntent().getStringExtra("address");
        presenter.formatAddress(address);
        presenter.updateTextViews();

        messageToUserTextView.setText("Fetching address information...");
        presenter.getAddressInformation();
    }

    @Override
    public void updateTextViews(FormattedAddress formattedAddress) {
        streetTextView.setText(formattedAddress.getStreet());
        postcodeTextView.setText(formattedAddress.getPostCode());
        cityTextView.setText(formattedAddress.getCity());
    }

    @Override
    public void setUpAdapter(AddressInformation addressInformation) {
        messageToUserTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressDetailsAdapter(addressInformation,this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.googleAddressLinkTextView)
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
    public void showCommentDisplay(CommentInformation commentInformation) {
        Intent i = new Intent(this, CommentDisplayActivity.class);
        i.putExtra("commentInformation", commentInformation);
        startActivity(i);
    }

    @Override
    public void showCommentInput(FormattedAddress formattedAddress) {
        Intent i = new Intent(this, CommentInputActivity.class);
        i.putExtra("formattedAddress", formattedAddress);
        startActivity(i);
    }

}
