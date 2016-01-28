package de.vegie1996.fhem_monkey.database.tables;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.vegie1996.fhem_monkey.networking.FHEMConfigResponse;

/**
 * Created by Osiris on 24.01.2016.
 */
public class LevelsTable implements FhemMonkeyTable {
    public static final String TABLE_NAME = "levels";
    public static final String COLUMNNAME_LEVELS_ID = "id";
    public static final String COLUMNNAME_LEVELS_NAME = "name";
    public static final String COLUMNNAME_LEVELS_FHEMNAME = "fhem_name";
    public static final String COLUMNNAME_LEVELS_ICON = "icon";
    public static final String COLUMNNAME_LEVELS_PARENT_ID = "parent_id";
    public static final String COLUMNNAME_LEVELS_READING_TOP_LEFT = "reading_top_left";
    public static final String COLUMNNAME_LEVELS_READING_TOP_CENTER = "reading_top_center";
    public static final String COLUMNNAME_LEVELS_READING_TOP_RIGHT = "reading_top_right";
    public static final String COLUMNNAME_LEVELS_READING_BOTTOM_LEFT = "reading_bottom_left";
    public static final String COLUMNNAME_LEVELS_READING_BOTTOM_CENTER = "reading_bottom_center";
    public static final String COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT = "reading_bottom_right";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public LevelsTable() {
    }

    public static String[] getColumns() {
        return new String[]{
                COLUMNNAME_LEVELS_ID,
                COLUMNNAME_LEVELS_NAME,
                COLUMNNAME_LEVELS_FHEMNAME,
                COLUMNNAME_LEVELS_ICON,
                COLUMNNAME_LEVELS_PARENT_ID,
                COLUMNNAME_LEVELS_READING_TOP_LEFT,
                COLUMNNAME_LEVELS_READING_TOP_CENTER,
                COLUMNNAME_LEVELS_READING_TOP_RIGHT,
                COLUMNNAME_LEVELS_READING_BOTTOM_LEFT,
                COLUMNNAME_LEVELS_READING_BOTTOM_CENTER,
                COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT
        };
    }

