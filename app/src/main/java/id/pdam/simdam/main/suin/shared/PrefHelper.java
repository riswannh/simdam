package id.pdam.simdam.main.suin.shared;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefHelper {
    public final static String KEY_NAME = "MyPref";
    public static SharedPreferences sharedPreferences;

    /**
     * init preference
     */
    public static SharedPreferences getSP(Context context) {
        Activity activity = (Activity) context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences = activity.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * save preference
     */
    public static void savePref(Context context, String key, String value) {
        getSP(context).edit().putString(key, value).commit();
    }

    public static void savePref(Context context, String key, boolean value) {
        getSP(context).edit().putBoolean(key, value).commit();
    }

    public static void savePref(Context context, String key, int value) {
        getSP(context).edit().putInt(key, value).commit();
    }

    /**
     * get preference
     */
    public static String getPref(Context context, String key) {
        return getSP(context).getString(key, null);
    }

    public static boolean getPrefBoolean(Context context, String key) {
        return getSP(context).getBoolean(key, false);
    }

    public static int getPrefInt(Context context, String key) {
        return getSP(context).getInt(key, 0);
    }

    /**
     * remove preference
     */
    public static void removePref(Context context, String key) {
        getSP(context).edit().remove(key).commit();
    }
}
