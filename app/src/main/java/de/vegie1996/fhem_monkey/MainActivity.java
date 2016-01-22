package de.vegie1996.fhem_monkey;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import de.vegie1996.fhem_monkey.adapter.RecyclerViewAdapter;
import de.vegie1996.fhem_monkey.helper.FHEMMonkeyRESTClient;
import de.vegie1996.fhem_monkey.networking.FHEMConfigResponse;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main_activity)
public class MainActivity extends FHEMMonkeyActivity {

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews
    public void initViews() {
        downloadFHEMConfigAndSetAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerView != null)
            downloadFHEMConfigAndSetAdapter();
    }
    //endregion

    public void downloadFHEMConfigAndSetAdapter() {
        FHEMMonkeyRESTClient.getFHEMConfig(getApplicationContext(), new FHEMMonkeyRESTClient.FHEMMonkeyRESTClientInterface() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("MainActivity", "onSuccess: " + response.toString());

                String s = "";
                FHEMConfigResponse fhemConfigResponse = new FHEMConfigResponse();
                try {
                    fhemConfigResponse.fillFromJSON(response);
                    for (FHEMConfigResponse.FHEMDevice device : fhemConfigResponse.getResults()) {
                        s = s + device.getName() + "\n";
                    }

                    GridLayoutManager llm = new GridLayoutManager(getApplicationContext(), 4);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setScrollbarFadingEnabled(true);

                    recyclerView.setVisibility(View.VISIBLE);
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(fhemConfigResponse.getResults());
                    recyclerView.setAdapter(recyclerViewAdapter);

                    //textView.setText(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("MainActivity", "onError: " + error.getLocalizedMessage());
            }
        });
    }

    @OptionsItem(R.id.menu_settings)
    public void onSettingsClicked() {
        SettingsActivity_.intent(this).start();
    }
}
