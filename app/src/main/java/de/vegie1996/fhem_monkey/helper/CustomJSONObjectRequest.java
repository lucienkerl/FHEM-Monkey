package de.vegie1996.fhem_monkey.helper;

import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucienkerl on 21/01/16.
 */
public class CustomJSONObjectRequest extends JsonObjectRequest {

    Context context;

    public CustomJSONObjectRequest(Context context, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Preferences preferences = new Preferences();
        String username = preferences.getString(context, Preferences.KEY_USERNAME, "");
        String password = preferences.getString(context, Preferences.KEY_PASSWORD, "");
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", username, password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);
        return params;
    }
}
