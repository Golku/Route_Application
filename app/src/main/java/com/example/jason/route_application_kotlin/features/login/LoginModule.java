package com.example.jason.route_application_kotlin.features.login;

import com.example.jason.route_application_kotlin.interactors.LoginInteractor;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginModule {

    @Binds
    public abstract MvpLogin.View provideView(LoginActivity view);

    @Binds
    public abstract MvpLogin.Presenter providePresenter(LoginPresenter presenter);

    @Binds
    public abstract MvpLogin.Interactor provideInteractor(LoginInteractor interactor);

}
