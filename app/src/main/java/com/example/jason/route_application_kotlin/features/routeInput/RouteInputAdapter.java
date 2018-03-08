package com.example.jason.route_application_kotlin.features.routeInput;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jason.route_application_kotlin.R;

import java.util.ArrayList;

/**
 * Created by Jason on 07-Feb-18.
 */

public class RouteInputAdapter extends RecyclerView.Adapter<RouteInputAdapter.CustomViewHolder>{

    private ArrayList<String> listOfAddresses;
    private AddressListFunctions addressListFunctions;

    RouteInputAdapter(ArrayList<String> listOfAddresses, AddressListFunctions addressListFunctions) {
        this.listOfAddresses = listOfAddresses;
        this.addressListFunctions = addressListFunctions;
    }

    public interface AddressListFunctions{
        void onListItemClick(String address);
        void onListItemSwipe(int position);
    }

    void addTouchHelper(RecyclerView recyclerView){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public RouteInputAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_address, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RouteInputAdapter.CustomViewHolder holder, int position) {
        String currentAddress = listOfAddresses.get(position);
        holder.address.setText(currentAddress);
    }

    @Override
    public int getItemCount() {
        return listOfAddresses.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView address;
        private ViewGroup container;

        CustomViewHolder(View itemView) {
            super(itemView);
            this.address = itemView.findViewById(R.id.address);
            this.container = itemView.findViewById(R.id.root_layout);
            this.container.setOnClickListener(this);
        }

        public void onClick(View v) {
            String address = listOfAddresses.get(this.getAdapterPosition());
            addressListFunctions.onListItemClick(address);
        }

    }

    private ItemTouchHelper.Callback createHelperCallback(){

        return new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                addressListFunctions.onListItemSwipe(position);
            }
        };
    }

}
