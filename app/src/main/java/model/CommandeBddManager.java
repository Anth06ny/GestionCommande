package model;

import com.example.anthony.gestionstock.controller.MyApplication;

import java.util.List;

import greendao.Commande;

/**
 * Created by Axel legué on 07/12/2016.
 */

public class CommandeBddManager {

    // ------------------------------------ '' SQL QUERY Fnctions ''' -------------------------------- //

    public static List<Commande> getCommande() {
        return MyApplication.getDaoSession().getCommandeDao().loadAll();
    }

    public static void insertOrUpdate(Commande commande) {
        MyApplication.getDaoSession().getCommandeDao().insertOrReplace(commande);
    }

    public static void clearCommande() {
        MyApplication.getDaoSession().getCommandeDao().deleteAll();
    }

    public static void deleteCommande(Commande commande) {
        MyApplication.getDaoSession().getCommandeDao().delete(commande);
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
