package com.example.anthony.gestionstock.model.webservice;

import android.util.Log;

import com.example.anthony.gestionstock.controller.MyApplication;
import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.model.bdd.CommandeBddManager;
import com.example.anthony.gestionstock.model.bdd.ConsommeBddManager;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;

import java.util.List;

import greendao.Categorie;
import greendao.Commande;
import greendao.Consomme;
import greendao.Produit;

/**
 * Created by Anthony on 20/12/2016.
 */
public class WSUtils {

    /**
     * Charge le base de donnée sur le serveur
     */
    public static void loadData() {

    }

    /**
     * Sauvegarde la base de donnée sur le serveur
     */
    public static void saveData() {
        saveCommande();
        saveConsomme();
        saveProduit();
        saveCategorie();
    }

    /**
     * Sauvegarde l'état des catégories    sur le serveur
     */
    public static void saveCategorie() {
        List<Categorie> list = CategorieBddManager.getCategories();
        Log.w("JSON_CATEGORIE", MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveProduit() {
        List<Produit> list = ProduitBddManager.getProduit();
        Log.w("JSON_PRODUIT", MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveConsomme() {
        List<Consomme> list = ConsommeBddManager.getConsomme();
        Log.w("JSON_CONSOMME", MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveCommande() {
        List<Commande> list = CommandeBddManager.getCommande();
        Log.w("JSON_COMMANDE", MyApplication.getGson().toJson(list));
    }
}
