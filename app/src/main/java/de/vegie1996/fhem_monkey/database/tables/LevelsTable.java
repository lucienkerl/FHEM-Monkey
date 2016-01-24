package de.vegie1996.fhem_monkey.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Osiris on 24.01.2016.
 */
public class LevelsTable implements FhemMonkeyTable {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public LevelsTable() {}

    public static final String TABLE_NAME = "levels";
    public static final String COLUMNNAME_LEVELS_ID = "id";
    public static final String COLUMNNAME_LEVELS_NAME = "name";
    public static final String COLUMNNAME_LEVELS_ICON = "icon";
    public static final String COLUMNNAME_LEVELS_PARENT_ID = "parent_id";

    @Override
    public String getCreateCommand() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMNNAME_LEVELS_ID + " INTEGER PRIMARY KEY," +
                COLUMNNAME_LEVELS_NAME + "TEXT, " +
                COLUMNNAME_LEVELS_ICON + "TEXT, " +
                COLUMNNAME_LEVELS_PARENT_ID + " INTEGER);";
    }

    @Override
    public String getDropCommand() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /* Inner class that defines the table contents */
    public static class LevelsEntry {
        private int id;
        private String name;
        private String icon;
        private int parentId;

        public static LevelsEntry fromCursor(Cursor c) {
            LevelsEntry entry = new LevelsEntry();
            entry.id = c.getInt(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_ID));
            entry.name = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_NAME));
            entry.icon = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_ICON));
            entry.parentId = c.getInt(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_PARENT_ID));
            return entry;
        }

        public ContentValues getContentValues() {
            ContentValues cv = new ContentValues();
            cv.put(LevelsTable.COLUMNNAME_LEVELS_ID, id);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_NAME, name);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_ICON, icon);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_PARENT_ID, parentId);
            return cv;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }
}
