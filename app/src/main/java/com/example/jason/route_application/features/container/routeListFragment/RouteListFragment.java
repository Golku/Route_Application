package com.example.jason.route_application.features.container.routeListFragment;

import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.FragmentDelegation;
import com.example.jason.route_application.features.container.ContainerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jason on 3/22/2018.,
 */

public class RouteListFragment extends Fragment implements MvpRouteList.View{

    @BindView(R.id.route_list)
    RecyclerView recyclerView;

    private final String debugTag = "debugTag";

    private MvpRouteList.Presenter presenter;

    private ContainerActivity containerActivityCallback;

    public interface RouteListListener{
        void onListItemClick(String address);
        void onGoButtonClick(String address);
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
        RouteInfoHolder routeInfoHolder = getArguments().getParcelable("routeInfoHolder");
        presenter = new RouteListPresenter(this, routeInfoHolder.getRouteList());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.showRouteList();
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
    public void setupAdapter(RouteListAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delegate(FragmentDelegation delegation){
        presenter.onDelegation(delegation);
    }

    @Override
    public void scrollToLastItem(int position) {
        if(position>0){
            recyclerView.smoothScrollToPosition(position-1);
        }
    }

    @Override
    public void listItemClick(String address) {
        containerActivityCallback.onListItemClick(address);
    }

    @Override
    public void goButtonClick(String address) {
        containerActivityCallback.onGoButtonClick(address);
    }
}