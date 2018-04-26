package com.example.jason.route_application.features.routeInput;

import com.example.jason.route_application.interactors.RouteInputInteractor;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Jason on 07-Feb-18.
 */
@Module
public abstract class RouteInputModule {

    @Binds
    public abstract MvpRouteInput.View provideView(RouteInputActivity view);

    @Binds
    public abstract MvpRouteInput.Presenter providePresenter(RouteInputPresenter presenter);

    @Binds
    public abstract MvpRouteInput.Interactor provideInteractor(RouteInputInteractor interactor);

}
