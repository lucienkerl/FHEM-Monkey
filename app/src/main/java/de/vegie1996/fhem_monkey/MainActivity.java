package de.vegie1996.fhem_monkey;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.vegie1996.fhem_monkey.adapter.RecyclerViewAdapter;
import de.vegie1996.fhem_monkey.database.FhemMonkeyDatabase;
import de.vegie1996.fhem_monkey.database.tables.LevelsTable;
import de.vegie1996.fhem_monkey.helper.FHEMMonkeyRESTClient;
import de.vegie1996.fhem_monkey.networking.FHEMConfigResponse;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main_activity)
public class MainActivity extends FHEMMonkeyActivity {

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews
    public void initViews() {
        downloadFHEMConfigAndSetAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerView != null)
            downloadFHEMConfigAndSetAdapter();
    }
    //endregion

    public void downloadFHEMConfigAndSetAdapter() {
        FHEMMonkeyRESTClient.getFHEMConfig(getApplicationContext(), new FHEMMonkeyRESTClient.FHEMMonkeyRESTClientInterface() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("MainActivity", "onSuccess: " + response.toString());

                String s = "";
                FHEMConfigResponse fhemConfigResponse = new FHEMConfigResponse();
                try {
                    fhemConfigResponse.fillFromJSON(response);
                    for (FHEMConfigResponse.FHEMDevice device : fhemConfigResponse.getResults()) {
                        s = s + device.getName() + "\n";
                    }

                    GridLayoutManager llm = new GridLayoutManager(getApplicationContext(), 4);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setScrollbarFadingEnabled(true);

                    recyclerView.setVisibility(View.VISIBLE);
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(fhemConfigResponse.getResults());
                    recyclerView.setAdapter(recyclerViewAdapter);

                    importToDatabase(fhemConfigResponse);
                    //textView.setText(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("MainActivity", "onError: " + error.getLocalizedMessage());
            }
        });
    }

    public void importToDatabase(FHEMConfigResponse response) {
        // Collect all rooms from the response
        Set<String> rooms = new HashSet<>();
        SQLiteDatabase db = new FhemMonkeyDatabase(this).getWritableDatabase();
        db.execSQL("DELETE FROM " + LevelsTable.TABLE_NAME);
        for (FHEMConfigResponse.FHEMDevice device : response.getResults()) {
            if (device.getInternals().get("TYPE").equals("notify")) {
                continue;
            }
            int idOfDeviceType;
            if (device.getAttributes().containsKey("room") && !rooms.contains(device.getAttributes().get("room"))) {
                // Room doesn't exist yet, create it
                LevelsTable.LevelsEntry level = new LevelsTable.LevelsEntry();
                level.setName(device.getAttributes().get("room"));
                db.insert(LevelsTable.TABLE_NAME, null, level.getContentValues());
                rooms.add(device.getAttributes().get("room"));
            }

            if (!device.getAttributes().containsKey("room")) {
                continue;
            }

            // Room exists, check if type exists in room
            // First we need the id of the room
            Cursor c = db.query(LevelsTable.TABLE_NAME,
                    LevelsTable.getColumns(),
                    LevelsTable.COLUMNNAME_LEVELS_NAME + " = ?",
                    new String[] { device.getAttributes().get("room") },
                    null,
                    null,
                    null);
            LevelsTable.LevelsEntry level = LevelsTable.LevelsEntry.fromCursor(c).get(0);

            int roomId = level.getId();
            String strDeviceType = device.getInternals().get("TYPE");

            // Then we need to check if there is an entry with the room id and the type in our database
            c = db.query(LevelsTable.TABLE_NAME,
                    LevelsTable.getColumns(),
                    LevelsTable.COLUMNNAME_LEVELS_NAME + " = ? AND " + LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                    new String[] { strDeviceType, Integer.toString(roomId) },
                    null,
                    null,
                    null);

            if (c.getCount() > 0) {
                // type already exists, we need to get the id
                LevelsTable.LevelsEntry deviceType = LevelsTable.LevelsEntry.fromCursor(c).get(0);
                idOfDeviceType = deviceType.getId();
            }
            else {
                // type does not exist yet, need to create it
                level = new LevelsTable.LevelsEntry();
                level.setName(strDeviceType);
                level.setParentId(roomId);
                idOfDeviceType = (int) db.insert(LevelsTable.TABLE_NAME, null, level.getContentValues());
            }


            // Now we can store the device itself in our database
            level = LevelsTable.LevelsEntry.fromDevice(device, idOfDeviceType);
            db.insert(LevelsTable.TABLE_NAME, null, level.getContentValues());
        }

        // To check if stored everything correctly, print it to the console
        Cursor c = db.query(LevelsTable.TABLE_NAME,
                LevelsTable.getColumns(),
                LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                new String[] { "-1" },
                null,
                null,
                null);
        List<LevelsTable.LevelsEntry> dbRooms = LevelsTable.LevelsEntry.fromCursor(c);
        for (LevelsTable.LevelsEntry room : dbRooms) {
            Log.d("Database", room.getName());

            c = db.query(LevelsTable.TABLE_NAME,
                    LevelsTable.getColumns(),
                    LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                    new String[] { Integer.toString(room.getId()) },
                    null,
                    null,
                    null);
            List<LevelsTable.LevelsEntry> deviceTypes = LevelsTable.LevelsEntry.fromCursor(c);
            for (LevelsTable.LevelsEntry deviceType : deviceTypes) {
                Log.d("Database", "   " + deviceType.getName());

                c = db.query(LevelsTable.TABLE_NAME,
                        LevelsTable.getColumns(),
                        LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                        new String[] { Integer.toString(deviceType.getId()) },
                        null,
                        null,
                        null);
                List<LevelsTable.LevelsEntry> devices = LevelsTable.LevelsEntry.fromCursor(c);

                for (LevelsTable.LevelsEntry device : devices) {
                    Log.d("Database", "      " + device.getName());
                }
            }
        }
    }

    @OptionsItem(R.id.menu_settings)
    public void onSettingsClicked() {
        SettingsActivity_.intent(this).start();
    }
}
