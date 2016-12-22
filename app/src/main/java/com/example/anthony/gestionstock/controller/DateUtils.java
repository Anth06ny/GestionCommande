package com.example.anthony.gestionstock.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allan on 22/12/2016.
 */

public class DateUtils {

    /**
     * Retourne le lundi de la semaine choisie
     *
     * @param date
     * @return
     */
    public static Date getSemaine(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    public static ArrayList<Date> getMois() {
        ArrayList<Date> dateArrayList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 0);

        for (int i = 0; i < c.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            c.add(Calendar.DATE, 1);
            dateArrayList.add(c.getTime());
        }
        return dateArrayList;
    }

    public static ArrayList<Date> getAnnee() {
        ArrayList<Date> dateArrayList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, 0);

        for (int i = 0; i < c.getActualMaximum((Calendar.DAY_OF_YEAR)); i++) {
            c.add(Calendar.DATE, 1);
            dateArrayList.add(c.getTime());
        }
        return dateArrayList;
    }
}
