package com.example.jason.route_application_kotlin.features.route.mapFragment;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;
import com.example.jason.route_application_kotlin.data.pojos.UnOrganizedRoute;

import java.util.List;

/**
 * Created by Jason on 3/28/2018.
 */

public interface MvpRouteMap {

    interface View{

    }

    interface Presenter{
        void setRoute(UnOrganizedRoute unOrganizedRoute);
        List<FormattedAddress> getAddressesList();
    }

}