    @Override
    public String getCreateCommand() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMNNAME_LEVELS_ID + " INTEGER PRIMARY KEY," +
                COLUMNNAME_LEVELS_NAME + " TEXT, " +
                COLUMNNAME_LEVELS_FHEMNAME + " TEXT, " +
                COLUMNNAME_LEVELS_ICON + " TEXT, " +
                COLUMNNAME_LEVELS_PARENT_ID + " INTEGER, " +
                COLUMNNAME_LEVELS_READING_TOP_LEFT + " TEXT, " +
                COLUMNNAME_LEVELS_READING_TOP_CENTER + " TEXT, " +
                COLUMNNAME_LEVELS_READING_TOP_RIGHT + " TEXT, " +
                COLUMNNAME_LEVELS_READING_BOTTOM_LEFT + " TEXT, " +
                COLUMNNAME_LEVELS_READING_BOTTOM_CENTER + " TEXT, " +
                COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT + " TEXT);";
    }

    @Override
    public String getDropCommand() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /* Inner class that defines the table contents */
    public static class LevelsEntry implements Serializable {
        private int id = -1;
        private String name;
        private String fhemName;
        private String icon;
        private int parentId = -1;
        private String readingTopLeft;
        private String readingTopCenter;
        private String readingTopRight;
        private String readingBottomLeft;
        private String readingBottomCenter;
        private String readingBottomRight;

        public static List<LevelsEntry> fromCursor(Cursor c) {
            List<LevelsEntry> levels = new ArrayList<>();
            if (c.moveToFirst()){
                while(!c.isAfterLast()){
                    LevelsEntry entry = new LevelsEntry();
                    entry.id = c.getInt(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_ID));
                    entry.name = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_NAME));
                    entry.fhemName = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_FHEMNAME));
                    entry.icon = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_ICON));
                    entry.parentId = c.getInt(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_PARENT_ID));
                    entry.readingTopLeft = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT));
                    entry.readingTopCenter = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER));
                    entry.readingTopRight = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT));
                    entry.readingBottomLeft = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT));
                    entry.readingBottomCenter = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER));
                    entry.readingBottomRight = c.getString(c.getColumnIndex(LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT));
                    levels.add(entry);
                    c.moveToNext();
                }
            }
            c.close();
            return levels;
        }

        public static LevelsEntry fromDevice(FHEMConfigResponse.FHEMDevice device, int parentId) {
            LevelsEntry entry = new LevelsEntry();
            entry.setName(device.getName());
            entry.setFhemName(device.getName());
            entry.setParentId(parentId);
            if (device.getInternals().get("TYPE").equals("at")) {
                return createAt(entry);
            } else if (device.getInternals().get("TYPE").equals("CUL")) {
                return createCUL(entry);
            } else if (device.getInternals().get("TYPE").equals("CUL_HM")) {
                return createCUL_HM(entry);
            } else if (device.getInternals().get("TYPE").equals("dummy")) {
                return createDummy(entry);
            } else if (device.getInternals().get("TYPE").equals("PRESENCE")) {
                return createPresence(entry);
            } else if (device.getInternals().get("TYPE").equals("yowsup")) {
                return createYowsup(entry);
            }

            return entry;
        }

        private static LevelsEntry createAt(LevelsEntry entry) {
            entry.setReadingBottomCenter("state");
            return entry;
        }

        private static LevelsEntry createCUL(LevelsEntry entry) {
            entry.setReadingBottomCenter("state");
            return entry;
        }

        private static LevelsEntry createCUL_HM(LevelsEntry entry) {
            entry.setReadingBottomLeft("measured-temp");
            entry.setReadingBottomRight("desired-temp");
            entry.setReadingTopCenter("actuator");
            return entry;
        }

        private static LevelsEntry createDummy(LevelsEntry entry) {
            entry.setReadingBottomCenter("state");
            return entry;
        }

        private static LevelsEntry createPresence(LevelsEntry entry) {
            entry.setReadingBottomCenter("state");
            return entry;
        }

        private static LevelsEntry createYowsup(LevelsEntry entry) {
            entry.setReadingBottomCenter("state");
            return entry;
        }

        public ContentValues getContentValues() {
            ContentValues cv = new ContentValues();
            if (id > -1) {
                cv.put(LevelsTable.COLUMNNAME_LEVELS_ID, id);
            }
            cv.put(LevelsTable.COLUMNNAME_LEVELS_NAME, name);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_FHEMNAME, fhemName);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_ICON, icon);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_PARENT_ID, parentId);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_READING_TOP_LEFT, readingTopLeft);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_READING_TOP_CENTER, readingTopCenter);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_READING_TOP_RIGHT, readingTopRight);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_LEFT, readingBottomLeft);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_CENTER, readingBottomCenter);
            cv.put(LevelsTable.COLUMNNAME_LEVELS_READING_BOTTOM_RIGHT, readingBottomRight);
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

        public String getFhemName() {
            return fhemName;
        }

        public void setFhemName(String fhemName) {
            this.fhemName = fhemName;
        }

        public String getReadingTopLeft() {
            return readingTopLeft;
        }

        public void setReadingTopLeft(String reading1) {
            this.readingTopLeft = reading1;
        }

        public String getReadingTopCenter() {
            return readingTopCenter;
        }

        public void setReadingTopCenter(String readingTopCenter) {
            this.readingTopCenter = readingTopCenter;
        }

        public String getReadingTopRight() {
            return readingTopRight;
        }

        public void setReadingTopRight(String readingTopRight) {
            this.readingTopRight = readingTopRight;
        }

        public String getReadingBottomLeft() {
            return readingBottomLeft;
        }

        public void setReadingBottomLeft(String readingBottomLeft) {
            this.readingBottomLeft = readingBottomLeft;
        }

        public String getReadingBottomCenter() {
            return readingBottomCenter;
        }

        public void setReadingBottomCenter(String readingBottomCenter) {
            this.readingBottomCenter = readingBottomCenter;
        }

        public String getReadingBottomRight() {
            return readingBottomRight;
        }

        public void setReadingBottomRight(String readingBottomRight) {
            this.readingBottomRight = readingBottomRight;
        }
    }
}
