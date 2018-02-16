package com.example.jason.route_application_kotlin.features.addressDetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.AddressInformation;
import java.util.ArrayList;

/**
 * Created by Jason on 07-Feb-18.
 */

public class AddressDetailsAdapter extends RecyclerView.Adapter <AddressDetailsAdapter.CustomViewHolder>{

    private AddressInformation addressInformation;
    private ArrayList<String> commentInformation;
    private CommentListFunctions commentListFunctions;


    public AddressDetailsAdapter(AddressInformation addressInformation, CommentListFunctions commentListFunctions) {
        this.addressInformation = addressInformation;
        this.commentListFunctions = commentListFunctions;
    }

    public interface CommentListFunctions{
        void onListItemClick(ArrayList<String> commentInformation);
    }

    @Override
    public AddressDetailsAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.employedName.setText(addressInformation.getEmployeeId().get(position));
        holder.date.setText(addressInformation.getDates().get(position));
        holder.comment.setText(addressInformation.getComments().get(position));
    }

    @Override
    public int getItemCount() {
        return addressInformation.getCommentsCount();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView employedName;
        private TextView date;
        private TextView comment;
        private ViewGroup container;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.employedName = itemView.findViewById(R.id.employeedNameTextView);
            this.date = itemView.findViewById(R.id.dateTextView);
            this.comment = itemView.findViewById(R.id.commentTextView);
            this.container = itemView.findViewById(R.id.root_layout);
            this.container.setOnClickListener(this);
        }

        public void onClick(View v) {

//                Log.d(log_tag, "Adapter position: " + this.getAdapterPosition());
//                Log.d(log_tag, "List info at index 0: " + singelAddressDbInformation.getEmployeeId().get(0));
//                Log.d(log_tag, "List info: " + singelAddressDbInformation.getEmployeeId().get(this.getAdapterPosition()));

            commentInformation.add(0, addressInformation.getEmployeeId().get(this.getAdapterPosition()));
            commentInformation.add(1, addressInformation.getDates().get(this.getAdapterPosition()));
            commentInformation.add(2, addressInformation.getComments().get(this.getAdapterPosition()));

            commentListFunctions.onListItemClick(commentInformation);
        }
    }

}
