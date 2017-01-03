package com.example.anthony.gestionstock.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import com.example.anthony.gestionstock.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Axel legué on 08/12/2016.
 */

public class DatePickerFragment extends DialogFragment implements DatePicker.OnDateChangedListener, DialogInterface.OnClickListener {

    private int reqCode;
    DatePickerFragmentCallBack datePickerFragmentCallBack;
    private Date date;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a Calendar to get the year, month, and day
        final Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        // calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater().inflate(
                R.layout.cellule_date_picker, null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, this);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Séléctionnez une date")
                .setPositiveButton(R.string.valider, this).setNegativeButton(R.string.annuler, null).create();
    }

    /* ---------------------------------
    // CalBack PositiveButton
    // -------------------------------- */

    public void onClick(DialogInterface dialog, int which) {
        if (datePickerFragmentCallBack != null) {
            datePickerFragmentCallBack.onSelectDate(reqCode, date);
        }
    }

    /* ---------------------------------
    // CalBack DatePicker change
    // -------------------------------- */
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0);
        date = calendar.getTime();
    }


    /* ---------------------------------
    // Setter
    // -------------------------------- */

    public void setDatePickerFragmentCallBack(DatePickerFragmentCallBack datePickerFragmentCallBack) {
        this.datePickerFragmentCallBack = datePickerFragmentCallBack;
    }

    public void setReqCode(int reqCode) {
        this.reqCode = reqCode;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /* ---------------------------------
    // Interface
    // -------------------------------- */
    public interface DatePickerFragmentCallBack {
        void onSelectDate(int reqCode, Date date);
    }
}
