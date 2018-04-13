package com.example.jason.route_application_kotlin.features.route.listFragment;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.RouteInfoHolder;
import com.example.jason.route_application_kotlin.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application_kotlin.data.pojos.api.SingleDrive;
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
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jason on 3/22/2018.,
 */

public class RouteListFragment extends Fragment implements RouteAdapter.RouteListFunctions, MvpRouteList.View{

    private final String debugTag = "debugTag";

    private MvpRouteList.Presenter presenter;

    private RouteActivity routeActivityCallback;

    private RecyclerView recyclerView;

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
        presenter = new RouteListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_list, container, false);
        this.recyclerView = view.findViewById(R.id.routeRecView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RouteInfoHolder routeInfoHolder = getArguments().getParcelable("routeInfoHolder");
        presenter.initializeAdapter(routeInfoHolder.getRouteList());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setupAdapter(List<SingleDrive> routeList) {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RouteAdapter(routeList, this);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delegation(RouteListFragmentDelegation delegation){
        presenter.onDelegation(delegation);
    }

    @Override
    public void addDriveToList(int position) {
        adapter.notifyItemInserted(position);
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void removeDriveFromList(int position) {
        adapter.notifyItemRemoved(position);
        recyclerView.smoothScrollToPosition(position-1);
    }

    @Override
    public void removeMultipleDriveFromList(int position) {
        adapter.notifyDataSetChanged();
        if(position>0){
            recyclerView.smoothScrollToPosition(position-1);
        }
    }

    @Override
    public void onAdapterListItemClick(String address) {
        routeActivityCallback.onListItemClick(address);
    }

    @Override
    public void onAdapterGoButtonClick(String address) {
        routeActivityCallback.onGoButtonClick(address);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
