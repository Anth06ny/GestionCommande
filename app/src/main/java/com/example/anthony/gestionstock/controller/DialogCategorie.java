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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import greendao.Categorie;

/**
 * Created by Allan on 23/11/2016.
 */

public class DialogCategorie extends DialogFragment {

    private Button btnChoisirCouleur;
    private int couleurChoisi;
    private TextView box;
    private Categorie categorieTest = new Categorie();
    private EditText edit_nomCategorie;
    private TextView edit_couleurCategorie;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_categorie, null);

        //On build la dialog box avec la vue personaliser + l'ajout des boutons positifs et négatif
        builder.setView(alertDialogView)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Ensemble des taches a realiser quand l'utilisateur cliq sur envoyer
                        //Recupère ce qui est contenu dans le champ apres saisi de l'utilisateur
                        edit_nomCategorie = (EditText) alertDialogView.findViewById(R.id.nomCategorie);
                        edit_couleurCategorie = (TextView) alertDialogView.findViewById(R.id.boxCouleur);
                        //TO DO: Gérer les Exceptions en cas d'input VIDE
                        //On passe les données recupèrer dans l'objet Catégorie
                        categorieTest.setNom(edit_nomCategorie.getText().toString());
                        categorieTest.setCouleur(String.valueOf(couleurChoisi));
                        //TO DO : insertion des données en BDD


                        Log.v("TestNom", categorieTest.getNom());
                        Log.v("TestColor", categorieTest.getCouleur());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Ensemble des taches a realiser quand l'utilisateur cliq sur annuler
                    }
                });

        //Recuperation des elements graphique de la dialog box
        box = (TextView) alertDialogView.findViewById(R.id.boxCouleur);
        btnChoisirCouleur = (Button) alertDialogView.findViewById(R.id.btnChoisirCouleur);
        btnChoisirCouleur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Au cliq du bouton on ouvre une nouvelle dialog box qui affiche le color picker
                final ColorChooserDialog dialog = new ColorChooserDialog(getContext());
                dialog.setTitle("Titre");
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        //Lorsque l'utilisateur selectionne une couleur on recupere la valeur int de la couleur choisie
                        couleurChoisi = color;
                        //On display la couleur choisie sur la dialog box precedente
                        box.setBackgroundColor(couleurChoisi);
                        //On termine le picker color et on retourne sur la dialog box precedente
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        return builder.create();
    }
}
