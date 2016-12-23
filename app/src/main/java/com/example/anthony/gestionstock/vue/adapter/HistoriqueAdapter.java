package com.example.anthony.gestionstock.vue.adapter;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Allan on 22/12/2016.
 */

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.ViewHolder> {
    private ArrayList<Date> dateArrayList;
    HistoriqueAdapterCallBack historiqueAdapterCallBack;

    public HistoriqueAdapter(ArrayList<Date> dateArrayList, HistoriqueAdapterCallBack historiqueAdapterCallBack) {
        this.dateArrayList = dateArrayList;
        this.historiqueAdapterCallBack = historiqueAdapterCallBack;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displayDate;
        public TextView displayHeure;
        public AppCompatButton buttonCharger;

        public ViewHolder(View itemView) {
            super(itemView);

            displayDate = (TextView) itemView.findViewById(R.id.display_date_historique);
            displayHeure = (TextView) itemView.findViewById(R.id.display_heure_historique);
            buttonCharger = (AppCompatButton) itemView.findViewById(R.id.btn_charger_donnees);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoriqueAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cellule_date_historique, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoriqueAdapter.ViewHolder holder, int position) {
        final Date datebean = dateArrayList.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.displayDate.setText(simpleDateFormat.format(datebean));

        SimpleDateFormat simpleHeureFormat = new SimpleDateFormat("HH:mm:ss");
        holder.displayHeure.setText(simpleHeureFormat.format(datebean));

        holder.buttonCharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historiqueAdapterCallBack.cliqOnCharger(datebean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateArrayList.size();
    }

    public interface HistoriqueAdapterCallBack {
        void cliqOnCharger(Date date);
    }
}
