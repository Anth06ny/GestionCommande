package model;

import com.example.anthony.gestionstock.controller.MyApplication;

import java.util.List;

import greendao.Consomme;

/**
 * Created by Axel legué on 07/12/2016.
 */

public class ConsommeBddManager {

    // ------------------------------------ '' SQL QUERY Fnctions ''' -------------------------------- //

    public static List<Consomme> getConsomme() {
        return MyApplication.getDaoSession().getConsommeDao().loadAll();
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
