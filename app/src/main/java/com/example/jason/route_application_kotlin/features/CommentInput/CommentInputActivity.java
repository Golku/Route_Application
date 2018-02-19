package com.example.jason.route_application_kotlin.features.CommentInput;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class CommentInputActivity extends DaggerAppCompatActivity implements MvpCommentInput.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_input);
        ButterKnife.bind(this);
        init();
    }

    private void init(){

        FormattedAddress formattedAddress = getIntent().getParcelableExtra("formattedAddress");

    }
}
