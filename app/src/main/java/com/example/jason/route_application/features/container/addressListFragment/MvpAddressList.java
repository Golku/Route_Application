package com.example.jason.route_application.features.container.addressListFragment;

import com.example.jason.route_application.data.pojos.Event;

public interface MvpAddressList {

    interface View{

        void setupAdapter(AddressListAdapter adapter);

        void showAddressInputDialog(String title);

        void postEvent(Event event);

        void scrollToItem(int position);

        void showToast(String message);
    }

    interface Presenter{

        void showAddressList();

        void showDialog(String title);

        void processAddress(String addressString);

        void eventReceived(Event event);
    }
}
