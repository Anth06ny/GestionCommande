package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import greendao.DaoMaster;

/**
 * Created by Axel legué on 25/11/2016.
 */

public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String NOM_BDD = "appli.db";
    private static final int VERSION_BDD = 1;

    public MaBaseSQLite(Context context) {
        super(context, NOM_BDD, null, VERSION_BDD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de toutes les tables
        DaoMaster.createAllTables(db, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
