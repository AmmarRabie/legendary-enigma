package com.android.example.studyStation.appLogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.example.studyStation.R;
import com.android.example.studyStation.server.ServerUtility;

/**
 * Created by AmmarRabie on 21/09/2017.
 */

public class PreferenceUtility {


    public static String getServerIp(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(context.getString(R.string.settings_server_ip_key), ServerUtility.getIP());
    }

    public static void setServerIp(Context context, String ip) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putString(context.getString(R.string.settings_server_ip_key), ip).apply();
    }



    public static boolean getYoutupePlayOption(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean(context.getString(R.string.settings_youtupe_option_fullscreen_key), false);
    }

    public static void serYoutupePlayOption(Context context, boolean option) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putBoolean(context.getString(R.string.settings_youtupe_option_fullscreen_key), option).apply();
    }

}
