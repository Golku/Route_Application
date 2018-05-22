package com.example.jason.route_application.features.addressDetails;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.Session;
import com.example.jason.route_application.data.pojos.database.AddressInformation;
import com.example.jason.route_application.data.pojos.CommentInformation;
import com.example.jason.route_application.features.commentDisplay.CommentDisplayActivity;
import com.example.jason.route_application.features.commentInput.CommentInputActivity;

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

    private boolean returning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);
        ButterKnife.bind(this);
        returning = false;
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (returning) {
            presenter.getAddressInformation();
        }
    }

    private void init() {
        Address address = getIntent().getParcelableExtra("address");

        streetTextView.setText(address.getStreet());
        postcodeTextView.setText(address.getPostCode());
        cityTextView.setText(address.getCity());

        addressTypeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeChangeConfirmation();
            }
        });

        if (address.isBusiness()) {
            addressTypeImageView.setImageResource(R.drawable.ic_marker_business);
        }

        presenter.setInfo(new Session(this), address);
        presenter.getAddressInformation();
    }

    @Override
    public void setUpAdapter(AddressInformation addressInformation) {
        messageToUserTextView.setVisibility(View.GONE);
        AddressDetailsAdapter adapter = new AddressDetailsAdapter(addressInformation, this);
        recyclerView.setAdapter(adapter);
    }

    private void typeChangeConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setTitle("Change address type")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.changeAddressType();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.googleSearchBtn)
    public void onGoogleLinkClick() {
        presenter.googleLinkClick();
    }

    @Override
    public void onListItemClick(CommentInformation commentInformation) {
        showCommentDisplay(commentInformation);
    }

    @OnClick(R.id.addCommentBtn)
    public void onAddCommentButtonClick() {
        presenter.addCommentButtonClick();
    }

    @Override
    public void showAddressInGoogle(Address address) {
        String url = "http://www.google.com/search?q=" + address.getStreet() + " " + address.getCity();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void updateMessageToUserTextView(String message) {
        messageToUserTextView.setText(message);
    }

    @Override
    public void onStartNetworkOperation() {
        progressBar.setVisibility(View.VISIBLE);
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
    public void showCommentInput(Address address) {
        returning = true;
        Intent i = new Intent(this, CommentInputActivity.class);
        i.putExtra("address", address);
        startActivity(i);
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
