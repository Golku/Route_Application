package com.example.jason.route_application.interactors;

import com.example.jason.route_application.data.database.DatabaseCallback;
import com.example.jason.route_application.data.database.DatabaseService;
import com.example.jason.route_application.data.pojos.database.AddressInformationResponse;
import com.example.jason.route_application.data.pojos.Address;
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
    public void getAddressInformation(final DatabaseCallback.AddressInformationCallBack callBack, Address address) {

        Call<AddressInformationResponse> call = databaseService.getAddressInformation(
                address.getStreet(),
                address.getPostCode(),
                address.getCity()
        );

        call.enqueue(new Callback<AddressInformationResponse>() {
            @Override
            public void onResponse(Call<AddressInformationResponse> call, Response<AddressInformationResponse> response) {
                callBack.onAddressInformationResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddressInformationResponse> call, Throwable t) {
                callBack.onAddressInformationResponseFailure();
            }
        });
    }
}
