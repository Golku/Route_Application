package com.example.jason.route_application_kotlin.features.login;

import com.example.jason.route_application_kotlin.data.database.DatabaseCallback;
import com.example.jason.route_application_kotlin.data.pojos.database.LoginResponse;

import javax.inject.Inject;

public class LoginPresenter implements MvpLogin.Presenter, DatabaseCallback.LoginCallBack{

    private final String debugTag = "debugTag";

    private MvpLogin.View view;
    private MvpLogin.Interactor interactor;

    @Inject
    LoginPresenter(MvpLogin.View view, MvpLogin.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void loginBtnClick(String username, String password) {

        //incription
        String encryptedUsername = encryptInput(username);
        String encryptedPassword = encryptInput(password);

        interactor.loginRequest(encryptedUsername, encryptedPassword, this);

    }

    private String encryptInput (String input){
        return input;
    }

    @Override
    public void onLoginResponse(LoginResponse response) {
        if (response == null) {return;}

        if(response.isMatch()){
//            begin session
//            and start containerActivity

            view.showContainer();
            view.closeActivity();
        }else{
            view.showToast("No match");
        }
    }

    @Override
    public void onLoginResponseFailure() {
        view.showToast("Failed");
    }
}
