package com.example.jason.route_application.interactors;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.database.DatabaseService;
import com.example.jason.route_application.data.pojos.database.AddressInformationResponse;
import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.database.AddressTypeResponse;
import com.example.jason.route_application.features.addressDetails.MvpAddressDetails;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 08-Feb-18.
 */

public class AddressDetailsInteractor implements MvpAddressDetails.Interactor {

    private DatabaseService databaseService;

    @Inject
    public AddressDetailsInteractor(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void getAddressInformation(Address address, final DatabaseCallback.AddressInformationCallBack callback) {

        Call<AddressInformationResponse> call = databaseService.getAddressInformation(
                address.getStreet(),
                address.getPostCode(),
                address.getCity()
        );

        call.enqueue(new Callback<AddressInformationResponse>() {
            @Override
            public void onResponse(Call<AddressInformationResponse> call, Response<AddressInformationResponse> response) {
                callback.onAddressInformationResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddressInformationResponse> call, Throwable t) {
                callback.onAddressInformationResponseFailure();
            }
        });
    }

    @Override
    public void changeAddressType(String username, Address address, final DatabaseCallback.AddressTypeChangeCallback callback) {
        Call<AddressTypeResponse> call = databaseService.changeAddressType(
                address.getStreet(),
                address.getPostCode(),
                address.getCity(),
                username
        );

        call.enqueue(new Callback<AddressTypeResponse>() {
            @Override
            public void onResponse(Call<AddressTypeResponse> call, Response<AddressTypeResponse> response) {
                callback.typeChangeResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddressTypeResponse> call, Throwable t) {
                callback.typeChangeResponseFailure();
            }
        });
    }
}
