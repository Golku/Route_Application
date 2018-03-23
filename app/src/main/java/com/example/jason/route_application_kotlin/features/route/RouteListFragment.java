package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 3/22/2018.
 */

public class RouteListFragment extends Fragment implements RouteAdapter.RouteListFunctions{

    private RouteActivity routeActivityCallback;

    private RecyclerView recyclerView;
    private TextView privateAddressesTextView;
    private TextView businessAddressesTextView;
    private TextView messageToUserTextView;
    private ProgressBar progressBar;
    private Button btn;

    public interface RouteListListener{
        void onListItemClick(String address);
        void onGoButtonClick(String address);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            routeActivityCallback = (RouteActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_list, container, false);

        this.recyclerView = view.findViewById(R.id.routeRecView);
        this.privateAddressesTextView = view.findViewById(R.id.privateAddressesTextView);
        this.businessAddressesTextView = view.findViewById(R.id.businessAddressesTextView);
        this.messageToUserTextView = view.findViewById(R.id.messageToUserTextView);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.btn = view.findViewById(R.id.testBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListItemClick("Ets 4, capelle aan den ijssel");
            }
        });
        return view;
    }

    public void setUpAdapter(OrganizedRoute organizedRoute) {
        privateAddressesTextView.setText(String.valueOf(organizedRoute.getPrivateAddressesCount()));
        businessAddressesTextView.setText(String.valueOf(organizedRoute.getBusinessAddressesCount()));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RouteAdapter adapter = new RouteAdapter(organizedRoute, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(String address) {
        routeActivityCallback.onListItemClick(address);
    }

    @Override
    public void onGoButtonClick(String address) {
        routeActivityCallback.onGoButtonClick(address);
    }

    public void onStartNetworkOperation() {
        messageToUserTextView.setText("Fetching route...");
    }

    public void onFinishNetworkOperation() {
        messageToUserTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

}
