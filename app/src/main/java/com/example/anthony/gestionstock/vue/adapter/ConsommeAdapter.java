package com.example.anthony.gestionstock.vue.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.Utils;

import java.util.ArrayList;

import greendao.Consomme;

/**
 * Created by Anthony on 15/12/2016.
 */
public class ConsommeAdapter extends RecyclerView.Adapter<ConsommeAdapter.ViewHolder> {

    private ArrayList<Consomme> consommeArrayList;
    private ConsommeAdapterCallBack consommeAdapterCallBack;

    public ConsommeAdapter(ArrayList<Consomme> consommeArrayList, ConsommeAdapterCallBack consommeAdapterCallBack) {
        this.consommeArrayList = consommeArrayList;
        this.consommeAdapterCallBack = consommeAdapterCallBack;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displaylibelle;
        public TextView displayTarif;
        public TextView displayMontant;
        public TextView displayQuantite;
        public ImageView displayDeleteProduit;

        public ViewHolder(View itemView) {
            super(itemView);

            displayQuantite = (TextView) itemView.findViewById(R.id.quantite_note);
            displaylibelle = (TextView) itemView.findViewById(R.id.libelle_note);
            displayTarif = (TextView) itemView.findViewById(R.id.prix_u_note);
            displayMontant = (TextView) itemView.findViewById(R.id.montant_note);
            displayDeleteProduit = (ImageView) itemView.findViewById(R.id.delete_produit_note);
            displayDeleteProduit.setColorFilter(displayDeleteProduit.getResources().getColor(R.color.red));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConsommeAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cellule_note_accueil, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Consomme consomme = consommeArrayList.get(position);
        holder.displayQuantite.setText(consomme.getQuantite() + "");
        holder.displaylibelle.setText(consomme.getProduitRef().getNom());
        holder.displayTarif.setText(Utils.formatToMoney(consomme.getProduitRef().getPrix()));
        holder.displayMontant.setText(Utils.formatToMoney(consomme.getProduitRef().getPrix() * consomme.getQuantite()));
        holder.displayDeleteProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (consommeAdapterCallBack != null) {
                    consommeAdapterCallBack.clicOnDeleteNote(consomme);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return consommeArrayList.size();
    }

    public interface ConsommeAdapterCallBack {
        void clicOnDeleteNote(Consomme consomme);
    }
}
