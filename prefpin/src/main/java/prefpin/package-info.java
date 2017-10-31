/**
 * Removes boilerplate codes in Android PreferenceFragment by field and method binding for
 * preference.
 * <p>
 * <ul>
 * <li>Eliminate {@link android.preference.PreferenceFragment#findPreference findPreference} calls by using {@link prefpin.BindPref @BindRef} on fields.</li>
 * <li>Eliminate {@link android.preference.Preference#setOnPreferenceClickListener setOnPreferenceClickListener} calls by using {@link prefpin.OnPrefClick @OnPrefClick} on methods.</li>
 * <li>Eliminate {@link android.preference.Preference#setOnPreferenceChangeListener setOnPreferenceChangeListener} calls by using {@link prefpin.OnPrefChange @OnPrefChange} on methods.</li>
 * </ul>
 * <p>
 * Notice: annotated fields and methods need to be set public.
 */
package prefpin;