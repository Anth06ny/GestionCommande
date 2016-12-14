package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

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

    private static final String USER_LOGIN = "Ad";
    private static final String USER_PASSWORD = "1234";

    public UserSession(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createUserLoginSession(String uName, String uPsw) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, USER_LOGIN);
        editor.putString(KEY_PASSWORD, USER_PASSWORD);
        editor.commit();
    }

    public Boolean checkLogin() {
        if (!this.isUserLoggedIn()) {

            Intent i = new Intent(context, FragmentLogin.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, DrawerActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    public boolean isUserLoggedIn() {
        return preferences.getBoolean(IS_USER_LOGIN, false);
    }
}
