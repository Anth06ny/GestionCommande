package com.example.anthony.gestionstock.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import com.example.anthony.gestionstock.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Axel legué on 08/12/2016.
 */

public class DatePickerFragment extends DialogFragment {
    DatePickerFragmentCallBack datePickerFragmentCallBack;
    private Date mDate;

    public static DatePickerFragment newInstance() {
        Bundle args = new Bundle();
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent i = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(),
                resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a Calendar to get the year, month, and day
        final Calendar calendar = Calendar.getInstance();
        // calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater().inflate(
                R.layout.cellule_date_picker, null);

        DatePicker datePicker = (DatePicker) v
                .findViewById(R.id.dialog_date_datePicker);
        mDate = (Calendar.getInstance().getTime());

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month,
                                      int day) {
                // Translate year, month, day into a Date object using a
                // calendar
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                mDate = new GregorianCalendar(year, month, day, hour, minute)
                        .getTime();
                // Update argument to preserve selected value on rotation
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Date")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                datePickerFragmentCallBack.onSelectDate(mDate);
                                sendResult(Activity.RESULT_OK);
                            }
                        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
    }

    public interface DatePickerFragmentCallBack {
        void onSelectDate(Date date);
    }

    public void setDatePickerFragmentCallBack(DatePickerFragmentCallBack datePickerFragmentCallBack) {
        this.datePickerFragmentCallBack = datePickerFragmentCallBack;
    }
}
