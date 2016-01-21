package de.vegie1996.fhem_monkey.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

/**
 * Created by lucienkerl on 21/01/16.
 */
public class Preferences {

    public static final String KEY_HOSTNAME = "key_hostname";
    public static final String KEY_PORT = "key_port";
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";

    public void putString(Context context, String key, String value) {
        SharedPreferences prefs = new SecurePreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(Context context, String key, String defaultValue) {
        SharedPreferences prefs = new SecurePreferences(context);
        return prefs.getString(key, defaultValue);
    }
}
