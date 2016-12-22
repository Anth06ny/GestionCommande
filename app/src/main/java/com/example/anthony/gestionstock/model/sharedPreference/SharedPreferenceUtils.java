package com.example.anthony.gestionstock.model.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.anthony.gestionstock.controller.MyApplication;

/**
 * Created by Axel legué on 13/12/2016.
 */

public class SharedPreferenceUtils {

    public static final String MyPREFERENCES = "MyPrefs";

    private static SharedPreferences getSharedPreferences() {

        return MyApplication.getInstance().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    /* ---------------------------------
    //  Gere le mot de passe
    // -------------------------------- */
    private static final String PASSWORD_KEY = "PASSWORD_KEY";

    public static String getPassword() {
        return getSharedPreferences().getString(PASSWORD_KEY, null);
    }

    public static void savePassword(String password) {

        SharedPreferences.Editor editor = getEditor();
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }

    /* ---------------------------------
   //  Gere date derniere sauvegarde
   // -------------------------------- */
    private static final String SAVE_DATE_KEY = "SAVE_DATE_KEY";

    public static long getSaveDate() {
        return getSharedPreferences().getLong(SAVE_DATE_KEY, 0);
    }

    public static void setSaveDate(long saveDate) {

        SharedPreferences.Editor editor = getEditor();
        editor.putLong(SAVE_DATE_KEY, saveDate);
        editor.apply();
    }
}
