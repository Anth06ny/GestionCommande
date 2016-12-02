package model;

import com.example.anthony.gestionstock.controller.MyApplication;

import java.util.List;

import greendao.Produit;
import greendao.ProduitDao;

/**
 * Created by Axel legué on 28/11/2016.
 */

public class ProduitBddManager {

    // ------------------------------------ '' SQL QUERY Fnctions ''' -------------------------------- //

    public static List<Produit> getProduit() {
        return MyApplication.getDaoSession().getProduitDao().loadAll();
    }

    public static Produit getProduitFromName(String name) {
        return MyApplication.getDaoSession().getProduitDao().queryBuilder().where(ProduitDao.Properties.Nom.eq(name)).unique();
    }

    public static void insertOrUpdate(Produit produit) {
        MyApplication.getDaoSession().getProduitDao().insertOrReplace(produit);
    }

    public static void clearProduit() {
        MyApplication.getDaoSession().getProduitDao().deleteAll();
    }

    public static void deleteProduit(Produit produit) {
        MyApplication.getDaoSession().getProduitDao().delete(produit);
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}

