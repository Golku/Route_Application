package com.example.jason.route_application_kotlin.features.route.listFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.models.FragmentCommunication;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.OrganizedRoute;
import com.example.jason.route_application_kotlin.data.pojos.SingleDrive;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;
import com.example.jason.route_application_kotlin.features.route.RouteActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/22/2018.,
 */

public class RouteListFragment extends Fragment implements RouteAdapter.RouteListFunctions, MvpRouteList.View{

    private final String logTag = "logDebugTag";

    private MvpRouteList.Presenter presenter;

    private RouteActivity routeActivityCallback;

    private RecyclerView recyclerView;
    private TextView messageToUserTextView;
    private ProgressBar progressBar;

    private RouteAdapter adapter;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new RouteListPresenter(this);
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
        EventBus.getDefault().register(this);
        presenter.setupList();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setupAdapter(List<SingleDrive> routeList) {
        onFinishNetworkOperation();
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new RouteAdapter(routeList, this);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FragmentCommunication fragmentCommunication){
        adapter.addToList(fragmentCommunication.getSingleDrive());
        adapter.notifyItemInserted(adapter.getList().size()-1);
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
