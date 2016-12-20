package com.example.anthony.gestionstock.model.webservice;

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

    private static final String SAVE_URL = "http://192.168.10.204/WebServiceAndroid/postJson.php?";
    private static final String LOAD_URL = "http://192.168.10.204/WebServiceAndroid/getJson.php?";
    private static final String TYPE_WORD = "type=";
    private static final String DATE_WORD = "date=";

    private enum TYPE {
        CATEGORIE, CONSOMME, PRODUIT, COMMANDE
    }

    /**
     * Charge le base de donnée sur le serveur
     */
    public static void loadData() throws Exception {
        ArrayList<Categorie> catFromWeb = loadCategorie();
        ArrayList<Produit> prodFromWeb = loadProduit();
        ArrayList<Commande> commandeFromWeb = loadCommande();
        ArrayList<Consomme> consomeFromWeb = loadConsomme();

        MaBaseSQLite.clearAllTable();

        CategorieBddManager.insertCategorieList(catFromWeb);
        ProduitBddManager.insertProduitList(prodFromWeb);
        CommandeBddManager.insertCommandeList(commandeFromWeb);
        ConsommeBddManager.insertConsommeList(consomeFromWeb);
    }

    private static ArrayList<Categorie> loadCategorie() throws Exception {
        String url = LOAD_URL + TYPE_WORD + TYPE.CATEGORIE;
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Categorie>>() {
                }.getType());
    }

    private static ArrayList<Produit> loadProduit() throws Exception {
        String url = LOAD_URL + TYPE_WORD + TYPE.PRODUIT;
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Produit>>() {
                }.getType());
    }

    private static ArrayList<Commande> loadCommande() throws Exception {
        String url = LOAD_URL + TYPE_WORD + TYPE.COMMANDE;
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Commande>>() {
                }.getType());
    }

    private static ArrayList<Consomme> loadConsomme() throws Exception {
        String url = LOAD_URL + TYPE_WORD + TYPE.CONSOMME;
        String jsonString = OkHttpUtils.sendGetOkHttpRequest(url);
        Gson gson = new Gson();
        return gson.fromJson(jsonString,
                new TypeToken<ArrayList<Consomme>>() {
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
     * Sauvegarde l'état des catégories    sur le serveur
     */
    public static void saveCategorie(long time) throws Exception {
        List<Categorie> list = CategorieBddManager.getCategories();
        String url = SAVE_URL + TYPE_WORD + TYPE.CATEGORIE + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveProduit(long time) throws Exception {
        List<Produit> list = ProduitBddManager.getProduit();
        String url = SAVE_URL + TYPE_WORD + TYPE.PRODUIT + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveConsomme(long time) throws Exception {
        List<Consomme> list = ConsommeBddManager.getConsomme();
        String url = SAVE_URL + TYPE_WORD + TYPE.CONSOMME + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }

    /**
     * Sauvegarde l'état des produits sur le serveur
     */
    public static void saveCommande(long time) throws Exception {
        List<Commande> list = CommandeBddManager.getCommande();
        String url = SAVE_URL + TYPE_WORD + TYPE.COMMANDE + "&" + DATE_WORD + time;

        OkHttpUtils.sendPostOkHttpRequest(url, MyApplication.getGson().toJson(list));
    }
}
