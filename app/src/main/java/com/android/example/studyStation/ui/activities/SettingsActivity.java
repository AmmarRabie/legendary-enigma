package com.android.example.studyStation.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.android.example.studyStation.R;
import com.android.example.studyStation.server.ServerUtility;

import static android.R.attr.value;

public class SettingsActivity extends AppCompatActivity {

    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setResult(RESULT_OK);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_main);

            Preference server_ip = findPreference(getString(R.string.settings_server_ip_key));
            bindPreferenceSummaryToValue(server_ip);

            Preference youtupe_option = findPreference(getString(R.string.settings_youtupe_option_fullscreen_key));
            bindPreferenceSummaryToValue(youtupe_option);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof CheckBoxPreference) return true;
            preference.setSummary(stringValue);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            if (preference instanceof CheckBoxPreference)
            {
                boolean preferenceString = preferences.getBoolean(preference.getKey(), true);
                onPreferenceChange(preference,preferenceString);
                return;
            }
            String preferenceString = preferences.getString(preference.getKey(), ServerUtility.getIP());
            onPreferenceChange(preference, preferenceString);
        }
    }


}