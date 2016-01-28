package de.vegie1996.fhem_monkey.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.vegie1996.fhem_monkey.database.FhemMonkeyDatabase;
import de.vegie1996.fhem_monkey.database.tables.LevelsTable;
import de.vegie1996.fhem_monkey.networking.FHEMConfigResponse;

/**
 * Created by lucienkerl on 25/01/16.
 */
public class FhemDBController {

    Context context;

    public FhemDBController(Context context) {
        this.context = context;
    }

    public List<LevelsTable.LevelsEntry> getAllLevelEntryList() {
        SQLiteDatabase db = new FhemMonkeyDatabase(context).getWritableDatabase();
        Cursor c = db.query(LevelsTable.TABLE_NAME,
                new String[]{LevelsTable.COLUMNNAME_LEVELS_ID,
                        LevelsTable.COLUMNNAME_LEVELS_NAME,
                        LevelsTable.COLUMNNAME_LEVELS_FHEMNAME,
                        LevelsTable.COLUMNNAME_LEVELS_ICON,
                        LevelsTable.COLUMNNAME_LEVELS_PARENT_ID,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT},
                LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                new String[]{"-1"},
                null,
                null,
                null);
        List<LevelsTable.LevelsEntry> dbRooms = LevelsTable.LevelsEntry.fromCursor(c);
        return dbRooms;
    }

    public List<LevelsTable.LevelsEntry> getLevelEntryListWithParentId(int parentId) {
        SQLiteDatabase db = new FhemMonkeyDatabase(context).getWritableDatabase();
        Cursor c = db.query(LevelsTable.TABLE_NAME,
                new String[]{LevelsTable.COLUMNNAME_LEVELS_ID,
                        LevelsTable.COLUMNNAME_LEVELS_NAME,
                        LevelsTable.COLUMNNAME_LEVELS_FHEMNAME,
                        LevelsTable.COLUMNNAME_LEVELS_ICON,
                        LevelsTable.COLUMNNAME_LEVELS_PARENT_ID,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT},
                LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                new String[]{String.valueOf(parentId)},
                null,
                null,
                null);
        List<LevelsTable.LevelsEntry> dbRooms = LevelsTable.LevelsEntry.fromCursor(c);
        return dbRooms;
    }

    public void importToDatabase(FHEMConfigResponse response) {
        importIntoDataBase(response.getResults());
    }

    public void importIntoDataBase(List<FHEMConfigResponse.FHEMDevice> deviceList) {
        // Collect all rooms from the response
        Set<String> rooms = new HashSet<>();
        SQLiteDatabase db = new FhemMonkeyDatabase(context).getWritableDatabase();
        //db.execSQL("DELETE FROM " + LevelsTable.TABLE_NAME);
        for (FHEMConfigResponse.FHEMDevice device : deviceList) {
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
                    new String[]{LevelsTable.COLUMNNAME_LEVELS_ID,
                            LevelsTable.COLUMNNAME_LEVELS_NAME,
                            LevelsTable.COLUMNNAME_LEVELS_FHEMNAME,
                            LevelsTable.COLUMNNAME_LEVELS_ICON,
                            LevelsTable.COLUMNNAME_LEVELS_PARENT_ID,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT},
                    LevelsTable.COLUMNNAME_LEVELS_NAME + " = ?",
                    new String[]{device.getAttributes().get("room")},
                    null,
                    null,
                    null);
            LevelsTable.LevelsEntry level = LevelsTable.LevelsEntry.fromCursor(c).get(0);

            int roomId = level.getId();
            String strDeviceType = device.getInternals().get("TYPE");

            // Then we need to check if there is an entry with the room id and the type in our database
            c = db.query(LevelsTable.TABLE_NAME,
                    new String[]{LevelsTable.COLUMNNAME_LEVELS_ID,
                            LevelsTable.COLUMNNAME_LEVELS_NAME,
                            LevelsTable.COLUMNNAME_LEVELS_FHEMNAME,
                            LevelsTable.COLUMNNAME_LEVELS_ICON,
                            LevelsTable.COLUMNNAME_LEVELS_PARENT_ID,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT},
                    LevelsTable.COLUMNNAME_LEVELS_NAME + " = ? AND " + LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                    new String[]{strDeviceType, Integer.toString(roomId)},
                    null,
                    null,
                    null);

            if (c.getCount() > 0) {
                // type already exists, we need to get the id
                LevelsTable.LevelsEntry deviceType = LevelsTable.LevelsEntry.fromCursor(c).get(0);
                idOfDeviceType = deviceType.getId();
            } else {
                // type does not exist yet, need to create it
                level = new LevelsTable.LevelsEntry();
                level.setName(strDeviceType);
                level.setParentId(roomId);
                idOfDeviceType = (int) db.insert(LevelsTable.TABLE_NAME, null, level.getContentValues());
            }


            // Now we can store the device itself in our database
            level = new LevelsTable.LevelsEntry();
            level.setName(device.getName());
            level.setParentId(idOfDeviceType);
            db.insert(LevelsTable.TABLE_NAME, null, level.getContentValues());
        }

