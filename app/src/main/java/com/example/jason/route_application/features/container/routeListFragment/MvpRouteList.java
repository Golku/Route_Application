package com.example.jason.route_application.features.container.routeListFragment;

import com.example.jason.route_application.data.pojos.FragmentDelegation;

/**
 * Created by Jason on 03-Apr-18.
 */

public interface MvpRouteList {

    interface View{

        void setupAdapter(RouteListAdapter adapter);

        void scrollToLastItem(int position);

        void listItemClick(String address);

        void goButtonClick(String address);
    }

    interface Presenter{

        void showRouteList();

        void onDelegation(FragmentDelegation delegation);
    }

}
