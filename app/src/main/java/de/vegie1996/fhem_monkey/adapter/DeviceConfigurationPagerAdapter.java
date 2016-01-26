package de.vegie1996.fhem_monkey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lucienkerl on 26/01/16.
 */
public class DeviceConfigurationPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList;
    List<String> titlesList;

    public DeviceConfigurationPagerAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fragmentList, List<String> titlesList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titlesList = titlesList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
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
