package com.example.jason.route_application_kotlin.features.login;

import com.example.jason.route_application_kotlin.R;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity implements MvpLogin.View{

    private final String debugTag = "debugTag";

    @Inject
    MvpLogin.Presenter presenter;

    @BindView(R.id.username_input)
    EditText usernameInput;
    @BindView(R.id.password_input)
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

    @OnClick(R.id.login_btn)
    @Override
    public void onLoginBtnClick() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        presenter.loginBtnClick(username, password);
    }

    @Override
    public void showContainer() {
        Intent i = new Intent (this, RouteInputActivity.class);
        startActivity(i);
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void closeActivity() {
        finish();
    }
}
