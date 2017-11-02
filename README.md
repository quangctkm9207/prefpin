# PrefPin :round_pushpin:
**Reduce boilerplate codes in PreferenceFragment by using field and method binding.**
* Remove `findFragment` by using `@BindPref`.
* Remove `setOnPreferenceClickListener` by using `@OnPrefClick`.
* Remove `setOnPreferenceChangeListener` by using `@OnPrefChange`.

## Installation
```gradle
dependencies {
    // ... others
    compile 'com.emo-pass:prefpin:0.1.0'
}
```

## Usage
Preference keys should be stored in `strings.xml`.
```xml
  <string name="pref_about_key" translatable="false">pref_about_key</string>
```

PreferenceFragment subclass will look like.
```java
public class SettingFragment extends PreferenceFragment{
  @BindPref(R.string.pref_about_key) public Preference aboutPreference;
  @BindPref(R.string.pref_name_key) public EditTextPreference namePreference;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.settings);
    PrefPin.bind(this);
    //...
  }
  
  @OnPrefClick(R.string.pref_about_key)
  public void onClick(Preference preference) {
    //...
  }
  
  @OnPrefChange(R.string.pref_name_key)
  public void onPrefChange(Preference preference, Object newObject) {
    //...
  } 
}
```

## License
This project is under MIT license. Copyright (c) 2017 Quang Nguyen.
