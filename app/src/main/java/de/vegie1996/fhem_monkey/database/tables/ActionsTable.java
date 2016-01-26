package de.vegie1996.fhem_monkey.database.tables;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Osiris on 24.01.2016.
 */
public class ActionsTable implements FhemMonkeyTable {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ActionsTable() {}

    public static final int ACTIONTYPE_TOGGLE = 1;
    public static final int ACTIONTYPE_SWITCH = 2;
    public static final int ACTIONTYPE_COLORPICKER = 3;

    public static final String TABLE_NAME = "actions";
    public static final String COLUMNNAME_ACTIONS_ID = "id";
    public static final String COLUMNNAME_ACTIONS_TYPE = "type";
    public static final String COLUMNNAME_ACTIONS_LEVEL_ID = "level_id";

    public static String[] getColumns() {
        return new String[]{
                COLUMNNAME_ACTIONS_ID,
                COLUMNNAME_ACTIONS_TYPE,
                COLUMNNAME_ACTIONS_LEVEL_ID
        };
    }

    @Override
    public String getCreateCommand() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMNNAME_ACTIONS_ID + " INTEGER PRIMARY KEY," +
                COLUMNNAME_ACTIONS_TYPE + " INTEGER, " +
                COLUMNNAME_ACTIONS_LEVEL_ID + " INTEGER);";
    }

    @Override
    public String getDropCommand() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /* Inner class that defines the table contents */
    public static class ActionEntry {
        private int id = -1;
        private int type;
        private int levelId;

        public static List<ActionEntry> fromCursor(Cursor c) {
            List<ActionEntry> levels = new ArrayList<>();
            if (c.moveToFirst()){
                while(!c.isAfterLast()){
                    ActionEntry entry = new ActionEntry();
                    entry.id = c.getInt(c.getColumnIndex(ActionsTable.COLUMNNAME_ACTIONS_ID));
                    entry.type = c.getInt(c.getColumnIndex(ActionsTable.COLUMNNAME_ACTIONS_TYPE));
                    entry.levelId = c.getInt(c.getColumnIndex(ActionsTable.COLUMNNAME_ACTIONS_LEVEL_ID));
                    levels.add(entry);
                    c.moveToNext();
                }
            }
            c.close();
            return levels;
        }

        public ContentValues getContentValues() {
            ContentValues cv = new ContentValues();
            if (id > -1) {
                cv.put(ActionsTable.COLUMNNAME_ACTIONS_ID, id);
            }
            cv.put(ActionsTable.COLUMNNAME_ACTIONS_TYPE, type);
            cv.put(ActionsTable.COLUMNNAME_ACTIONS_LEVEL_ID, levelId);
            return cv;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }
    }
}
