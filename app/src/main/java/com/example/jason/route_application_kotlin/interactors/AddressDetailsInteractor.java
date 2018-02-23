package com.example.jason.route_application_kotlin.interactors;

import android.util.Log;

import com.example.jason.route_application_kotlin.data.database.DatabasePresenterCallBack;
import com.example.jason.route_application_kotlin.data.database.DatabaseService;
import com.example.jason.route_application_kotlin.data.pojos.DatabaseResponse;
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
    public void getAddressInformation(final DatabasePresenterCallBack databasePresenterCallBack, FormattedAddress formattedAddress) {

        Call<DatabaseResponse> call = databaseService.getAddressInformation(
                formattedAddress.getStreet(),
                formattedAddress.getPostCode(),
                formattedAddress.getCity()
        );

        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(Call<DatabaseResponse> call, Response<DatabaseResponse> response) {
                Log.d("detailsLogTag", "Responded");
                databasePresenterCallBack.processDatabaseResponse(response.body());
            }

            @Override
            public void onFailure(Call<DatabaseResponse> call, Throwable t) {
                Log.d("detailsLogTag", "Failure");
                Log.d("detailsLogTag", "Throwable: " + t.toString());
                Log.d("detailsLogTag", "call: " + call.toString());
            }
        });
    }
}
