package com.example.anthony.gestionstock.model.bdd;

import com.example.anthony.gestionstock.controller.MyApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import greendao.Commande;
import greendao.CommandeDao;

/**
 * Created by Axel legué on 07/12/2016.
 */

public class CommandeBddManager {

    // ------------------------------------ '' SQL QUERY Fnctions ''' -------------------------------- //

    public static List<Commande> getCommande() {
        return MyApplication.getDaoSession().getCommandeDao().loadAll();
    }

    public static List<Commande> getCommandeBetweenDate(Date debut, Date fin) {
        Calendar cDebut = Calendar.getInstance();
        cDebut.setTime(debut);
        cDebut.set(Calendar.HOUR_OF_DAY, 0);
        cDebut.set(Calendar.MINUTE, 0);
        cDebut.set(Calendar.SECOND, 0);

        Calendar cFin = Calendar.getInstance();
        cFin.setTime(fin);
        cFin.set(Calendar.HOUR_OF_DAY, 23);
        cFin.set(Calendar.MINUTE, 59);
        cFin.set(Calendar.SECOND, 59);

        return MyApplication.getDaoSession().getCommandeDao().queryBuilder()
                .where(CommandeDao.Properties.Date.between(cDebut.getTime(), cFin.getTime())).list();
    }

    public static void insertCommandeList(ArrayList<Commande> commandeArrayList) throws Exception {
        if (commandeArrayList == null || commandeArrayList.isEmpty()) {
            return;
        }
        //On ajoute chaque consomme à la base
        for (Commande commande : commandeArrayList) {
            insertOrUpdate(commande);
        }
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
