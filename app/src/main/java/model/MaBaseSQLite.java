package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import greendao.Categorie;
import greendao.DaoMaster;
import greendao.Produit;
import vue.material_color_picker.ColorChooserDialog;

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
        DaoMaster.dropAllTables(db, true);
        onCreate(db);
    }

    /**
     * Remplir la base avec un jeu de donnée
     */
    public static void fillBase(Context context) {

        //CATEGORIE 1
        Categorie categorie = new Categorie();
        categorie.setCouleur("" + ColorChooserDialog.DeepOrange);
        categorie.setNom("Alcool");
        CategorieBddManager.insertOrUpdate(categorie);

        Produit produit = new Produit(null, "Téquila", 12.0f, 10, 0, true, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Vodka", 12.0f, 10, 0, true, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Pastis", 10.0f, 8, 0, false, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Vin", 13.0f, 16, 0, false, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        //CATEGORIE 2
        categorie = new Categorie();
        categorie.setCouleur("" + ColorChooserDialog.Blue);
        categorie.setNom("Soft");
        CategorieBddManager.insertOrUpdate(categorie);

        produit = new Produit(null, "Orange", 3.0f, 16, 0, true, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Coca", 4.0f, 16, 0, true, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Ice tea", 2.0f, 16, 0, false, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Jus de peche", 2.0f, 8, 0, false, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        //CATEGORIE 3
        categorie = new Categorie();
        categorie.setCouleur("" + ColorChooserDialog.Teal);
        categorie.setNom("Cafe");
        CategorieBddManager.insertOrUpdate(categorie);

        produit = new Produit(null, "Cafe", 1f, 16, 0, true, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Cafe court", 1.5f, 16, 0, true, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Cafe long", 2.0f, 8, 0, false, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);

        produit = new Produit(null, "Cafe américain", 2.5f, 8, 0, false, categorie.getId());
        ProduitBddManager.insertOrUpdate(produit);
    }
}
