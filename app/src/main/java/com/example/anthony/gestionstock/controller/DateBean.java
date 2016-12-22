package com.example.anthony.gestionstock.controller;

/**
 * Created by Allan on 22/12/2016.
 */

public class DateBean {
    private long dateSave;

    public DateBean(long dateSave) {
        this.dateSave = dateSave;
    }

    public long getDateSave() {
        return dateSave;
    }

    public void setDateSave(long dateSave) {
        this.dateSave = dateSave;
    }
}
