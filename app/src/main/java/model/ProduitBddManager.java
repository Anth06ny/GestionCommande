package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Produit;
import greendao.ProduitDao;

/**
 * Created by Axel legué on 28/11/2016.
 */

public class ProduitBddManager {
    List<String> pResult = new ArrayList<>();

    public void insertProduit(Context context, Produit produit) {
        DaoMaster.DevOpenHelper helper = null;
    }

    // Récupère la liste des produits présente en BDD

    public void generateResultProduit(ProduitDao produitDao) {
        List<Produit> produitList = getFromSQLProduit(produitDao);
        int size = produitList.size();

        if (size > 0) {
            pResult.clear();
            for (int i = 0; i < size; i++) {
                Produit currentItem = produitList.get(i);
                Long idCategorie = currentItem.getCategorieID();

                pResult.add(0, currentItem.getNom() + "," + currentItem.getLot() + "," + currentItem.getPrix() + "," + currentItem.getCategorieID());
            }
        }
    }

    // --------------------------------------  DB Setup Function ------------------------------------------- //
    //Return the Configured LogDao Object
    public ProduitDao setupDB(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "appli.db", null);//Création de la base de donnée fichier db si il existe pas
        SQLiteDatabase db = helper.getWritableDatabase();// récupère  la base de donnée cré
        DaoMaster master = new DaoMaster(db);// creation de de masterDao
        DaoSession masterSession = master.newSession(); // création de la Session session
        return masterSession.getProduitDao();
    }
    // --------------------------------------- '''' END DB Setup Function '''' --------------------- --- //

    // ------------------------------------ '' SQL QUERY Fnctions ''' -------------------------------- //

    public List<Produit> getFromSQLProduit(ProduitDao produitDao) {
        List<Produit> produits = produitDao.queryBuilder().orderDesc(ProduitDao.Properties.Id).build().list();
        return produits;
    }

    public void saveToSQLPproduit(ProduitDao produitDao, Produit produit) {
        produitDao.insert(produit);
    }

    public String getIdProduit(ProduitDao produitDao, Produit produit) {
        List<Produit> produit2 = produitDao.queryBuilder().where(ProduitDao.Properties.Nom.eq(produit.getNom())).list();
        return String.valueOf(produit2.get(0).getId());
    }
    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
