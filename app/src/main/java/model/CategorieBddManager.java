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

    public static List<Categorie> getFromSQLCategories() {
        return MyApplication.getDaoSession().getCategorieDao().queryBuilder().orderDesc(CategorieDao.Properties.Id).build().list();
    }

    public static void SaveToSQLCategorie(Categorie categorie) {
        MyApplication.getDaoSession().getCategorieDao().insert(categorie);
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
