package com.example.jason.route_application_kotlin.features.correctInvalidAddresses;

import android.os.Bundle;
import com.example.jason.route_application_kotlin.R;
import dagger.android.support.DaggerAppCompatActivity;

public class CorrectInvalidAddressesActivity extends DaggerAppCompatActivity implements MvpCorrectInvalidAddresses.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_invalid_addresses);
    }




}
