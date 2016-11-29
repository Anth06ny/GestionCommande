package model;

import com.example.anthony.gestionstock.controller.MyApplication;

import java.util.List;

import greendao.Categorie;
import greendao.CategorieDao;

/**
 * Created by Axel legué on 25/11/2016.
 */

public class CategorieBddManager {

    // ------------------------------------ '' SQL QUERY Functions ''' -------------------------------- //

    public static List<Categorie> getCategories() {
        return MyApplication.getDaoSession().getCategorieDao().loadAll();
    }

    public static List<Categorie> getCategoriesSortName() {
        return MyApplication.getDaoSession().getCategorieDao().queryBuilder().orderAsc(CategorieDao.Properties.Nom).list();
    }

    public static long insertOrUpdate(Categorie categorie) {
        return MyApplication.getDaoSession().getCategorieDao().insertOrReplace(categorie);
    }

    public static void clearCategorie() {
        MyApplication.getDaoSession().getCategorieDao().deleteAll();
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
