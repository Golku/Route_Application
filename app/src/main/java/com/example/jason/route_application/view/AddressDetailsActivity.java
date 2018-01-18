package com.example.jason.route_application.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application.R;
import com.example.jason.route_application.controller.AddressDetailsActivityController;
import com.example.jason.route_application.model.pojos.DatabaseResponse;
import com.example.jason.route_application.model.pojos.FormattedAddress;
import com.example.jason.route_application.model.pojos.SingleAddressDbInformation;

import java.util.ArrayList;

public class AddressDetailsActivity extends AppCompatActivity {

    public boolean active;

    private final String log_tag = "AddressDetailsLog";

    private AddressDetailsActivityController controller;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter commentsListAdapter;

    private FormattedAddress formattedAddress;
    private SingleAddressDbInformation singleAddressDbInformation;

    private TextView streetTextView;
    private TextView postcodeTextView;
    private TextView cityTextView;
    private TextView businessTextView;
    private TextView commentsListTextView;
    private TextView googleAddressLink;
    private FloatingActionButton addCommentButton;
    private ProgressBar loadingBar;

    private ArrayList<String> commentInformation;

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        active = true;

        controller = new AddressDetailsActivityController(this);

        recyclerView = findViewById(R.id.addressCommentsList);
        layoutInflater = getLayoutInflater();

        streetTextView = findViewById(R.id.streetTextView);
        postcodeTextView = findViewById(R.id.postcodeTextView);
        cityTextView = findViewById(R.id.cityTextView);
        businessTextView = findViewById(R.id.businessTextView);
        commentsListTextView = findViewById(R.id.commentsListTextView);
        googleAddressLink = findViewById(R.id.googleAddressLink);
        addCommentButton = findViewById(R.id.addCommentBtn);
        loadingBar = findViewById(R.id.progressBar);

        commentInformation = new ArrayList<>();

        formattedAddress = (FormattedAddress) getIntent().getSerializableExtra("formattedAddress");

        streetTextView.setText(formattedAddress.getStreet());
        postcodeTextView.setText(formattedAddress.getPostCode());
        cityTextView.setText(formattedAddress.getCity());

        googleAddressLink.setOnClickListener(onClickListener);
        addCommentButton.setOnClickListener(onClickListener);

        controller.displayMessage("loadingBar", true, "");
        controller.getAddressInfo(formattedAddress);
    }

    public void updateView(DatabaseResponse databaseResponse) {

        controller.displayMessage("loadingBar", false, "");

        if (databaseResponse.getError()) {
            commentsListTextView.setText("There are no comments");
            if(!databaseResponse.getErrorMessage().isEmpty()){
                controller.displayMessage("toast", true, databaseResponse.getErrorMessage());
            }
        } else {

            singleAddressDbInformation = databaseResponse.getSingleAddressDbInformation();

            if(singleAddressDbInformation.getBusiness() == 1) {
                businessTextView.setText("Yes");
            }

            if (singleAddressDbInformation.getCommentsCount() == 0) {
                commentsListTextView.setText("There are no comments");
            } else {
                commentsListTextView.setVisibility(View.GONE);
                setUpAdapter();
            }

        }

    }

    public void setUpAdapter() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsListAdapter = new CustomAdapter();
        recyclerView.setAdapter(commentsListAdapter);

        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        //itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void startCommentDisplayActivity(ArrayList<String> commentInformation) {
        Intent i = new Intent(this, CommentDisplayActivity.class);
        i.putStringArrayListExtra("commentInformation", commentInformation);
        startActivity(i);
    }

    public void startCommentInputActivity() {

        ArrayList<String> addressInformation = new ArrayList<>();

        addressInformation.add(0, formattedAddress.getStreet());
        addressInformation.add(1, formattedAddress.getPostCode());
        addressInformation.add(2, formattedAddress.getCity());

        Intent i = new Intent(this, CommentInputActivity.class);
        i.putStringArrayListExtra("addressInformation", addressInformation);
        startActivity(i);
    }

    public void showLoadingBar(){
        loadingBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingBar(){
        loadingBar.setVisibility(View.GONE);
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        public void onClick(View v) {

            if (v == googleAddressLink) {

                String url = "http://www.google.com/search?q=" + formattedAddress.getStreet() + " " + formattedAddress.getCity();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }else if (v == addCommentButton) {
                controller.onAddCommentButtonClick();
            }
        }
    };

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.single_comment, parent, false);
            return new CustomAdapter.CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {

            holder.employedName.setText(singleAddressDbInformation.getEmployeeId().get(position));
            holder.date.setText(singleAddressDbInformation.getDates().get(position));
            holder.comment.setText(singleAddressDbInformation.getComments().get(position));

        }

        @Override
        public int getItemCount() {
            return singleAddressDbInformation.getCommentsCount();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView employedName;
            private TextView date;
            private TextView comment;
            private ViewGroup container;

            public CustomViewHolder(View itemView) {
                super(itemView);
                this.employedName = itemView.findViewById(R.id.employeedNameTextView);
                this.date = itemView.findViewById(R.id.dateTextView);
                this.comment = itemView.findViewById(R.id.commentTextView);
                this.container = itemView.findViewById(R.id.root_layout);
                this.container.setOnClickListener(this);
            }

            public void onClick(View v) {

//                Log.d(log_tag, "Adapter position: " + this.getAdapterPosition());
//                Log.d(log_tag, "List info at index 0: " + singelAddressDbInformation.getEmployeeId().get(0));
//                Log.d(log_tag, "List info: " + singelAddressDbInformation.getEmployeeId().get(this.getAdapterPosition()));

                commentInformation.add(0, singleAddressDbInformation.getEmployeeId().get(this.getAdapterPosition()));
                commentInformation.add(1, singleAddressDbInformation.getDates().get(this.getAdapterPosition()));
                commentInformation.add(2, singleAddressDbInformation.getComments().get(this.getAdapterPosition()));

                controller.onListItemClick(commentInformation);
            }
        }

    }

}
