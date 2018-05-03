package com.example.jason.route_application.features.container.listFragment;

import com.example.jason.route_application.data.pojos.Address;
import com.example.jason.route_application.data.pojos.RouteListFragmentDelegation;
import com.example.jason.route_application.data.pojos.api.Drive;

import java.util.List;

/**
 * Created by Jason on 03-Apr-18.
 */

public interface MvpContainerList {

    interface View{

        void setupAddressAdapter(ContainerAddressesAdapter adapter);

        void setupRouteAdapter(ContainerRouteAdapter adapter);

        void scrollToLastItem(int position);

        void listItemClick(String address);

        void goButtonClick(String address);

        void showToast(String message);
    }

    interface Presenter{

        void setupList(List<Address> addressList, List<Drive> routeList);

        void showAddressList();

        void showRouteList();

        void onDelegation(RouteListFragmentDelegation delegation);
    }

}
