package de.vegie1996.fhem_monkey.database.tables;

/**
 * Created by Osiris on 24.01.2016.
 */
public interface FhemMonkeyTable {
    String getCreateCommand();
    String getDropCommand();
}
