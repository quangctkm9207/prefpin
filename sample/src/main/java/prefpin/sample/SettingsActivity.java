package prefpin.sample;

import android.annotation.SuppressLint;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.os.Bundle;

@SuppressLint("ExportedPreferenceActivity")
public class SettingsActivity extends PreferenceActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new SettingsFragment())
        .commit();
  }
}
