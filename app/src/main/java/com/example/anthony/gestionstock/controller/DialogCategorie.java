package com.example.anthony.gestionstock.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import java.util.ArrayList;
import java.util.Objects;

import greendao.Categorie;
import model.CategorieBddManager;

/**
 * Created by Allan on 23/11/2016.
 */

public class DialogCategorie extends DialogFragment {

    private Button btnChoisirCouleur;
    private int couleurChoisi;
    private TextView box;
    private Categorie categorie;
    private EditText edit_nomCategorie;
    private Boolean choixDialog;
    private Bundle mArgs;

    DialogCategorieCallBack dialogCategorieCallBack;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_categorie, null);

        //Recuperation des elements graphique de la dialog box
        box = (TextView) alertDialogView.findViewById(R.id.boxCouleur);
        btnChoisirCouleur = (Button) alertDialogView.findViewById(R.id.btnChoisirCouleur);
        edit_nomCategorie = (EditText) alertDialogView.findViewById(R.id.nomCategorie);

        //Si on est en modification on va rentrer par defaut les valeurs de la categorie selectionner
        if (categorie.getNom() != null) {
            edit_nomCategorie.setText(categorie.getNom());
        }

        if (categorie.getCouleur() != null) {
            box.setBackgroundColor(Integer.parseInt(categorie.getCouleur()));
        }

        //On build la dialog box avec la vue personaliser + l'ajout des boutons positifs et négatif
        builder.setView(alertDialogView)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //TODO: Gérer les Exceptions en cas d'ajout d'une categorie deja existante

                        if (edit_nomCategorie.getText().toString().length() != 0) {
                            Boolean erreur = false;
                            ArrayList<Categorie> categorieArrayList = new ArrayList<>();
                            categorieArrayList = (ArrayList<Categorie>) CategorieBddManager.getCategories();

                            categorie.setNom(edit_nomCategorie.getText().toString());

                            for (int i = 0; i < categorieArrayList.size(); i++) {
                                if (Objects.equals(categorie.getNom(), categorieArrayList.get(i).getNom()) && !Objects.equals(categorie.getId(), categorieArrayList.get(i).getId())) {
                                    erreur = true;
                                }
                            }
                            if (couleurChoisi != 0 && !erreur) {
                                categorie.setCouleur(String.valueOf(couleurChoisi));
                                dialogCategorieCallBack.dialogCategorieClicOnValider();
                            }
                            else {
                                if (categorie.getCouleur() == null) {
                                    dialogCategorieCallBack.dialogCategorieClicOnValiderErreur();
                                }
                                else {
                                    dialogCategorieCallBack.dialogCategorieClicOnValider();
                                }
                            }
                        }

                        else {
                            dialogCategorieCallBack.dialogCategorieClicOnValiderErreur();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Ensemble des taches a realiser quand l'utilisateur cliq sur annuler
                    }
                });

        //Au cliq du bouton on ouvre une nouvelle dialog box qui affiche le color picker
        btnChoisirCouleur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

    public void setDialogCategorieCallBack(DialogCategorieCallBack dialogCategorieCallBack) {
        this.dialogCategorieCallBack = dialogCategorieCallBack;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public interface DialogCategorieCallBack {
        void dialogCategorieClicOnValider();

        void dialogCategorieClicOnValiderErreur();
    }
}
