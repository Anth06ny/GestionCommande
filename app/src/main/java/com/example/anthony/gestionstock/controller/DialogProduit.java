package com.example.anthony.gestionstock.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.anthony.gestionstock.R;

import greendao.Produit;
import greendao.ProduitDao;
import model.ProduitBddManager;

/**
 * Created by Allan on 23/11/2016.
 */

public class DialogProduit extends DialogFragment {

    // Dao --> Data Acces Object
    private ProduitDao produitDao; // Sql acces object
    private ProduitBddManager produitBddManager;

    private EditText editNom;
    private EditText editCategorie;
    private EditText editPrix;
    private EditText editLot;
    private Produit produit;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_produit, null);

        //Initalise la DAO
        produitBddManager = new ProduitBddManager();
        produitDao = produitBddManager.setupDB(this.getContext());

        //On build la dialog box avec la vue personaliser + l'ajout des boutons positifs et négatif
        builder.setView(alertDialogView)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Ensemble des taches a realiser quand l'utilisateur cliq sur envoyer
                        //On recupere les elements graphique et leur valeur
                        //Recupère ce qui est contenu dans le champ apres saisi de l'utilisateur
                        editNom = (EditText) alertDialogView.findViewById(R.id.editNomProduit);
                        editCategorie = (EditText) alertDialogView.findViewById(R.id.editCategorieProduit);
                        editPrix = (EditText) alertDialogView.findViewById(R.id.editPrixProduit);
                        editLot = (EditText) alertDialogView.findViewById(R.id.editLotProduit);
                        //TO DO: Gérer les Exceptions en cas d'input VIDE
                        //On passe les données recupèrer dans l'objet Catégorie
                        produit = new Produit();
                        produit.setNom(editNom.getText().toString());
                        // TO DO : verifier le type de données saisies
                        produit.setLot(editLot.getInputType());
                        produit.setPrix((float) editPrix.getInputType());

                        Produit insProduit = new Produit(null, produit.getNom(), produit.getPrix(), produit.getLot(), null, null);
                        produitBddManager.saveToSQLPproduit(produitDao, insProduit);
                        produitBddManager.generateResultProduit(produitDao);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
}