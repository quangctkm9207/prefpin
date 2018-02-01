package prefpin;

import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
  private static final String BINDING_CLASS_NAME_POSTFIX = "_PrefBinding";
  private PrefPin() {
    throw new AssertionError("No instance.");
  }

  @UiThread public static void bind(@NonNull PreferenceFragment target) {
    createBinding(target);
  }

  private static void createBinding(PreferenceFragment target) {
    Constructor constructor = findBindingConstructor(target);
    if (constructor != null) {
      try {
        constructor.newInstance(target);
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  private static Constructor findBindingConstructor(PreferenceFragment target) {
    String targetClassName = target.getClass().getName();
    String bindingClassName = targetClassName + BINDING_CLASS_NAME_POSTFIX;
    Constructor constructor = null;
    try {
      Class bindingClass = target.getClass().getClassLoader().loadClass(bindingClassName);
      constructor = bindingClass.getConstructor(target.getClass());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    return constructor;
  }
}
