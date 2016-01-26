package de.vegie1996.fhem_monkey.helper;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import de.vegie1996.fhem_monkey.networking.FHEMConfigResponse;

/**
 * Created by lucienkerl on 21/01/16.
 */
public final class FHEMMonkeyRESTClient {

    private static final String JSONLIST_URL_APPEND = "?cmd=jsonlist2&XHR=1";

    public static void getFHEMConfig(Context context, final FHEMMonkeyRESTClientInterface fhemMonkeyRESTClientInterface) {
        Preferences preferences = new Preferences();
        String hostname = preferences.getString(context, Preferences.KEY_HOSTNAME, "");
        String port = preferences.getString(context, Preferences.KEY_PORT, "");
        String http = preferences.getString(context, Preferences.KEY_HTTP_S, "");
        String trustCertificates = preferences.getString(context, Preferences.KEY_TRUST_CERTIFICATES, "");
        String prefix = preferences.getString(context, Preferences.KEY_PREFIX, "");
        String url = http + "://" + hostname + ":" + port + prefix + JSONLIST_URL_APPEND;
        if (http.equals("https") && trustCertificates.equals("1")) {
            TrustAllCertificates.trustAll();
        }
        CustomJSONObjectRequest request = new CustomJSONObjectRequest(context, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                FHEMConfigResponse fhemConfigResponse = new FHEMConfigResponse();
                try {
                    fhemConfigResponse.fillFromJSON(response);
                    fhemMonkeyRESTClientInterface.onSuccess(fhemConfigResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fhemMonkeyRESTClientInterface.onError(error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public interface FHEMMonkeyRESTClientInterface {
        void onSuccess(FHEMConfigResponse response);

        void onError(VolleyError error);
    }

}


