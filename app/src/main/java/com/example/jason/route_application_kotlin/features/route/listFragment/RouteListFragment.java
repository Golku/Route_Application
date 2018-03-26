package com.example.jason.route_application_kotlin.features.route.listFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Jason on 3/22/2018.
 */

public class RouteListFragment extends Fragment implements RouteAdapter.RouteListFunctions {

    private final String logTag = "logDebugTag";

    private RouteActivity routeActivityCallback;

    private RecyclerView recyclerView;
    private TextView messageToUserTextView;
    private ProgressBar progressBar;

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
                    + " routeActivityCallback error");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_list, container, false);

        this.recyclerView = view.findViewById(R.id.routeRecView);
        this.messageToUserTextView = view.findViewById(R.id.messageToUserTextView);
        this.progressBar = view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            OrganizedRoute organizedRoute = bundle.getParcelable("organizedRoute");
            setUpAdapter(organizedRoute);
        }
    }

    public void setUpAdapter(OrganizedRoute organizedRoute) {
        onFinishNetworkOperation();
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RouteAdapter adapter = new RouteAdapter(organizedRoute, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAdapterListItemClick(String address) {
        routeActivityCallback.onListItemClick(address);
    }

    @Override
    public void onAdapterGoButtonClick(String address) {
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
