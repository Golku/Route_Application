package com.example.jason.route_application.features.container.addressListFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jason.route_application.R;
import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.RouteInfoHolder;
import com.example.jason.route_application.data.pojos.FragmentEvent;

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

    private AlertDialog alertDialog;
    private TextView dialogTitle;
    private EditText streetInput;
    private EditText postcodeLettersInput;
    private EditText postcodeNumbersInput;
    private EditText cityInput;
    private Button cancelDialogBtn;
    private Button addAddressBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setupAdapter(AddressListAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    private void setupAddressInputDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_address_input, null);

        dialogTitle = view.findViewById(R.id.title_tv);
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
    public void addressInputBtnClick(){
        presenter.showDialog("New address");
    }

    @Override
    public void showAddressInputDialog(String title) {
        this.dialogTitle.setText(title);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = streetInput.getText().toString()+", "+
                        postcodeNumbersInput.getText().toString()+" "+
                        postcodeLettersInput.getText().toString()+" "+
                        cityInput.getText().toString()+", "+"Netherlands";
                alertDialog.dismiss();
                if(streetInput.getText().toString().isEmpty()){
                    showToast("Fill in a address");
                }else{
                    presenter.addAddress(address);
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
    public void onActivityEvent(ActivityEvent activityEvent){
        presenter.activityEvent(activityEvent);
    }

    @Override
    public void sendFragmentEvent(FragmentEvent fragmentEvent) {
        EventBus.getDefault().post(fragmentEvent);
    }

    @Override
    public void scrollToItem(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
