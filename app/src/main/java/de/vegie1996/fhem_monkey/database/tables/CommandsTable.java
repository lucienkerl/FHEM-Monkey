package de.vegie1996.fhem_monkey.database.tables;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Osiris on 24.01.2016.
 */
public class CommandsTable implements FhemMonkeyTable {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public CommandsTable() {}

    public static final String TABLE_NAME = "commands";
    public static final String COLUMNNAME_COMMANDS_ID = "id";
    public static final String COLUMNNAME_COMMANDS_ACTION_ID = "action_id";
    public static final String COLUMNNAME_COMMANDS_COMMAND = "command";

    public static String[] getColumns() {
        return new String[]{
                COLUMNNAME_COMMANDS_ID,
                COLUMNNAME_COMMANDS_ACTION_ID,
                COLUMNNAME_COMMANDS_COMMAND
        };
    }

    @Override
    public String getCreateCommand() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMNNAME_COMMANDS_ID + " INTEGER PRIMARY KEY," +
                COLUMNNAME_COMMANDS_ACTION_ID + " INTEGER, " +
                COLUMNNAME_COMMANDS_COMMAND + " TEXT);";
    }

    @Override
    public String getDropCommand() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /* Inner class that defines the table contents */
    public static class CommandEntry {
        private int id = -1;
        private int actionId;
        private String command;

        public static List<CommandEntry> fromCursor(Cursor c) {
            List<CommandEntry> levels = new ArrayList<>();
            if (c.moveToFirst()){
                while(!c.isAfterLast()){
                    CommandEntry entry = new CommandEntry();
                    entry.id = c.getInt(c.getColumnIndex(CommandsTable.COLUMNNAME_COMMANDS_ID));
                    entry.actionId = c.getInt(c.getColumnIndex(CommandsTable.COLUMNNAME_COMMANDS_ACTION_ID));
                    entry.command = c.getString(c.getColumnIndex(CommandsTable.COLUMNNAME_COMMANDS_COMMAND));
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
                cv.put(CommandsTable.COLUMNNAME_COMMANDS_ID, id);
            }
            cv.put(CommandsTable.COLUMNNAME_COMMANDS_ACTION_ID, actionId);
            cv.put(CommandsTable.COLUMNNAME_COMMANDS_COMMAND, command);
            return cv;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActionId() {
            return actionId;
        }

        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }
}
