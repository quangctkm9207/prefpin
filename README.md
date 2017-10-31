## PrefPin
**Reduce boilerplate codes in PreferenceFragment by using field and method binding.**
* Remove `findFragment` by using `@BindPref`.
* Remove `setOnPreferenceClickListener` by using `@OnPrefClick`.
* Remove `setOnPreferenceChangeListener` by using `@OnPrefChange`.

### Installation

### Usage
```java
public class SettingFragment extends PreferenceFragment{
  @BindPref(R.string.pref_general_key) Preference generalPreference;
  @BindPref(R.string.pref_name_key) EditTextPreference namePreference;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.settings);
    PrefPin.bind(this);
    //...
  }
  
  @OnPrefClick(R.string.pref_name_key)
  public void onClick(Preference preference) {
    //...
  }
  
  @OnPrefChange(R.string.pref_name_key)
  public void onPrefChange(Preference preference, Object newObject) {
    //...
  } 
}
```

### License
This project is under MIT license.