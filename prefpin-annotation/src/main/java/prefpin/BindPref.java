package prefpin;

import android.support.annotation.StringRes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a field to the preference for the specified key.
 * type.
 * <pre><code>
 * {@literal @}BindView(R.string.pref_name_key) Preference namePreference;
 * </code></pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindPref {
  /**
   * Preference's key which is stored as a string resource.
   */
  @StringRes int value();
}
