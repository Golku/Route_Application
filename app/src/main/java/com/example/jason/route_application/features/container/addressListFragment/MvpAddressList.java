package com.example.jason.route_application.features.container.addressListFragment;

import com.example.jason.route_application.data.pojos.FragmentDelegation;

public interface MvpAddressList {

    interface View{
        void setupAdapter(AddressListAdapter adapter);

        void scrollToLastItem(int position);

        void listItemClick(String address);
    }

    interface Presenter{

        void showAddressList();

        void onDelegation(FragmentDelegation delegation);
    }
}
