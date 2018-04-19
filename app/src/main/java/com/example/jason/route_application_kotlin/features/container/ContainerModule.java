package com.example.jason.route_application_kotlin.features.container;

import com.example.jason.route_application_kotlin.interactors.RouteInteractor;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Jason on 07-Feb-18.
 */

@Module
public abstract class ContainerModule {

    @Binds
    public abstract MvpContainer.View provideView(ContainerActivity view);

    @Binds
    public abstract MvpContainer.Presenter providePresenter(ContainerPresenter presenter);

    @Binds
    public abstract MvpContainer.Interactor provideInteractor(RouteInteractor interactor);

}
