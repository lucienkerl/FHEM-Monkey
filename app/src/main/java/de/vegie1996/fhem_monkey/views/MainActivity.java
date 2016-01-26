package de.vegie1996.fhem_monkey.views;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.vegie1996.fhem_monkey.R;
import de.vegie1996.fhem_monkey.adapter.RecyclerViewAdapter;
import de.vegie1996.fhem_monkey.controller.FhemDBController;
import de.vegie1996.fhem_monkey.database.tables.LevelsTable;
import de.vegie1996.fhem_monkey.helper.FHEMMonkeyActivity;
import de.vegie1996.fhem_monkey.helper.RecyclerItemClickListener;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main_activity)
public class MainActivity extends FHEMMonkeyActivity {

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews
    public void initViews() {
        FhemDBController dbController = new FhemDBController(getApplicationContext());
        List<LevelsTable.LevelsEntry> levelsEntryList = dbController.getAllLevelEntryList();
        if (levelsEntryList.size() > 0) {
            setAdapter(levelsEntryList);
        }
    }

    public void setAdapter(final List<LevelsTable.LevelsEntry> levelsEntryList) {
        GridLayoutManager llm = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(llm);
        recyclerView.setScrollbarFadingEnabled(true);

        recyclerView.setVisibility(View.VISIBLE);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(levelsEntryList);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                FhemDBController controller = new FhemDBController(getApplicationContext());
                List<LevelsTable.LevelsEntry> entryList = controller.getLevelEntryListWithParentId(levelsEntryList.get(position).getId());
                setAdapter(entryList);
                Log.d("MainActivity", "item: " + levelsEntryList.get(position).getId());
            }
        }));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OptionsItem(R.id.menu_settings)
    public void onSettingsClicked() {
        SettingsActivity_.intent(this).start();
    }

    @OptionsItem(R.id.menu_device_configuration)
    public void onDeviceConfigurationClicked() {
        DeviceConfigurationActivity_.intent(this).start();
    }
}
