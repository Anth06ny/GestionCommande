package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import greendao.Categorie;
import greendao.CategorieDao;
import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by Axel legué on 25/11/2016.
 */

public class CategorieBddManager {
    List<String> mResult = new ArrayList<>();

    public void insertCategorie(Context context, Categorie categorie) {
        DaoMaster.DevOpenHelper helper = null;
    }

    // Récupère la liste des catégories présente en BDD
    public void generateResultCategories(CategorieDao categorieDao) {
        List<Categorie> categoriesList = getFromSQLCategories(categorieDao);
        int size = categoriesList.size();

        if (size > 0) {
            mResult.clear();
            for (int i = 0; i < size; i++) {
                Categorie currentItem = categoriesList.get(i);
                mResult.add(0, currentItem.getId() + "," + currentItem.getNom() + "," + currentItem.getCouleur());
                Log.v("test1", currentItem.getId() + "," + currentItem.getNom() + "," + currentItem.getCouleur());
            }
        }
    }

    // --------------------------------------  DB Setup Function ------------------------------------------- //
    //Return the Configured LogDao Object
    public CategorieDao setupDB(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "appli.db", null);//Création de la base de donnée fichier db si il existe pas
        SQLiteDatabase db = helper.getWritableDatabase(); // récupère  la base de donnée crée
        DaoMaster master = new DaoMaster(db); // creation de de masterDao
        DaoSession masterSession = master.newSession(); // création de la Session session
        return masterSession.getCategorieDao();
    }

    // --------------------------------------- '''' END DB Setup Function '''' --------------------- --- //

    // ------------------------------------ '' SQL QUERY Functions ''' -------------------------------- //

    public List<Categorie> getFromSQLCategories(CategorieDao categorieDao) {
        List<Categorie> categories = categorieDao.queryBuilder().orderDesc(CategorieDao.Properties.Id).build().list();
        return categories;
    }

    public void SaveToSQLCategorie(CategorieDao categorieDao, Categorie categorie) {
        categorieDao.insert(categorie);
    }


    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
