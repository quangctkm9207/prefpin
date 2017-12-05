package prefpin;

import android.support.annotation.StringRes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Binds a method to the OnPreferenceClick handler of a preference for the specific key.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface OnPrefClick {
  @StringRes int[] value();
}
