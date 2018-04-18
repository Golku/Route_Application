package com.example.jason.route_application_kotlin.interactors;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;
import com.example.jason.route_application_kotlin.data.database.DatabaseService;
import com.example.jason.route_application_kotlin.data.pojos.database.AddressInformationResponse;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.features.addressDetails.MvpAddressDetails;
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
    public void getAddressInformation(final DatabaseCallback.AddressInformationCallBack callBack, FormattedAddress formattedAddress) {

        Call<AddressInformationResponse> call = databaseService.getAddressInformation(
                formattedAddress.getStreet(),
                formattedAddress.getPostCode(),
                formattedAddress.getCity()
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
