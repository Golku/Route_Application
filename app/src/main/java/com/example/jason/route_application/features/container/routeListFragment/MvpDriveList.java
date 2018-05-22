package com.example.jason.route_application.features.container.routeListFragment;

import com.example.jason.route_application.data.pojos.Event;

/**
 * Created by Jason on 03-Apr-18.
 */

public interface MvpDriveList {

    interface View{

        void setupAdapter(DriveListAdapter adapter);

        void postEvent(Event event);

        void scrollToItem(int position);
    }

    interface Presenter{

        void showDriveList();

        void eventReceived(Event event);
    }

}
