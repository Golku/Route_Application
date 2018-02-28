package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.jason.route_application_kotlin.R;
import java.util.ArrayList;

/**
 * Created by Jason on 26-Feb-18.
 */

public class CorrectInvalidAddressesAdapter extends RecyclerView.Adapter<CorrectInvalidAddressesAdapter.CustomViewHolder> {

    private ArrayList<String> invalidAddressesList;
    private AddressListFunctions addressListFunctions;

    public CorrectInvalidAddressesAdapter(ArrayList<String> invalidAddressesList, AddressListFunctions addressListFunctions) {
        this.invalidAddressesList = invalidAddressesList;
        this.addressListFunctions = addressListFunctions;
    }

    public interface AddressListFunctions{
        void onRemoveAddressButtonClick();
        void onCorrectAddressButtonClick();
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

        private EditText invalidAddress;
        private Button removeAddressBtn;
        private Button correctAddressBtn;
        private ViewGroup container;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.invalidAddress = itemView.findViewById(R.id.invalidAddress);
            this.removeAddressBtn = itemView.findViewById(R.id.removeAddressBtn);
            this.correctAddressBtn = itemView.findViewById(R.id.correctAddressBtn);
            this.container = itemView.findViewById(R.id.root_layout);
            this.removeAddressBtn.setOnClickListener(this);
            this.correctAddressBtn.setOnClickListener(this);
            this.container.setOnClickListener(this);
        }

        public void onClick(View v) {
            if(v == removeAddressBtn){
                addressListFunctions.onRemoveAddressButtonClick();
            }else if(v == correctAddressBtn){
                addressListFunctions.onCorrectAddressButtonClick();
            }
        }

    }
}
