package de.vegie1996.fhem_monkey.views;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.vegie1996.fhem_monkey.R;
import de.vegie1996.fhem_monkey.adapter.DeviceConfigurationPagerAdapter;
import de.vegie1996.fhem_monkey.helper.FHEMMonkeyActivity;

@EActivity(R.layout.activity_device_configuration)
public class DeviceConfigurationActivity extends FHEMMonkeyActivity {

    @ViewById(R.id.pager)
    ViewPager viewPager;

    @AfterViews
    public void initViews() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        fragmentList.add(AddDeviceFromFhemFragment_.builder().build());
        titleList.add(getResources().getString(R.string.import_devices));

        DeviceConfigurationPagerAdapter adapter = new DeviceConfigurationPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DeviceConfigurationFragment fragment = DeviceConfigurationFragment_.builder().build();

        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();*/
    }
}
