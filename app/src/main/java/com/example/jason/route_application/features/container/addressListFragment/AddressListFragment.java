package com.example.jason.route_application.features.container.addressListFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.FragmentDelegation;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.features.container.ContainerActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressListFragment extends Fragment implements MvpAddressList.View{

    @BindView(R.id.address_list)
    RecyclerView recyclerView;

    private final String debugTag = "debugTag";

    private MvpAddressList.Presenter presenter;

    private ContainerActivity containerActivityCallback;

    private AlertDialog alertDialog;
    private TextView title;
    private EditText streetInput;
    private EditText postcodeLettersInput;
    private EditText postcodeNumbersInput;
    private EditText cityInput;
    private Button cancelDialogBtn;
    private Button addAddressBtn;

    public interface AddressListListener{
        void onListItemClick(String address);
        void onAddAddress(String address);
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
        setupAddressInputDialog();
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

    private void setupAddressInputDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_address_input, null);

        title = view.findViewById(R.id.title_tv);
        streetInput = view.findViewById(R.id.street_input);
        postcodeNumbersInput = view.findViewById(R.id.postcode_numbers_input);
        postcodeLettersInput = view.findViewById(R.id.postcode_letters_input);
        cityInput = view.findViewById(R.id.city_input);
        addAddressBtn = view.findViewById(R.id.add_address_btn);
        cancelDialogBtn = view.findViewById(R.id.cancel_dialog_btn);

        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
    }

    @OnClick(R.id.address_input_btn)
    @Override
    public void showAddressInputDialog() {
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!streetInput.getText().toString().isEmpty()) {
                    String address = streetInput.getText().toString()+", "+
                            postcodeNumbersInput.getText().toString()+" "+
                            postcodeLettersInput.getText().toString()+" "+
                            cityInput.getText().toString()+", "+"Netherlands";
                    containerActivityCallback.onAddAddress(address);
                }else{
                    showToast("Fill in a address");
                }
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
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
    public void showToast(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