        // To check if stored everything correctly, print it to the console
        Cursor c = db.query(LevelsTable.TABLE_NAME,
                new String[]{LevelsTable.COLUMNNAME_LEVELS_ID,
                        LevelsTable.COLUMNNAME_LEVELS_NAME,
                        LevelsTable.COLUMNNAME_LEVELS_FHEMNAME,
                        LevelsTable.COLUMNNAME_LEVELS_ICON,
                        LevelsTable.COLUMNNAME_LEVELS_PARENT_ID,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER,
                        LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                        LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT},
                LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                new String[]{"-1"},
                null,
                null,
                null);
        List<LevelsTable.LevelsEntry> dbRooms = LevelsTable.LevelsEntry.fromCursor(c);
        for (LevelsTable.LevelsEntry room : dbRooms) {
            Log.d("Database", "id:" + room.getId() + " " + room.getName() + " parentid: " + room.getParentId());

            c = db.query(LevelsTable.TABLE_NAME,
                    new String[]{LevelsTable.COLUMNNAME_LEVELS_ID,
                            LevelsTable.COLUMNNAME_LEVELS_NAME,
                            LevelsTable.COLUMNNAME_LEVELS_FHEMNAME,
                            LevelsTable.COLUMNNAME_LEVELS_ICON,
                            LevelsTable.COLUMNNAME_LEVELS_PARENT_ID,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER,
                            LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                            LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT},
                    LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                    new String[]{Integer.toString(room.getId())},
                    null,
                    null,
                    null);
            List<LevelsTable.LevelsEntry> deviceTypes = LevelsTable.LevelsEntry.fromCursor(c);
            for (LevelsTable.LevelsEntry deviceType : deviceTypes) {
                Log.d("Database", "id:" + deviceType.getId() + " " + "   " + deviceType.getName() + " parentid: " + deviceType.getParentId());

                c = db.query(LevelsTable.TABLE_NAME,
                        new String[]{LevelsTable.COLUMNNAME_LEVELS_ID,
                                LevelsTable.COLUMNNAME_LEVELS_NAME,
                                LevelsTable.COLUMNNAME_LEVELS_FHEMNAME,
                                LevelsTable.COLUMNNAME_LEVELS_ICON,
                                LevelsTable.COLUMNNAME_LEVELS_PARENT_ID,
                                LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT,
                                LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER,
                                LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                                LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                                LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                                LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT},
                        LevelsTable.COLUMNNAME_LEVELS_PARENT_ID + " = ?",
                        new String[]{Integer.toString(deviceType.getId())},
                        null,
                        null,
                        null);
                List<LevelsTable.LevelsEntry> devices = LevelsTable.LevelsEntry.fromCursor(c);

                for (LevelsTable.LevelsEntry device : devices) {
                    Log.d("Database", "id:" + device.getId() + " " + "      " + device.getName() + " parentid: " + device.getParentId());
                }
            }
        }
    }
}
