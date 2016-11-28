package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import greendao.Categorie;
import greendao.CategorieDao;
import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by Axel legué on 25/11/2016.
 */

public class CategorieBddManager {
    public void insertCategorie(Context context, Categorie categorie) {
        DaoMaster.DevOpenHelper helper = null;



    }


    public CategorieDao setupDB(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "appli.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master= new DaoMaster(db);
        DaoSession masterSession = master.newSession();
        return masterSession.getCategorieDao();
    }

    public void SaveToSQL (Categorie categorie){

    }
}
