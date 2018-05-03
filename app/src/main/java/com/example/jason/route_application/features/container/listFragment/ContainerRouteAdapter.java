package com.example.jason.route_application.features.container.listFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.api.Drive;

import java.util.List;

/**
 * Created by Jason on 07-Feb-18.
 */

public class ContainerRouteAdapter extends RecyclerView.Adapter<ContainerRouteAdapter.CustomViewHolder>{

    private List<Drive> routeList;
    private RouteAdapterFunctions routeAdapterFunctions;

    ContainerRouteAdapter(RouteAdapterFunctions routeAdapterFunctions, List<Drive> routeList) {
        this.routeList = routeList;
        this.routeAdapterFunctions = routeAdapterFunctions;
    }

    public interface RouteAdapterFunctions {
        void routeAdapterItemClick(String address);
        void routeAdapterGoButtonClick(String address);
//        void onListItemSwipe(int position);
    }

    List<Drive> getList(){
        return this.routeList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_travel_information, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        Drive drive = routeList.get(position);

        String positionTracker = Integer.toString(position + 1);
        String city = drive.getDestinationAddress().getPostCode() + " " + drive.getDestinationAddress().getCity();
        String distance = "Distance: "+drive.getDriveDistanceHumanReadable();
        String duration = "Duration: "+drive.getDriveDurationHumanReadable();
        String arrivalTime = drive.getDeliveryTimeHumanReadable();

        holder.positionTextView.setText(positionTracker);
        holder.streetTextView.setText(drive.getDestinationAddress().getStreet());
        holder.cityTextView.setText(city);
        holder.distanceTextView.setText(distance);
        holder.durationTextView.setText(duration);
        holder.estimatedArrivalTime.setText(arrivalTime);

        if(drive.isDestinationIsABusiness()){
            holder.addressType.setImageResource(R.drawable.ic_marker_business);
        }else{
            holder.addressType.setImageResource(R.drawable.ic_marker_private);
        }

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView positionTextView;
        private TextView streetTextView;
        private TextView cityTextView;
        private TextView distanceTextView;
        private TextView durationTextView;
        private TextView estimatedArrivalTime;
        private ImageView addressType;
        private ImageView goButton;
        private ViewGroup container;

        CustomViewHolder(View itemView) {
            super(itemView);
            this.positionTextView = itemView.findViewById(R.id.positionTextView);
            this.streetTextView = itemView.findViewById(R.id.streetTextView);
            this.cityTextView = itemView.findViewById(R.id.cityTextView);
            this.distanceTextView = itemView.findViewById(R.id.distanceTextView);
            this.durationTextView = itemView.findViewById(R.id.durationTextView);
            this.estimatedArrivalTime = itemView.findViewById(R.id.estimatedArrivalTimeTextView);
            this.goButton = itemView.findViewById(R.id.goBtn);
            this.addressType = itemView.findViewById(R.id.addressTypeImageView);
            this.container = itemView.findViewById(R.id.root_layout);
            this.goButton.setOnClickListener(this);
            this.container.setOnClickListener(this);
        }

        public void onClick(View v) {

            if(v == this.container){
                routeAdapterFunctions.routeAdapterItemClick(routeList.get(this.getAdapterPosition()).getDestinationAddress().getAddress());
            }
            else if(v == this.goButton){
                routeAdapterFunctions.routeAdapterGoButtonClick(routeList.get(this.getAdapterPosition()).getDestinationAddress().getAddress());
            }
        }

    }
}
