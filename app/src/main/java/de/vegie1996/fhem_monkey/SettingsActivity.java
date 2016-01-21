package de.vegie1996.fhem_monkey;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import de.vegie1996.fhem_monkey.helper.Preferences;

@EActivity(R.layout.activity_settings)
@OptionsMenu(R.menu.settings_activity)
public class SettingsActivity extends FHEMMonkeyActivity {

    @ViewById(R.id.input_hostname)
    EditText inputHostname;

    @ViewById(R.id.input_port)
    EditText inputPort;

    @ViewById(R.id.input_username)
    EditText inputUsername;

    @ViewById(R.id.input_password)
    EditText inputPassword;

    @ViewById(R.id.input_prefix)
    EditText inputPrefix;

    @ViewById(R.id.use_https)
    Switch useHttps;

    MaterialDialog dialog;

    @AfterViews
    public void initViews() {
        showLoadingDialog();
        getPreferencesAndSetValues();
    }

    @OptionsItem(R.id.menu_save)
    public void saveClicked() {
        showLoadingDialog();
        saveInBackground();
    }

    @Background
    public void getPreferencesAndSetValues() {
        Preferences preferences = new Preferences();
        String hostname = preferences.getString(getApplicationContext(), Preferences.KEY_HOSTNAME, "");
        String port = preferences.getString(getApplicationContext(), Preferences.KEY_PORT, "8083");
        String username = preferences.getString(getApplicationContext(), Preferences.KEY_USERNAME, "");
        String password = preferences.getString(getApplicationContext(), Preferences.KEY_PASSWORD, "");
        String https = preferences.getString(getApplicationContext(), Preferences.KEY_HTTP_S, "http");
        String prefix = preferences.getString(getApplicationContext(), Preferences.KEY_PREFIX, "/fhem");

        setInputs(hostname, port, username, password, https, prefix);
    }

    @UiThread
    public void setInputs(String hostname, String port, String username, String password, String https, String prefix) {
        inputHostname.setText(hostname);
        inputPort.setText(port);
        inputUsername.setText(username);
        inputPassword.setText(password);
        inputPrefix.setText(prefix);
        if (https.equals("https")) useHttps.setChecked(true);
        else useHttps.setChecked(false);

        dismissDialog();
    }

    @Background
    public void saveInBackground() {
        Preferences preferences = new Preferences();
        preferences.putString(getApplicationContext(), Preferences.KEY_HOSTNAME, inputHostname.getText().toString());
        preferences.putString(getApplicationContext(), Preferences.KEY_PORT, inputPort.getText().toString());
        preferences.putString(getApplicationContext(), Preferences.KEY_USERNAME, inputUsername.getText().toString());
        preferences.putString(getApplicationContext(), Preferences.KEY_PASSWORD, inputPassword.getText().toString());
        preferences.putString(getApplicationContext(), Preferences.KEY_PREFIX, inputPrefix.getText().toString());
        if (useHttps.isChecked()) {
            preferences.putString(getApplicationContext(), Preferences.KEY_HTTP_S, "https");
        } else {
            preferences.putString(getApplicationContext(), Preferences.KEY_HTTP_S, "http");
        }
        showSuccessAndDismissActivity();
    }

    @UiThread
    public void showSuccessAndDismissActivity() {
        dismissDialog();
        Toast.makeText(SettingsActivity.this, getResources().getString(R.string.saved_settings), Toast.LENGTH_SHORT).show();
        finish();
    }

    @UiThread
    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    public void showLoadingDialog() {
        dialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(getResources().getString(R.string.loading))
                .cancelable(false)
                .build();
        dialog.show();
    }
}