package com.example.anthony.gestionstock.controller;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.model.bdd.MaBaseSQLite;
import com.google.gson.Gson;

import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by Allan on 29/11/2016.
 */

public class MyApplication extends Application {

    private static String BASE_NAME = "appli.db";
    public final static boolean DEBUG = true;

    private static DaoSession daoSession;
    private static MyApplication instance;
    private static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupDatabase();

        gson = new Gson();

        //TODO retirer l'ajout de données test
        if (MyApplication.DEBUG && CategorieBddManager.getCategories().size() == 0) {
            MaBaseSQLite.fillBase(this);
        }
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, BASE_NAME,
                null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Gson getGson() {
        return gson;
    }
}
