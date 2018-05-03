package com.example.jason.route_application.features.container.listFragment;

import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application.features.container.ContainerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 3/22/2018.,
 */

public class ContainerListFragment extends Fragment implements MvpContainerList.View{

    @BindView(R.id.address_list_btn)
    Button addressListBtn;
    @BindView(R.id.route_list_btn)
    Button routeListBtn;
    @BindView(R.id.route_input_btn)
    Button routeInputBtn;
    @BindView(R.id.address_input_btn)
    FloatingActionButton addressInputBtn;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private final String debugTag = "debugTag";

    private MvpContainerList.Presenter presenter;

    private ContainerActivity containerActivityCallback;

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
        ButterKnife.bind(this, view);

//        this.recyclerView = view.findViewById(R.id.address_input_btn);
//
//        Button routeInputBtn = view.findViewById(R.id.route_input_btn);
//        routeInputBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showRouteInput();
//            }
//        });
//
//        Button routeInputBtn = view.findViewById(R.id.route_input_btn);
//        routeInputBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showRouteInput();
//            }
//        });



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RouteInfoHolder routeInfoHolder = null;
        if (getArguments() != null) {
            routeInfoHolder = getArguments().getParcelable("routeInfoHolder");
        }
        presenter.setupList(routeInfoHolder.getAddressList(), routeInfoHolder.getRouteList());
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
    public void setupAddressAdapter(ContainerAddressesAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setupRouteAdapter(ContainerRouteAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delegation(RouteListFragmentDelegation delegation){
        presenter.onDelegation(delegation);
    }

    @Override
    public void scrollToLastItem(int position) {
        if(position>0){
            recyclerView.smoothScrollToPosition(position-1);
        }
    }

    @OnClick(R.id.address_list_btn)
    public void onAddressListBtnClick(){
        presenter.showAddressList();
    }

    @OnClick(R.id.route_list_btn)
    public void onRouteListBtnClick(){
        presenter.showRouteList();
    }

    @OnClick(R.id.route_input_btn)
    public void showRouteInput(){
        containerActivityCallback.showRouteInput();
    }

    @Override
    public void listItemClick(String address) {
        containerActivityCallback.onListItemClick(address);
    }

    @Override
    public void goButtonClick(String address) {
        containerActivityCallback.onGoButtonClick(address);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
