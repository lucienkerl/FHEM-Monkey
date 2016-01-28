package de.vegie1996.fhem_monkey.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.vegie1996.fhem_monkey.helper.SimpleListItemView;
import de.vegie1996.fhem_monkey.helper.SimpleListItemView_;
import de.vegie1996.fhem_monkey.networking.FHEMConfigResponse;

/**
 * Created by lucienkerl on 26/01/16.
 */
public class AddDeviceFromFhemAdapter extends BaseExpandableListAdapter {

    public List<FHEMConfigResponse.FHEMDevice> checkedItems;
    Context context;
    LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>> groupChildList;
    CheckedButtonInterface checkedButtonInterface;

    public AddDeviceFromFhemAdapter(Context context, LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>> groupChildList, CheckedButtonInterface checkedButtonInterface) {
        this.context = context;
        this.groupChildList = groupChildList;
        this.checkedButtonInterface = checkedButtonInterface;
        this.checkedItems = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return groupChildList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupChildList.get(getKeyByIndex(groupChildList, groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getKeyByIndex(groupChildList, groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupChildList.get(getKeyByIndex(groupChildList, groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = SimpleListItemView_.build(context);
        }

        String value = getKeyByIndex(groupChildList, groupPosition);

        ((SimpleListItemView) view)
                .setTitle(value);

        return view;
    }

    public String getKeyByIndex(LinkedHashMap<String, List<FHEMConfigResponse.FHEMDevice>> hMap, int index) {
        return (String) hMap.keySet().toArray()[index];
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = SimpleListItemView_.build(context);
        }

        final View tmpView = view;
        final FHEMConfigResponse.FHEMDevice fhemDevice = groupChildList.get(getKeyByIndex(groupChildList, groupPosition)).get(childPosition);

        ((SimpleListItemView) view)
                .setTitle(fhemDevice.getName())
                .setIsChildItem()
                .setCheckBox(checkedItems.contains(fhemDevice), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = ((SimpleListItemView) tmpView).getCheckBox().isChecked();
                        checkedButtonInterface.checkedButton(groupPosition, childPosition, isChecked);
                        if (isChecked)
                            checkedItems.add(fhemDevice);
                        else
                            checkedItems.remove(fhemDevice);
                    }
                });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<FHEMConfigResponse.FHEMDevice> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(List<FHEMConfigResponse.FHEMDevice> checkedItems) {
        this.checkedItems = checkedItems;
    }

    public interface CheckedButtonInterface {
        void checkedButton(int groupPosition, int childPosition, boolean isChecked);
    }
}
