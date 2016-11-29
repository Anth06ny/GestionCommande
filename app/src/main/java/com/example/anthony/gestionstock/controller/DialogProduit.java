package com.example.anthony.gestionstock.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;
import java.util.List;

import greendao.Categorie;
import greendao.Produit;
import model.CategorieBddManager;
import model.ProduitBddManager;

/**
 * Created by Allan on 23/11/2016.
 */

public class DialogProduit extends DialogFragment {

    private Categorie categorieSelected;
    private EditText editNom;
    private Spinner editCategorie;
    private EditText editPrix;
    private EditText editLot;
    private Produit produit;
    private List<Categorie> listCategories;
    private SpinnerAdapter spinnerTest;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_produit, null);

        //Initialisation de la liste de catégories
        listCategories = new ArrayList<>();
        listCategories = CategorieBddManager.getFromSQLCategories();

        // instancie l'adapteur pour la liste déroulante
        spinnerTest = new vue.SpinnerAdapter(this.getActivity(), listCategories);

        //On build la dialog box avec la vue personaliser + l'ajout des boutons positifs et négatif
        builder.setView(alertDialogView)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //On recupere les elements graphique et leur valeur, Recupère ce qui est contenu dans le champ apres saisi de l'utilisateur
                        editNom = (EditText) alertDialogView.findViewById(R.id.editNomProduit);
                        editPrix = (EditText) alertDialogView.findViewById(R.id.editPrixProduit);
                        editLot = (EditText) alertDialogView.findViewById(R.id.editLotProduit);

                        //TO DO: Gérer les Exceptions en cas d'input VIDE

                        //On passe les données recupèrer dans l'objet Catégorie
                        produit = new Produit();

                        // TO DO : verifier le type de données saisies
                        produit.setNom(editNom.getText().toString());
                        produit.setPrix(Float.valueOf(String.valueOf(editPrix.getText())));
                        produit.setLot(Integer.valueOf(String.valueOf(editLot.getText())));
                        produit.setCategorieID(categorieSelected.getId());

                        ProduitBddManager.saveToSQLPproduit(produit);
                        Log.v("TAG5", categorieSelected.getId().toString());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        //recupère l'élément graphique de l'adapteur et le switch entre celui du layout du dialogue.
        editCategorie = (Spinner) alertDialogView.findViewById(R.id.editCategorieProduit);
        editCategorie.setAdapter(spinnerTest);
        editCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorieSelected = (Categorie) spinnerTest.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return builder.create();
    }
}