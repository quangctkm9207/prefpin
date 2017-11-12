package prefpin.sample;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import prefpin.BindPref;
import prefpin.PrefPin;

public class AboutFragment extends PreferenceFragment {

  @BindPref(R.string.pref_author_key) Preference authorPreference;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.about);
    PrefPin.bind(this);
    authorPreference.setSummary("@quangctkm9207");
  }
}
