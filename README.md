# PrefPin :round_pushpin:
[ ![Download](https://api.bintray.com/packages/quangnguyen/maven/com.emo-pass%3Aprefpin/images/download.svg) ](https://bintray.com/quangnguyen/maven/com.emo-pass%3Aprefpin/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PrefPin-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6419)  
**Reduce boilerplate codes in PreferenceFragment by using field and method binding.**
* Remove `findPreference` by using `@BindPref`.
* Remove `setOnPreferenceClickListener` by using `@OnPrefClick`.
* Remove `setOnPreferenceChangeListener` by using `@OnPrefChange`.

## Installation
```gradle
dependencies {
    // ... others
    compile 'com.emo-pass:prefpin:1.0.0'
    annotationProcessor 'com.emo-pass:prefpin-processor:1.0.0'
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
  @BindPref(R.string.pref_about_key) Preference aboutPreference;
  @BindPref(R.string.pref_name_key) EditTextPreference namePreference;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.settings);
    PrefPin.bind(this);
    //...
  }
  
  @OnPrefClick(R.string.pref_about_key)
  void onClick(Preference preference) {
    //...
  }
  
  @OnPrefChange(R.string.pref_name_key)
  void onPrefChange(Preference preference, Object newObject) {
    //...
  } 
}
```

## License
This project is under MIT license. Copyright (c) 2017 Quang Nguyen.
