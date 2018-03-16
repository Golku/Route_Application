package com.example.jason.route_application_kotlin.features.routeState;

import com.example.jason.route_application_kotlin.interactors.RouteStateInteractor;
import dagger.Binds;
import dagger.Module;

/**
 * Created by Jason on 3/15/2018.
 */

@Module
public abstract class RouteStateModule {

    @Binds
    public abstract MvpRouteState.View provideView(RouteStateActivity view);

    @Binds
    public abstract MvpRouteState.Presenter providePresenter(RouteStatePresenter presenter);

    @Binds
    public abstract MvpRouteState.Interactor provideInteractor(RouteStateInteractor interactor);

}
