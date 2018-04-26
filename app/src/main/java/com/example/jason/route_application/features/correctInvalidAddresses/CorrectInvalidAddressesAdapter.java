package com.example.jason.route_application.features.correctInvalidAddresses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jason.route_application.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 26-Feb-18.
 */

public class CorrectInvalidAddressesAdapter extends RecyclerView.Adapter<CorrectInvalidAddressesAdapter.CustomViewHolder> {

    private List<String> invalidAddressesList;
    private AddressListFunctions addressListFunctions;

    CorrectInvalidAddressesAdapter(List<String> invalidAddressesList, AddressListFunctions addressListFunctions) {
        this.invalidAddressesList = invalidAddressesList;
        this.addressListFunctions = addressListFunctions;
    }

    public interface AddressListFunctions{
        void onRemoveAddressButtonClick(int position);
        void onListItemClick(int position);
    }

    @Override
    public CorrectInvalidAddressesAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invalid_addresses, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CorrectInvalidAddressesAdapter.CustomViewHolder holder, int position) {
        String currentAddress = invalidAddressesList.get(position);
        holder.invalidAddress.setText(currentAddress);
    }

    @Override
    public int getItemCount() {
        return invalidAddressesList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView invalidAddress;
        private ImageView deleteAddressBtn;
        private ViewGroup container;


        CustomViewHolder(View itemView) {
            super(itemView);
            this.invalidAddress = itemView.findViewById(R.id.invalidAddressTextView);
            this.deleteAddressBtn = itemView.findViewById(R.id.deleteAddressBtn);
            this.container = itemView.findViewById(R.id.root_layout);
            this.deleteAddressBtn.setOnClickListener(this);
            this.container.setOnClickListener(this);
        }

        public void onClick(View v) {
            if(v == deleteAddressBtn){
                addressListFunctions.onRemoveAddressButtonClick(this.getAdapterPosition());
            }else if(v == container){
                addressListFunctions.onListItemClick(this.getAdapterPosition());
            }
        }

    }
}
