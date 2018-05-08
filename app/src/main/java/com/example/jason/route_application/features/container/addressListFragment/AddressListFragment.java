package com.example.jason.route_application.features.container.addressListFragment;

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
import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.FragmentDelegation;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.features.container.ContainerActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListFragment extends Fragment implements MvpAddressList.View{

    @BindView(R.id.add_address_btn)
    FloatingActionButton addressInputBtn;
    @BindView(R.id.address_list)
    RecyclerView recyclerView;

    private final String debugTag = "debugTag";

    private MvpAddressList.Presenter presenter;

    private ContainerActivity containerActivityCallback;

    public interface AddressListListener{
        void onListItemClick(String address);
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
        presenter = new AddressListPresenter(this, routeInfoHolder.getAddressList());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.showAddressList();
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
    public void setupAdapter(AddressListAdapter adapter) {
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
}
