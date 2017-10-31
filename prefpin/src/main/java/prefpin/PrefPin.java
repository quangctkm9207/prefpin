package prefpin;

import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.preference.TwoStatePreference;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.util.Pair;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Field and method binding for Android {@link android.preference.Preference preference}.
 * <p>
 * <pre><code>
 *   public class SettingFragment extends PreferenceFragment{
 *     {@literal @}BindPref(R.string.pref_general_key) Preference generalPreference;
 *     {@literal @}BindPref(R.string.pref_name_key) EditTextPreference namePreference;
 *
 *     {@literal @}Override protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         addPreferencesFromResource(R.xml.settings);
 *         PrefPin.bind(this);
 *     }
 *
 *     {@literal @}OnPrefClick(R.string.pref_name_key) public void onClick(Preference preference) {
 *     }
 *
 *     {@literal @}OnPrefChange(R.string.pref_name_key) public void onPrefChange(Preference
 * preference, Object newObject) {
 *     }
 *   }
 * </code></pre>
 */
public class PrefPin {
  private PrefPin() {
    throw new AssertionError("No instance.");
  }

  @UiThread public static void bind(@NonNull PreferenceFragment target) {
    createBinding(target);
  }

  private static void createBinding(PreferenceFragment target) {
    bindField(target);
    bindMethod(target);
  }

  /**
   * Binds all fields annotated with {@link BindPref} in PreferenceFragment.
   *
   * @param target the PreferenceFragment which contains annotated fields.
   */
  private static void bindField(PreferenceFragment target) {
    Class targetClass = target.getClass();
    Field[] fields = targetClass.getFields();
    for (Field field : fields) {
      Class<?> type = field.getType();
      if (type == Preference.class
          || type == EditTextPreference.class
          || type == CheckBoxPreference.class
          || type == SwitchPreference.class
          || type == ListPreference.class
          || type == RingtonePreference.class
          || type == MultiSelectListPreference.class
          || type == PreferenceCategory.class
          || type == PreferenceScreen.class
          || type == PreferenceGroup.class
          || type == TwoStatePreference.class) {
        int prefKey = getPreferenceKey(field);
        if (prefKey != -1) {
          try {
            field.set(target, target.findPreference(target.getString(prefKey)));
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Binds all fields annotated with {@link OnPrefClick} in PreferenceFragment.
   *
   * @param target the PreferenceFragment which contains annotated methods.
   */
  private static void bindMethod(PreferenceFragment target) {
    Class targetClass = target.getClass();
    Method[] methods = targetClass.getMethods();
    for (final Method method : methods) {
      Pair<Integer, Class<Annotation>> pair = getPreferenceKey(method);
      int prefKey = pair.first;
      if (prefKey != -1) {
        Preference preference = target.findPreference(target.getString(prefKey));
        if (pair.second.equals(OnPrefClick.class)) {
          bindOnClick(target, preference, method);
        } else if (pair.second.equals(OnPrefChange.class)) {
          bindOnChange(target, preference, method);
        }
      }
    }
  }

  /**
   * Looks for {@link BindPref} annotation in a field and get preference key from its value.
   *
   * @return preference key string resource id or -1 if @BindPref is not available.
   */
  @StringRes private static int getPreferenceKey(Field field) {
    Annotation[] annotations = field.getAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation.annotationType() == BindPref.class) {
        BindPref bindPref = field.getAnnotation(BindPref.class);
        return bindPref.value();
      }
    }
    return -1;
  }

  /**
   * Looks for {@link OnPrefClick} or {@link OnPrefChange} annotation in a method to get preference
   * key from its value.
   *
   * @return preference key string resource id or -1 if @BindPref is not available.
   */
  private static Pair<Integer, Class<Annotation>> getPreferenceKey(Method method) {
    Annotation[] annotations = method.getAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation.annotationType() == OnPrefClick.class) {
        OnPrefClick prefClick = method.getAnnotation(OnPrefClick.class);
        return new Pair(prefClick.value(), OnPrefClick.class);
      } else if (annotation.annotationType() == OnPrefChange.class) {
        OnPrefChange prefChange = method.getAnnotation(OnPrefChange.class);
        return new Pair(prefChange.value(), OnPrefChange.class);
      }
    }
    return new Pair(-1, null);
  }

  private static void bindOnClick(final PreferenceFragment target, Preference preference,
      final Method method) {
    final boolean hasParameter = method.getParameterTypes().length > 0;
    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override public boolean onPreferenceClick(Preference preference) {
        try {
          if (hasParameter) {
            method.invoke(target, preference);
          } else {
            method.invoke(target);
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
        return true;
      }
    });
  }

  private static void bindOnChange(final PreferenceFragment target, Preference preference,
      final Method method) {
    final boolean hasParameter = method.getParameterTypes().length > 0;
    preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
        try {
          if (hasParameter) {
            method.invoke(target, preference, newValue);
          } else {
            method.invoke(target);
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
        return true;
      }
    });
  }
}
