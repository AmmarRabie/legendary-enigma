package com.android.example.studyStation.appLogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by AmmarRabie on 21/09/2017.
 */

public class PreferenceUtility {

    public static final String KEY_IS_FIRST_TIME = "first-time-code64";

    /**
     * where the max radius that near grounds will considered to be
     */
    public static final String KEY_MAX_RADIUS_NEAR_GROUNDS = "max-radius-for-near-grounds-code45";


    public static final float DEFAULT_MAX_RADIUS = 100.0f;

    public static boolean isFirstTimeRunApp(Context context)
    {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean(KEY_IS_FIRST_TIME,true);
    }

    public static void setFirstTimeRunApp(Context context, boolean isFirstTime)
    {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putBoolean(KEY_IS_FIRST_TIME,isFirstTime).apply();
    }

    public static void setMaxRadiusNearGrounds(Context context, float radius)
    {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putFloat(KEY_MAX_RADIUS_NEAR_GROUNDS,radius).apply();
    }

    public static float getMaxRadiusNearGrounds(Context context)
    {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getFloat(KEY_MAX_RADIUS_NEAR_GROUNDS,DEFAULT_MAX_RADIUS);
    }


}
