package de.vegie1996.fhem_monkey.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import de.vegie1996.fhem_monkey.database.tables.ActionsTable;
import de.vegie1996.fhem_monkey.database.tables.CommandsTable;
import de.vegie1996.fhem_monkey.database.tables.FhemMonkeyTable;
import de.vegie1996.fhem_monkey.database.tables.LevelsTable;

/**
 * Created by Osiris on 24.01.2016.
 */
public class FhemMonkeyDatabase extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "FHEMMonkey.db";

    public FhemMonkeyTable[] tables = {
            new LevelsTable(),
            new ActionsTable(),
            new CommandsTable()
    };

    public FhemMonkeyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (FhemMonkeyTable table : tables) {
            String createCommand = table.getCreateCommand();
            Log.d("Database Creation", createCommand);
            db.execSQL(createCommand);
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
