package com.example.jason.route_application.features.container.addressListFragment;

import com.example.jason.route_application.data.pojos.ActivityEvent;
import com.example.jason.route_application.data.pojos.FragmentEvent;

public interface MvpAddressList {

    interface View{

        void setupAdapter(AddressListAdapter adapter);

        void showAddressInputDialog(String title);

        void sendFragmentEvent(FragmentEvent fragmentEvent);

        void scrollToItem(int position);

        void showToast(String message);
    }

    interface Presenter{

        void showAddressList();

        void showDialog(String title);

        void addAddress(String addressString);

        void activityEvent(ActivityEvent activityEvent);
    }
}
