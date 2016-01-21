package de.vegie1996.fhem_monkey;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import de.vegie1996.fhem_monkey.helper.FHEMMonkeyRESTClient;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main_activity)
public class MainActivity extends FHEMMonkeyActivity {

    @ViewById(R.id.test)
    TextView textView;

    @AfterViews
    public void initViews() {
        FHEMMonkeyRESTClient.getFHEMConfig(getApplicationContext(), new FHEMMonkeyRESTClient.FHEMMonkeyRESTClientInterface() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("MainActivity", "onSuccess: " + response.toString());
                textView.setText(response.toString());
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
