package com.example.jason.route_application_kotlin.features.routeInput;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.jason.route_application_kotlin.R;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteInputAdapter extends RecyclerView.Adapter<RouteInputAdapter.CustomViewHolder>{

    @Override
    public RouteInputAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RouteInputAdapter.CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

//        private TextView position;
//        //private TextView origin;
//        private TextView destination;
//        private TextView distance;
//        private TextView travelTime;
//        private TextView deliveryTimeTextView;
        private Button goButton;
        private ViewGroup container;

        public CustomViewHolder(View itemView) {
            super(itemView);
//            this.position = itemView.findViewById(R.id.position);
//            //this.origin = itemView.findViewById(R.id.origin);
//            this.destination = itemView.findViewById(R.id.destination);
//            this.distance = itemView.findViewById(R.id.distance);
//            this.travelTime = itemView.findViewById(R.id.travelTime);
//            this.deliveryTimeTextView = itemView.findViewById(R.id.deliveryTime);
//            this.goButton = itemView.findViewById(R.id.openMapButton);
//            this.container = itemView.findViewById(R.id.root_layout);
//            this.goButton.setOnClickListener(this);
//            this.container.setOnClickListener(this);
        }

        public void onClick(View v) {

            if(v == this.container){

            }else if(v == this.goButton){

            }
        }
    }

}
