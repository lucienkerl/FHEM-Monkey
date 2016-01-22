package de.vegie1996.fhem_monkey.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.securepreferences.SecurePreferences;

/**
 * Created by lucienkerl on 21/01/16.
 */
public class Preferences {

    public static final String KEY_HOSTNAME = "key_hostname";
    public static final String KEY_PORT = "key_port";
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_HTTP_S = "key_http_s";
    public static final String KEY_PREFIX = "key_prefix";

    public void putString(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Only time you should use putSecureString method is when you store the password.
     **/
    public void putSecureString(Context context, String key, String value) {
        SharedPreferences prefs = new SecurePreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSecureString(Context context, String key, String defaultValue) {
        SharedPreferences prefs = new SecurePreferences(context);
        return prefs.getString(key, defaultValue);
    }

    public String getString(Context context, String key, String defaultValue) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, defaultValue);
    }

}
