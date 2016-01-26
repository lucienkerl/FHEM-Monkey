package de.vegie1996.fhem_monkey.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.vegie1996.fhem_monkey.database.tables.FhemMonkeyTable;
import de.vegie1996.fhem_monkey.database.tables.LevelsTable;

/**
 * Created by Osiris on 24.01.2016.
 */
public class FhemMonkeyDatabase extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FHEMMoneky.db";

    public FhemMonkeyTable[] tables = { new LevelsTable() };

    public FhemMonkeyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (FhemMonkeyTable table : tables) {
            db.execSQL(table.getCreateCommand());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (FhemMonkeyTable table : tables) {
            db.execSQL(table.getDropCommand());
        }
        onCreate(db);
    }

}
