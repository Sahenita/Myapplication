package com.rit.tcs.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by A688629 on 24-08-2018.
 */

public class TCSPreference {

    SharedPreferences sharedPreferences;
    Context context = null;
    SharedPreferences.Editor editor = null;

    public TCSPreference(Context context) {
        if(context!=null)
        sharedPreferences = context.getSharedPreferences("TCSPref", 0);
    }


    public void startToSavePreference() {
        editor = sharedPreferences.edit();

    }

    public void stopToSavePreference() {
        editor.commit();

    }

    public void clearPreference() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void savePreference(String type, String key, String value) {
        switch (type) {
            case "Integer":
                editor.putInt(key, Integer.parseInt(value));
                break;
            case "Float":
                editor.putFloat(key, Float.parseFloat(value));
                break;
            case "Long":
                editor.putLong(key, Long.parseLong(value));
                break;
            case "String":
            default:
                editor.putString(key, value);
                break;
        }
    }

    public String getPreference(String type, String key) {
        switch (type) {
            case "Integer":
                return String.valueOf(sharedPreferences.getInt(key, 0));
            case "Float":
                return String.valueOf(sharedPreferences.getFloat(key, 0));
            case "Long":
                return String.valueOf(sharedPreferences.getLong(key, 0));
            case "String":
            default:
                return sharedPreferences.getString(key, "");
        }
    }
}
