package prefpin.sample;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import prefpin.BindPref;
import prefpin.OnPrefChange;
import prefpin.OnPrefClick;
import prefpin.PrefPin;

public class SettingsFragment extends PreferenceFragment {

  @BindPref(R.string.pref_edit_key) EditTextPreference editPreference;
  @BindPref(R.string.pref_checkbox_key) CheckBoxPreference checkBoxPreference;
  @BindPref(R.string.pref_switch_key) SwitchPreference switchPreference;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.settings);

    PrefPin.bind(this);
  }

  @Override public void onResume() {
    super.onResume();
    editPreference.setSummary(
        getPreferenceManager().getSharedPreferences().getString(editPreference.getKey(), ""));
    checkBoxPreference.setChecked(true);
    switchPreference.setChecked(true);
  }

  @OnPrefClick(R.string.pref_about_key) void showGeneral(Preference preference) {
    Intent intent = new Intent(getActivity(), AboutActivity.class);
    startActivity(intent);
  }

  @OnPrefChange({ R.string.pref_edit_key, R.string.pref_checkbox_key, R.string.pref_switch_key })
  void onNameUpdate(Preference preference, Object newValue) {
    preference.setSummary(newValue.toString());
  }
}
