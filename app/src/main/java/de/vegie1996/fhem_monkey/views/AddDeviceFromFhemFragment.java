package de.vegie1996.fhem_monkey.views;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.vegie1996.fhem_monkey.R;
import de.vegie1996.fhem_monkey.adapter.AddDeviceFromFhemAdapter;
import de.vegie1996.fhem_monkey.controller.FhemDBController;
import de.vegie1996.fhem_monkey.helper.FHEMMonkeyRESTClient;
import de.vegie1996.fhem_monkey.networking.FHEMConfigResponse;

/**
 * Created by lucienkerl on 26/01/16.
 */
@EFragment(R.layout.fragment_add_device_from_fhem)
@OptionsMenu(R.menu.import_device_fragment)
public class AddDeviceFromFhemFragment extends Fragment {

    @ViewById(R.id.list_view)
    ExpandableListView listView;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @ViewById(R.id.ll_root)
    LinearLayout linearLayout;

    AddDeviceFromFhemAdapter adapter;

    @AfterViews
    public void initViews() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        FHEMMonkeyRESTClient.getFHEMConfig(getActivity(), new FHEMMonkeyRESTClient.FHEMMonkeyRESTClientInterface() {
            @Override
            public void onSuccess(FHEMConfigResponse response) {
                LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>> roomsWithChildsList = new LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>>();

                for (FHEMConfigResponse.FHEMDevice device : response.getResults()) {

                    if (device.getAttributes().get("room") != null) {
                        String room = device.getAttributes().get("room");
                        //room attribute exists
                        if (!roomsWithChildsList.containsKey(room)) {
                            //room not in list - add it
                            roomsWithChildsList.put(room, new ArrayList<FHEMConfigResponse.FHEMDevice>());
                        }

                        //add device to room list
                        roomsWithChildsList.get(room).add(device);
                    }
                }
                //LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>> linkedHashMap = new LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>>()


                setAdapter(roomsWithChildsList);

            }

            @Override
            public void onError(VolleyError error) {

            }
        });


    }

    public void setAdapter(LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>> roomsWithChildsList) {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter = new AddDeviceFromFhemAdapter(getActivity(), roomsWithChildsList, new AddDeviceFromFhemAdapter.CheckedButtonInterface() {
            @Override
            public void checkedButton(int groupPosition, int childPosition, boolean isChecked) {
                Log.d("ButtonClicked", "groupPosition: " + groupPosition + ", childPosition: " + childPosition + ", isChecked: " + isChecked);
            }
        });
        listView.setAdapter(adapter);
    }

    @OptionsItem(R.id.menu_import)
    public void onImportCLicked() {
        List<FHEMConfigResponse.FHEMDevice> checkedDevices = adapter.getCheckedItems();
        FhemDBController dbController = new FhemDBController(getActivity());
        dbController.importIntoDataBase(checkedDevices);

        Snackbar.make(linearLayout, getResources().getString(R.string.import_devices_successfull), Snackbar.LENGTH_SHORT).show();
        adapter.setCheckedItems(new ArrayList<FHEMConfigResponse.FHEMDevice>());
        adapter.notifyDataSetChanged();
    }
}
