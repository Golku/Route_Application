package com.example.jason.route_application_kotlin.features.route;

/**
 * Created by Jason on 07-Feb-18.
 */

public interface MvpRoute {

    interface View{

        void setUpAdapter();



    }

    interface Presenter{

        void getData();

    }

    interface Interactor{
        void getRoute();
    }

}
