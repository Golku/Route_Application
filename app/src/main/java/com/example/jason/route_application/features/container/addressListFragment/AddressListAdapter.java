package com.example.jason.route_application.features.container.addressListFragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.Address;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.CustomViewHolder>{

    private final String debugTag = "debugTag";

    private List<Address> addressList;
    private AdapterCallback callback;
    private int expandedItem = -1;

    AddressListAdapter(AdapterCallback callback, List<Address> addressList) {
        this.addressList = addressList;
        this.callback = callback;
    }

    interface AdapterCallback{
        void itemClick(Address address);
        void showAddress(Address address);
        void removeAddress(Address address);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_address, parent, false);
        return new CustomViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Address address = addressList.get(position);

        if(address.isValid()){
            holder.streetTv.setText(address.getStreet());
            holder.cityTv.setText(address.getPostCode() +" "+ address.getCity());
        }else{
            holder.streetTv.setText(address.getAddress());
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewGroup itemWrapper;
        private TextView streetTv;
        private TextView cityTv;
        private Button showOnMapBtn;


        CustomViewHolder(View itemView) {
            super(itemView);
            itemWrapper = itemView.findViewById(R.id.address_wrapper);
            streetTv = itemView.findViewById(R.id.street_tv);
            cityTv = itemView.findViewById(R.id.city_tv);
            showOnMapBtn = itemView.findViewById(R.id.show_on_map_btn);

            itemWrapper.setOnClickListener(this);
            showOnMapBtn.setOnClickListener(this);
        }

        public void onClick(View v) {

            if (v == itemWrapper) {
                callback.itemClick(addressList.get(getAdapterPosition()));
            } else if (v == showOnMapBtn) {
                callback.showAddress(addressList.get(getAdapterPosition()));
            }
        }
    }
}
