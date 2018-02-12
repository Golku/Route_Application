package com.example.jason.route_application_kotlin.features.routeInput;

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

}
