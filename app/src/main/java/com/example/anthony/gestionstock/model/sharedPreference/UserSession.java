package com.example.anthony.gestionstock.model.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Axel legué on 13/12/2016.
 */

public class UserSession {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    int PRIVATE_MODE = 0;

    public static final String PREFER_NAME = "Admin";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "Name";
    public static final String KEY_PASSWORD = "MotDePasse";

    public static final String USER_LOGIN = "Ad";
    public static final String USER_PASSWORD = "1234";

    public UserSession(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = preferences.edit();
        editor.putString(KEY_NAME, USER_LOGIN);
        editor.putString(KEY_PASSWORD, USER_PASSWORD);
        editor.commit();
    }

    //Create login session
    public void createUserLoginSession(String uPsw) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing password in preferences
        editor.putString(KEY_PASSWORD, uPsw);
        // commit changes
        editor.commit();
    }
}
