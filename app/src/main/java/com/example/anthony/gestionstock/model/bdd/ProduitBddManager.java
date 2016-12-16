package com.example.anthony.gestionstock.model.bdd;

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
        MyApplication.getDaoSession().clear();
        return MyApplication.getDaoSession().getProduitDao().loadAll();
    }

    public static List<Produit> getProduitFavoris() {
        MyApplication.getDaoSession().clear();
        return MyApplication.getDaoSession().getProduitDao().queryBuilder().where(ProduitDao.Properties.Favori.eq(true)).list();
    }

    public static List<Produit> getProduitConsommation() {
        MyApplication.getDaoSession().clear();
        return MyApplication.getDaoSession().getProduitDao().queryBuilder().where(ProduitDao.Properties.Consommation.notEq(0), ProduitDao.Properties.Consommation
                .isNotNull()).list();
    }

    public static Produit getProduitById(Long id) {
        MyApplication.getDaoSession().clear();
        return MyApplication.getDaoSession().getProduitDao().queryBuilder().where(ProduitDao.Properties.Id.eq(id)).unique();
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

