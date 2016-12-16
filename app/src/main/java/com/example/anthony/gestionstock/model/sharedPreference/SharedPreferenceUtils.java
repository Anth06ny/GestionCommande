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

    public static boolean checkPassword(String password) {

        String savePassword = getSharedPreferences().getString(PASSWORD_KEY, "");
        return savePassword.equalsIgnoreCase(password);
    }

    public static void savePAssword(String password) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }
}
