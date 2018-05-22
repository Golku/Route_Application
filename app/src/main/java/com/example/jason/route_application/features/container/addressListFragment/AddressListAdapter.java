package com.example.jason.route_application.features.container.addressListFragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.Address;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.CustomViewHolder>{

    private final String debugTag = "debugTag";

    private List<Address> addressList;
    private AdapterCallback callback;

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
            holder.addressStatusIv.setImageResource(R.drawable.valid_ic);
        }else{
            holder.streetTv.setText(address.getAddress());
            holder.addressStatusIv.setImageResource(R.drawable.invalid_ic);
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView streetTv;
        private TextView cityTv;
        private ProgressBar addressPb;
        private ImageView addressStatusIv;
        private ImageView deleteAddressIv;
        private ImageView showAddressIv;
        private ViewGroup addressWrapper;

        CustomViewHolder(View itemView) {
            super(itemView);
            addressWrapper = itemView.findViewById(R.id.address_wrapper);
            streetTv = itemView.findViewById(R.id.street_tv);
            cityTv = itemView.findViewById(R.id.city_tv);
            addressPb = itemView.findViewById(R.id.address_pb);
            addressStatusIv = itemView.findViewById(R.id.address_status_iv);
            deleteAddressIv = itemView.findViewById(R.id.delete_address_iv);
            showAddressIv = itemView.findViewById(R.id.show_address_iv);
            addressWrapper.setOnClickListener(this);
            deleteAddressIv.setOnClickListener(this);
            showAddressIv.setOnClickListener(this);
        }

        public void onClick(View v) {

           if(v == addressWrapper){
               callback.itemClick(addressList.get(getAdapterPosition()));
           }else if(v == showAddressIv){
               callback.showAddress(addressList.get(getAdapterPosition()));
           }else if(v == deleteAddressIv){
                callback.removeAddress(addressList.get(getAdapterPosition()));
           }
        }
    }
}
