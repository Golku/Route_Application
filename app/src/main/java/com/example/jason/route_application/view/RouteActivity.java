package com.example.jason.route_application.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.route_application.R;
import com.example.jason.route_application.controller.RouteActivityController;
import com.example.jason.route_application.model.InformationFetcher;
import com.example.jason.route_application.model.pojos.ApiResponse;
import com.example.jason.route_application.model.pojos.OutGoingRoute;
import com.example.jason.route_application.model.pojos.SingleDrive;
import com.example.jason.route_application.model.pojos.SingleOrganizedRoute;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {

    private final String log_tag = "RouteActivityLog";

    private RouteActivityController controller;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter addressesAdapter;

    private SingleOrganizedRoute singleOrganizedRoute;

    private TextView privateAddresses;
    private TextView businessAddresses;
    private TextView wrongAddresses;
    private TextView estimatedRouteCompletedTime;

    private OutGoingRoute outGoingRoute;

    private ProgressBar loadingBar;
    private boolean requestError;
    private String errorMessage;

    private InformationFetcher informationFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        recyclerView = findViewById(R.id.recView);
        layoutInflater = getLayoutInflater();

        controller = new RouteActivityController(this);

        privateAddresses = findViewById(R.id.privateAddressesTextView);
        businessAddresses = findViewById(R.id.businessAddressesTextView);
        wrongAddresses = findViewById(R.id.wrongAddressesTextView);
        estimatedRouteCompletedTime = findViewById(R.id.estimatedRouteCompletedTimeTextView);
        loadingBar = findViewById(R.id.progressBar);

        outGoingRoute = new OutGoingRoute();

        String routeCode = getIntent().getStringExtra("routeCode");
        String origin = getIntent().getStringExtra("origin");
        ArrayList inputtedAddressesList =  getIntent().getStringArrayListExtra("addressesList");

        outGoingRoute.setRouteCode(routeCode);
        outGoingRoute.setOrigin(origin);
        outGoingRoute.setAddressList(inputtedAddressesList);

//        List<String> addressesList = new ArrayList<>();

//        addressesList.add("Els Borst-Eilersplein 275, Den haag");
//        addressesList.add("Deimanstraat 96, Den haag");
//        addressesList.add("Van Musschenbroekstraat 45, Den haag");
//        addressesList.add("Allard Piersonlaan 294, Den haag");
//        addressesList.add("Koopmans van Boekerenstraat 65, Den haag");
//        addressesList.add("Goeverneurlaan 102, Den haag");
//        addressesList.add("Antheunisstraat 196, Den haag");
//        addressesList.add("Haverschmidtstraat 15, Den haag");
//        addressesList.add("Jan de Baenstraat 15, Den haag");
//        addressesList.add("Hobbemastraat 85, Den haag");
//        addressesList.add("Kempstraat 44, Den haag");
//        addressesList.add("Schaarsbergenstraat 180, Den haag");
//        addressesList.add("Goeverneurlaan 102, Den haag");
//        addressesList.add("Antheunisstraat 196, Den haag");
//        addressesList.add("Haverschmidtstraat 15, Den haag");
//        addressesList.add("Hoefkade 99, Den haag");
//        addressesList.add("Jan de Baenstraat 15, Den haag");
//        addressesList.add("Hobbemastraat 85, Den haag");
//        addressesList.add("Kempstraat 44, Den haag");
//        addressesList.add("Schaarsbergenstraat 180, Den haag");
//        addressesList.add("Zuiderparklaan 391, Den haag");
//        addressesList.add("Klimopstraat 109, Den haag");
//        addressesList.add("Toscaninistraat 92, Den haag");
//        addressesList.add("Denijsstraat 149, Den haag");
//        addressesList.add("Machiel Vrijenhoeklaan 450, Den haag");
//        addressesList.add("Loosduinse Hoofdstraat 602, Den haag");
//        addressesList.add("Nieuwendamlaan 184, Den haag");
//        addressesList.add("Medemblikstraat 240, Den haag");
//        addressesList.add("Reitzstraat 185, Den haag");
//        addressesList.add("Joubertplantsoen 147, Den haag");
//        addressesList.add("Van der Helststraat 48, Den haag");
//        addressesList.add("Verisstraat 36, Den haag");
//        addressesList.add("Beetsstraat 94, Den haag");
//        addressesList.add("Hasebroekstraat 84, Den haag");
//        addressesList.add("Hasebroekstraat 112, Den haag");
//        addressesList.add("Van Meursstraat 4, Den haag");

        //Log.d(log_tag, outGoingRoute.getRouteCode());

        requestError = false;
        errorMessage = "";
        controller.displayMessage("loadingBar", true, "");

        controller.getRoute();

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message message) {

            controller.displayMessage("loadingBar", false, "");

            if(requestError){

                controller.displayMessage("toast", true, errorMessage);

            }else{

                setUpAdapter();
            }
        }

    };


    public void beginGetRouteJob(){

        informationFetcher = new InformationFetcher(){

            @Override
            protected void onPostExecute(ApiResponse apiResponse) {
                controller.processApiResponse(apiResponse);
            }

        };

        informationFetcher.execute();
    }

    public void setUpAdapter() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressesAdapter = new CustomAdapter();

        privateAddresses.setText(String.valueOf(singleOrganizedRoute.getPrivateAddressesCount()));
        businessAddresses.setText(String.valueOf(singleOrganizedRoute.getBusinessAddressesCount()));
        wrongAddresses.setText(String.valueOf(singleOrganizedRoute.getWrongAddressesCount()));

        recyclerView.setAdapter(addressesAdapter);

        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        //itemTouchHelper.attachToRecyclerView(recyclerView);
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

    public void startAddressDetailsActivity(int adapterPosition) {
        Intent i = new Intent (this, AddressDetailsActivity.class);
        i.putExtra("formattedAddress", singleOrganizedRoute.getRouteList().get(adapterPosition).getDestinationFormattedAddress());
        startActivity(i);
    }

    public void startNavigationOnGoogleMaps(int adapterPosition){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+singleOrganizedRoute.getRouteList().get(adapterPosition).getDestinationFormattedAddress().getFormattedAddress()));
        startActivity(intent);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.single_travel_information, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            SingleDrive currentItem = singleOrganizedRoute.getRouteList().get(position);
            holder.position.setText(Integer.toString(position + 1));
            //holder.origin.setText(currentItem.getOriginFormattedAddress().getCompletedAddress());
            holder.destination.setText(currentItem.getDestinationFormattedAddress().getFormattedAddress());
            holder.distance.setText("Distance: "+currentItem.getDriveDistanceHumanReadable());
            holder.travelTime.setText("Duration: "+currentItem.getDriveDurationHumanReadable());
            holder.deliveryTimeTextView.setText("Estimated delivery time: " + currentItem.getDeliveryTimeHumanReadable());
        }


        @Override
        public int getItemCount() {
            return singleOrganizedRoute.getRouteList().size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private TextView position;
            //private TextView origin;
            private TextView destination;
            private TextView distance;
            private TextView travelTime;
            private TextView deliveryTimeTextView;
            private Button goButton;
            private ViewGroup container;

            public CustomViewHolder(View itemView) {
                super(itemView);
                this.position = itemView.findViewById(R.id.position);
                //this.origin = itemView.findViewById(R.id.origin);
                this.destination = itemView.findViewById(R.id.destination);
                this.distance = itemView.findViewById(R.id.distance);
                this.travelTime = itemView.findViewById(R.id.travelTime);
                this.deliveryTimeTextView = itemView.findViewById(R.id.deliveryTime);
                this.goButton = itemView.findViewById(R.id.openMapButton);
                this.container = itemView.findViewById(R.id.root_layout);
                this.goButton.setOnClickListener(this);
                this.container.setOnClickListener(this);
            }

            public void onClick(View v) {

                if(v == this.container){
                    controller.onListItemClick(this.getAdapterPosition());
                }else if(v == this.goButton){
                    controller.onListItemGoButtonClick(this.getAdapterPosition());
                }
            }
        }

    }

}
