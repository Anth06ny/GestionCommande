package com.example.anthony.gestionstock.model.webservice;

import com.example.anthony.gestionstock.controller.MyApplication;
import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.model.bdd.CommandeBddManager;
import com.example.anthony.gestionstock.model.bdd.ConsommeBddManager;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;

import java.util.Date;
import java.util.List;

import greendao.Categorie;
import greendao.Commande;
import greendao.Consomme;
import greendao.Produit;

/**
 * Created by Anthony on 20/12/2016.
 */
public class WSUtils {

    private static final String SAVE_URL = "http://192.168.10.204//WebServiceAndroid/postJson.php?";
    private static final String TYPE_WORD = "type=";
    private static final String DATE_WORD = "date=";

    private enum TYPE {
        CATEGORIE, CONSOMME, PRODUIT, COMMANDE
    }

    /**
     * Charge le base de donnée sur le serveur
     */
    public static void loadData() {

    }

    /**
     * Sauvegarde la base de donnée sur le serveur
     */
    public static void saveData() throws Exception {
        //Pour qu'il est tous la même date
        long time = new Date().getTime();
        saveCategorie(time);
        //        saveCommande();
        //        saveProduit();
        //        saveConsomme();

    }

    /**
     * Sauvegarde l'état des catégories    sur le serveur
     */
    public static void saveCategorie(long time) throws Exception {
        List<Categorie> list = CategorieBddManager.getCategories();
        String url = SAVE_URL + TYPE_WORD + TYPE.CATEGORIE + "?" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveProduit() {
        List<Produit> list = ProduitBddManager.getProduit();
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveConsomme() {
        List<Consomme> list = ConsommeBddManager.getConsomme();
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveCommande() {
        List<Commande> list = CommandeBddManager.getCommande();
    }
}
