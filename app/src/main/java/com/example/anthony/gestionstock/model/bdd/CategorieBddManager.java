package com.example.anthony.gestionstock.model.bdd;

import com.example.anthony.gestionstock.controller.MyApplication;

import java.util.List;

import greendao.Categorie;
import greendao.CategorieDao;
import greendao.Produit;

/**
 * Created by Axel legué on 25/11/2016.
 */

public class CategorieBddManager {

    // ------------------------------------ '' SQL QUERY Functions ''' -------------------------------- //

    public static List<Categorie> getCategories() {
        MyApplication.getDaoSession().clear();
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

    public static void deleteCategorie(Categorie categorie) {

        //On fait une boucle pour supprimer de la liste et de la bdd tous les produits de cette categorie
        for (Produit produit : categorie.getProduitList()) {
            ProduitBddManager.deleteProduit(produit);
        }

        MyApplication.getDaoSession().getCategorieDao().delete(categorie);
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
