package model;

import com.example.anthony.gestionstock.controller.MyApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import greendao.Commande;
import greendao.Consomme;

/**
 * Created by Axel legué on 07/12/2016.
 */

public class ConsommeBddManager {

    // ------------------------------------ '' SQL QUERY Fnctions ''' -------------------------------- //

    public static List<Consomme> getConsomme() {
        return MyApplication.getDaoSession().getConsommeDao().loadAll();
    }

    public static void insertConsommeList(ArrayList<Consomme> consommeArrayList) throws Exception {
        if (consommeArrayList == null || consommeArrayList.isEmpty()) {
            return;
        }
        //on Ajoute une commande en base
        final Commande commande = new Commande();
        commande.setDate(Calendar.getInstance().getTime());
        CommandeBddManager.insertOrUpdate(commande);

        if (commande.getId() == null) {
            throw new Exception("Erreur lors de la sauvegarde de la commande");
        }

        //On ajoute chaque consomme à la base
        for (Consomme consomme : consommeArrayList) {
            consomme.setCommande(commande.getId());
            insertOrUpdate(consomme);
        }
    }

    public static void insertOrUpdate(Consomme consomme) {
        MyApplication.getDaoSession().getConsommeDao().insertOrReplace(consomme);
    }

    public static void clearConsomme() {
        MyApplication.getDaoSession().getConsommeDao().deleteAll();
    }

    public static void deleteConsomme(Consomme consomme) {
        MyApplication.getDaoSession().getConsommeDao().delete(consomme);
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
