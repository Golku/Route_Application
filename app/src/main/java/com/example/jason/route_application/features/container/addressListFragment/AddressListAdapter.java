package com.example.jason.route_application.features.container.addressListFragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

    private List<Address> addressList;
    private AdapterCallback callback;

    AddressListAdapter(AdapterCallback callback, List<Address> addressList) {
        this.addressList = addressList;
        this.callback = callback;
    }

    interface AdapterCallback{
        void onItemClick(Address address);
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
        holder.streetTv.setText(addressList.get(position).getStreet());
        holder.cityTv.setText(addressList.get(position).getPostCode() +" "+ addressList.get(position).getCity());

        if(addressList.get(position).isValid()){
            holder.addressStatusIv.setImageResource(R.drawable.valid_ic);
        }else{
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
        private ViewGroup addressWrapper;

        CustomViewHolder(View itemView) {
            super(itemView);
            addressWrapper = itemView.findViewById(R.id.address_wrapper);
            streetTv = itemView.findViewById(R.id.street_tv);
            cityTv = itemView.findViewById(R.id.city_tv);
            addressPb = itemView.findViewById(R.id.address_pb);
            addressStatusIv = itemView.findViewById(R.id.address_status_iv);
            addressWrapper.setOnClickListener(this);
        }

        public void onClick(View v) {
            callback.onItemClick(addressList.get(getAdapterPosition()));
        }

    }

}
