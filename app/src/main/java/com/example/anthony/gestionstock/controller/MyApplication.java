package com.example.anthony.gestionstock.controller;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import greendao.DaoMaster;
import greendao.DaoSession;
import model.MaBaseSQLite;

/**
 * Created by Allan on 29/11/2016.
 */

public class MyApplication extends Application {

    private static String BASE_NAME = "appli.db";
    public final static boolean DEBUG = true;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();

        if (MyApplication.DEBUG || daoSession.getCategorieDao().count() == 0) {
            MaBaseSQLite.fillBase(this);
        }

        //CategorieBddManager.clearCategorie();
        // ProduitBddManager.clearProduit();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, BASE_NAME,
                null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
