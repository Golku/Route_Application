package com.example.jason.route_application.features.correctInvalidAddresses;
import com.example.jason.route_application.interactors.CorrectInvalidAddressesInteractor;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Jason on 23-Feb-18.
 */

@Module
public abstract class CorrectInvalidAddressesModule {

    @Binds
    public abstract MvpCorrectInvalidAddresses.View provideView(CorrectInvalidAddressesActivity view);

    @Binds
    public abstract MvpCorrectInvalidAddresses.Presenter providePresenter(CorrectInvalidAddressesPresenter presenter);

    @Binds
    public abstract MvpCorrectInvalidAddresses.Interactor provideInteractor(CorrectInvalidAddressesInteractor interactor);


}
