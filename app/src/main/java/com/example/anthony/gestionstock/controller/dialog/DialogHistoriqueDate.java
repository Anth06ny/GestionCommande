package com.example.anthony.gestionstock.controller.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.model.webservice.WSUtils;
import com.example.anthony.gestionstock.vue.AlertDialogutils;
import com.example.anthony.gestionstock.vue.adapter.HistoriqueAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Allan on 22/12/2016.
 */

public class DialogHistoriqueDate extends DialogFragment implements HistoriqueAdapter.HistoriqueAdapterCallBack {

    private RecyclerView recyclerViewHistorique;
    private ArrayList<Date> dateArrayList;
    private HistoriqueAdapter historiqueAdapter;
    DialogHistoriqueDateCallBack dialogHistoriqueDateCallBack;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_historique_date, null);

        historiqueAdapter = new HistoriqueAdapter(dateArrayList, this);
        recyclerViewHistorique = (RecyclerView) alertDialogView.findViewById(R.id.rv_dialog_historique);

        recyclerViewHistorique.setAdapter(historiqueAdapter);
        recyclerViewHistorique.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewHistorique.setItemAnimator(new DefaultItemAnimator());

        historiqueAdapter.notifyDataSetChanged();

        builder.setView(alertDialogView).setNegativeButton(R.string.annuler, null);
        return builder.create();
    }

    public void setDateArrayList(ArrayList<Date> dateArrayList) {
        this.dateArrayList = dateArrayList;
    }

    @Override
    public void cliqOnCharger(final Date date) {
        AlertDialogutils.showOkCancelDialog(getActivity(), R.string.confirmation, R.string.dialog_reglage_ask_confirm_load, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    new MonAT(date).execute();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), "Une erreur est survenue " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private class MonAT extends AsyncTask<Void, Void, Exception> {
        private Date date;
        private ProgressDialog progressDialog;

        public MonAT(Date date) {
            this.date = date;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(), "", getContext().getString(R.string.reglage_load_message), true, false);
        }

        @Override
        protected Exception doInBackground(Void... params) {
            try {
                WSUtils.loadData(date);
                return null;
            }
            catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            if (e != null) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Une erreur est survenue " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.dismiss();
                dialogHistoriqueDateCallBack.loadFinish();
            }
        }
    }

    public interface DialogHistoriqueDateCallBack {
        void loadFinish();
    }

    public void setDialogHistoriqueDateCallBack(DialogHistoriqueDateCallBack dialogHistoriqueDateCallBack) {
        this.dialogHistoriqueDateCallBack = dialogHistoriqueDateCallBack;
    }
}
