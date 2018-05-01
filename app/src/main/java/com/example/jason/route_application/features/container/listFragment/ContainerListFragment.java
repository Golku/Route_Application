package com.example.jason.route_application.features.container.listFragment;

import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application.data.pojos.api.Drive;
import com.example.jason.route_application.features.container.ContainerActivity;

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
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jason on 3/22/2018.,
 */

public class ContainerListFragment extends Fragment implements ContainerAdapter.RouteListFunctions, MvpContainerList.View{

    private final String debugTag = "debugTag";

    private MvpContainerList.Presenter presenter;

    private ContainerActivity containerActivityCallback;

    private RecyclerView recyclerView;

    private ContainerAdapter adapter;

    public interface RouteListListener{
        void onListItemClick(String address);
        void onGoButtonClick(String address);
        void showRouteInput();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            containerActivityCallback = (ContainerActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " containerActivityCallback error");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContainerListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container_list, container, false);
        this.recyclerView = view.findViewById(R.id.routeRecView);

        Button routeInputBtn = view.findViewById(R.id.route_input_btn);
        routeInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRouteInput();
            }
        });

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
    public void setupAdapter(List<Drive> routeList) {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContainerAdapter(routeList, this);
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

    private void showRouteInput(){
        containerActivityCallback.showRouteInput();
    }

    @Override
    public void onAdapterListItemClick(String address) {
        containerActivityCallback.onListItemClick(address);
    }

    @Override
    public void onAdapterGoButtonClick(String address) {
        containerActivityCallback.onGoButtonClick(address);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
