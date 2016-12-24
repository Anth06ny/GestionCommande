package com.example.anthony.gestionstock.controller;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allan on 22/12/2016.
 */

public class DateUtils {

    /**
     * Retourne la date de debut du mois en cours
     *
     * @return
     */
    public static Date get1erJourMoisEnCours() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);

        return c.getTime();
    }

    /**
     * Retourne le 1er jours de la semaine en cours à 0h0
     *
     * @return
     */
    public static Date get1erJourSemaineEnCours() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);

        return c.getTime();
    }

    /**
     * Retourne le 1er jour de l'anéee en cours
     *
     * @return
     */
    public static Date get1erJourAnneeEnCours() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);

        return c.getTime();
    }
}
