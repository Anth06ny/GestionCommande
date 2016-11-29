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

    public static List<Produit> getFromSQLProduit() {
        return MyApplication.getDaoSession().getProduitDao().queryBuilder().orderDesc(ProduitDao.Properties.Id).build().list();
    }

    public static void saveToSQLPproduit(Produit produit) {
        MyApplication.getDaoSession().getProduitDao().insert(produit);
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
