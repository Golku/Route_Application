package com.example.jason.route_application_kotlin.di;

import com.example.jason.route_application_kotlin.features.commentInput.CommentInputActivity;
import com.example.jason.route_application_kotlin.features.commentInput.CommentInputModule;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsActivity;
import com.example.jason.route_application_kotlin.features.addressDetails.AddressDetailsModule;
import com.example.jason.route_application_kotlin.features.correctInvalidAddresses.CorrectInvalidAddressesActivity;
import com.example.jason.route_application_kotlin.features.correctInvalidAddresses.CorrectInvalidAddressesModule;
import com.example.jason.route_application_kotlin.features.login.LoginActivity;
import com.example.jason.route_application_kotlin.features.login.LoginModule;
import com.example.jason.route_application_kotlin.features.container.ContainerActivity;
import com.example.jason.route_application_kotlin.features.container.ContainerModule;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputActivity;
import com.example.jason.route_application_kotlin.features.routeInput.RouteInputModule;
import com.example.jason.route_application_kotlin.features.routeState.RouteStateActivity;
import com.example.jason.route_application_kotlin.features.routeState.RouteStateModule;
import com.example.jason.route_application_kotlin.features.splash.SplashActivity;
import com.example.jason.route_application_kotlin.features.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Jason on 12-Feb-18.
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplash();

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLogin();

    @ContributesAndroidInjector(modules = RouteInputModule.class)
    abstract RouteInputActivity bindRouteInput();

    @ContributesAndroidInjector(modules = RouteStateModule.class)
    abstract RouteStateActivity bindRouteState();

    @ContributesAndroidInjector(modules = ContainerModule.class)
    abstract ContainerActivity bindContainer();

    @ContributesAndroidInjector(modules = AddressDetailsModule.class)
    abstract AddressDetailsActivity bindAddressDetails();

    @ContributesAndroidInjector(modules = CommentInputModule.class)
    abstract CommentInputActivity bindCommentInput();

    @ContributesAndroidInjector(modules = CorrectInvalidAddressesModule.class)
    abstract CorrectInvalidAddressesActivity bindCorrectInvalidAddresses();

}
