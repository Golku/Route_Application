package com.example.jason.route_application_kotlin.features.route;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/22/2018.
 */

public class RouteSectionPagerAdapter extends FragmentPagerAdapter{

    private List<String>fragmentTitle = new ArrayList<>();
    private List<Fragment>fragmentList = new ArrayList<>();

    RouteSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void addFragment(String title, Fragment fragment){
        fragmentTitle.add(title);
        fragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
