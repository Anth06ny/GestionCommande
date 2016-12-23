package com.example.anthony.gestionstock.model.webservice;

import com.example.anthony.gestionstock.controller.DateBean;
import com.example.anthony.gestionstock.controller.MyApplication;
import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.model.bdd.CommandeBddManager;
import com.example.anthony.gestionstock.model.bdd.ConsommeBddManager;
import com.example.anthony.gestionstock.model.bdd.MaBaseSQLite;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
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
    private static final String URL = "http://www.amonteiro.fr/gestionstock/";
    private static final String SAVE_URL = "postJson.php?";
    private static final String LOAD_URL = "getJson.php?";
    private static final String LOAD_DATE_URL = "getDate.php";
    private static final String TYPE_WORD = "type=";
    private static final String DATE_WORD = "date=";

    private enum TYPE {
        CATEGORIE, CONSOMME, PRODUIT, COMMANDE
    }

    /**
     * Charge le base de donnée sur le serveur
     */
    public static void loadData() throws Exception {
        loadData(null);
    }

    public static void loadData(Date date) throws Exception {

        long dateLong = date != null ? date.getTime() : 0;

        ArrayList<Categorie> catFromWeb = loadCategorie(dateLong);
        ArrayList<Produit> prodFromWeb = loadProduit(dateLong);
        ArrayList<Commande> commandeFromWeb = loadCommande(dateLong);
        ArrayList<Consomme> consomeFromWeb = loadConsomme(dateLong);

        MaBaseSQLite.clearAllTable();

        CategorieBddManager.insertCategorieList(catFromWeb);
        ProduitBddManager.insertProduitList(prodFromWeb);
        CommandeBddManager.insertCommandeList(commandeFromWeb);
        ConsommeBddManager.insertConsommeList(consomeFromWeb);
    }

    private static ArrayList<Categorie> loadCategorie(Long date) throws Exception {
        String url;

        if (date == null) {
            url = URL + LOAD_URL + TYPE_WORD + TYPE.CATEGORIE;
        }
        else {
            url = URL + LOAD_URL + TYPE_WORD + TYPE.CATEGORIE + "&" + DATE_WORD + date;
        }
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Categorie>>() {
                }.getType());
    }

    private static ArrayList<Produit> loadProduit(Long date) throws Exception {
        String url;

        if (date == null) {

            url = URL + LOAD_URL + TYPE_WORD + TYPE.PRODUIT;
        }
        else {
            url = URL + LOAD_URL + TYPE_WORD + TYPE.PRODUIT + "&" + DATE_WORD + date;
        }
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Produit>>() {
                }.getType());
    }

    private static ArrayList<Commande> loadCommande(Long date) throws Exception {
        String url;
        if (date == null) {
            url = URL + LOAD_URL + TYPE_WORD + TYPE.COMMANDE;
        }
        else {
            url = URL + LOAD_URL + TYPE_WORD + TYPE.COMMANDE + "&" + DATE_WORD + date;
        }
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Commande>>() {
                }.getType());
    }

    private static ArrayList<Consomme> loadConsomme(Long date) throws Exception {
        String url;
        if (date == null) {
            url = URL + LOAD_URL + TYPE_WORD + TYPE.CONSOMME;
        }
        else {
            url = URL + LOAD_URL + TYPE_WORD + TYPE.CONSOMME + "&" + DATE_WORD + date;
        }
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Consomme>>() {
                }.getType());
    }

    /**
     * Charge l'historique des dates
     */

    public static ArrayList<DateBean> loadHistoriqueDate() throws Exception {
        String url = URL + LOAD_DATE_URL;
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();

        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<DateBean>>() {
                }.getType());
    }

    /**
     * Sauvegarde la base de donnée sur le serveur
     */
    public static void saveData() throws Exception {
        //Pour qu'il est tous la même date
        long time = new Date().getTime();
        saveCategorie(time);
        saveCommande(time);
        saveProduit(time);
        saveConsomme(time);
    }

    /**
     * Sauvegarde l'état des catégories sur le serveur
     */
    public static void saveCategorie(long time) throws Exception {
        List<Categorie> list = CategorieBddManager.getCategories();
        String url = URL + SAVE_URL + TYPE_WORD + TYPE.CATEGORIE + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveProduit(long time) throws Exception {
        List<Produit> list = ProduitBddManager.getProduit();
        String url = URL + SAVE_URL + TYPE_WORD + TYPE.PRODUIT + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveConsomme(long time) throws Exception {
        List<Consomme> list = ConsommeBddManager.getConsomme();
        String url = URL + SAVE_URL + TYPE_WORD + TYPE.CONSOMME + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveCommande(long time) throws Exception {
        List<Commande> list = CommandeBddManager.getCommande();
        String url = URL + SAVE_URL + TYPE_WORD + TYPE.COMMANDE + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }
}
