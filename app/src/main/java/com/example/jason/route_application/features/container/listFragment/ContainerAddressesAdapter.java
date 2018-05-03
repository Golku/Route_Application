package com.example.jason.route_application.features.container.listFragment;

import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.Address;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContainerAddressesAdapter extends RecyclerView.Adapter<ContainerAddressesAdapter.CustomViewHolder>{

    private List<Address> addressList;
    private AddressAdapterFunctions addressAdapterFunctions;

    ContainerAddressesAdapter(AddressAdapterFunctions addressAdapterFunctions, List<Address> addressList) {
        this.addressList = addressList;
        this.addressAdapterFunctions = addressAdapterFunctions;
    }

    public interface AddressAdapterFunctions {
        void addressAdapterItemClick(String address);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_address, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.address.setText(addressList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView address;
        private ViewGroup addressWrapper;

        CustomViewHolder(View itemView) {
            super(itemView);
            addressWrapper = itemView.findViewById(R.id.address_wrapper);
            address = itemView.findViewById(R.id.address_text_view);
            addressWrapper.setOnClickListener(this);
        }

        public void onClick(View v) {
            addressAdapterFunctions.addressAdapterItemClick(addressList.get(getAdapterPosition()).getAddress());
        }

    }
}
