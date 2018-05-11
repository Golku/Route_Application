package com.example.jason.route_application.features.container.routeListFragment;

import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.FragmentEvent;

/**
 * Created by Jason on 03-Apr-18.
 */

public interface MvpDriveList {

    interface View{

        void setupAdapter(DriveListAdapter adapter);

        void sendFragmentEvent(FragmentEvent fragmentEvent);

        void scrollToItem(int position);
    }

    interface Presenter{

        void showDriveList();

        void activityEvent(ActivityEvent activityEvent);
    }

}
