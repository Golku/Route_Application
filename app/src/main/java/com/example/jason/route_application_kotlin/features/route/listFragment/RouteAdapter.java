package com.example.jason.route_application_kotlin.features.route.listFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;

import java.util.List;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.CustomViewHolder>{

    private List<SingleDrive> routeList;
    private RouteListFunctions routeListFunctions;

    RouteAdapter(List<SingleDrive> routeList, RouteListFunctions routeListFunctions) {
        this.routeList = routeList;
        this.routeListFunctions = routeListFunctions;
    }

    public interface RouteListFunctions{
        void onAdapterListItemClick(String address);
        void onAdapterGoButtonClick(String address);
//        void onListItemSwipe(int position);
    }

    void addToList(SingleDrive singleDrive){
        this.routeList.add(singleDrive);
    }

    List<SingleDrive> getList(){
        return this.routeList;
    }

    @Override
    public RouteAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_travel_information, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RouteAdapter.CustomViewHolder holder, int position) {

        SingleDrive currentItem = routeList.get(position);

        String positionTracker = Integer.toString(position + 1);
        String city = currentItem.getDestinationFormattedAddress().getPostCode() + " " + currentItem.getDestinationFormattedAddress().getCity();
        String distance = "Distance: "+currentItem.getDriveDistanceHumanReadable();
        String duration = "Duration: "+currentItem.getDriveDurationHumanReadable();
        String arrivalTime = currentItem.getDeliveryTimeHumanReadable();

        holder.positionTextView.setText(positionTracker);
        holder.streetTextView.setText(currentItem.getDestinationFormattedAddress().getStreet());
        holder.cityTextView.setText(city);
        holder.distanceTextView.setText(distance);
        holder.durationTextView.setText(duration);
        holder.estimatedArrivalTime.setText(arrivalTime);

        if(currentItem.getDestinationIsABusiness()){
            holder.addressType.setImageResource(R.drawable.ic_business_address);
        }else{
            holder.addressType.setImageResource(R.drawable.ic_private_address2);
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
                routeListFunctions.onAdapterListItemClick(routeList.get(this.getAdapterPosition()).getDestinationFormattedAddress().getFormattedAddress());
            }
            else if(v == this.goButton){
                routeListFunctions.onAdapterGoButtonClick(routeList.get(this.getAdapterPosition()).getDestinationFormattedAddress().getFormattedAddress());
            }
        }

    }
}
