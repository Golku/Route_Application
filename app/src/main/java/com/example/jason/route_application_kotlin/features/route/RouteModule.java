package com.example.jason.route_application_kotlin.features.route;

import com.example.jason.route_application_kotlin.interactors.RouteInteractor;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Jason on 07-Feb-18.
 */

@Module
public abstract class RouteModule {

    @Binds
    public abstract MvpRoute.View provideView(RouteActivity view);

    @Binds
    public abstract MvpRoute.Presenter providePresenter(RouteApiPresenter presenter);

    @Binds
    public abstract MvpRoute.Interactor provideInteractor(RouteInteractor interactor);

}
