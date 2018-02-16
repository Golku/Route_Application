package com.example.jason.route_application_kotlin.features.route;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.CustomViewHolder>{

    private OrganizedRoute organizedRoute;
    private RouteListFunctions routeListFunctions;

    public RouteAdapter(OrganizedRoute organizedRoute, RouteListFunctions routeListFunctions) {
        this.organizedRoute = organizedRoute;
        this.routeListFunctions = routeListFunctions;
    }

    public interface RouteListFunctions{
        void onListItemClick(String address);
        void onGoButtonClick(String address);
//        void onListItemSwipe(int position);
    }

    @Override
    public RouteAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_information, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RouteAdapter.CustomViewHolder holder, int position) {
        SingleDrive currentItem = organizedRoute.getRouteList().get(position);
        holder.positionTextView.setText(Integer.toString(position + 1));
        //holder.origin.setText(currentItem.getOriginFormattedAddress().getCompletedAddress());
        holder.destinationTextView.setText(currentItem.getDestinationFormattedAddress().getFormattedAddress());
        holder.distanceTextView.setText("Distance: "+currentItem.getDriveDistanceHumanReadable());
        holder.travelTimeTextView.setText("Duration: "+currentItem.getDriveDurationHumanReadable());
        holder.deliveryTimeTextView.setText("Estimated delivery time: " + currentItem.getDeliveryTimeHumanReadable());
    }

    @Override
    public int getItemCount() {
        return organizedRoute.getRouteList().size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView positionTextView;
        //private TextView originTextView;
        private TextView destinationTextView;
        private TextView distanceTextView;
        private TextView travelTimeTextView;
        private TextView deliveryTimeTextView;
        private Button goButton;
        private ViewGroup container;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.positionTextView = itemView.findViewById(R.id.positionTextView);
            //this.originTextView = itemView.findViewById(R.id.originTextView);
            this.destinationTextView = itemView.findViewById(R.id.destinationTextView);
            this.distanceTextView = itemView.findViewById(R.id.distanceTextView);
            this.travelTimeTextView = itemView.findViewById(R.id.travelTimeTextView);
            this.deliveryTimeTextView = itemView.findViewById(R.id.deliveryTimeTextView);
            this.goButton = itemView.findViewById(R.id.goButton);
            this.container = itemView.findViewById(R.id.root_layout);
            this.goButton.setOnClickListener(this);
            this.container.setOnClickListener(this);
        }

        public void onClick(View v) {

            if(v == this.container){
                routeListFunctions.onListItemClick(organizedRoute.getRouteList().get(this.getAdapterPosition()).getDestinationFormattedAddress().getFormattedAddress());
            }else if(v == this.goButton){
                routeListFunctions.onGoButtonClick(organizedRoute.getRouteList().get(this.getAdapterPosition()).getDestinationFormattedAddress().getFormattedAddress());
            }
        }

    }
}
