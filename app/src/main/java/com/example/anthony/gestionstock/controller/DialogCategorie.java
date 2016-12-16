package com.example.anthony.gestionstock.controller;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.anthony.gestionstock.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import greendao.Categorie;
import com.example.anthony.gestionstock.model.CategorieBddManager;
import com.example.anthony.gestionstock.vue.material_color_picker.ColorChooserDialog;

/**
 * Created by Allan on 23/11/2016.
 */

public class DialogCategorie extends DialogFragment {

    private AppCompatImageButton btnChoisirCouleur;
    private int couleurChoisi;
    private Categorie categorie;
    private EditText edit_nomCategorie;
    private final int NB_MAX_CATEGORIE = 6;

    DialogCategorieCallBack dialogCategorieCallBack;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_categorie, null);

        //Recuperation des elements graphique de la dialog box
        btnChoisirCouleur = (AppCompatImageButton) alertDialogView.findViewById(R.id.btnChoisirCouleur);
        edit_nomCategorie = (EditText) alertDialogView.findViewById(R.id.nomCategorie);

        //Si on est en modification on va rentrer par defaut les valeurs de la categorie selectionner
        if (categorie.getNom() != null) {
            edit_nomCategorie.setText(categorie.getNom());
        }

        if (categorie.getCouleur() != null) {
            btnChoisirCouleur.setColorFilter(Integer.parseInt(categorie.getCouleur()), PorterDuff.Mode.SRC_IN);
        }

        //Je récupère l'icon des ressources et je la change de couleur
        Drawable icon = getResources().getDrawable(R.drawable.ic_note_add_black_48dp);
        icon.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        builder.setIcon(icon);
        builder.setTitle(categorie.getId() == null ? R.string.dialog_categorie_title_new : R.string.dialog_categorie_title_edit);

        //On build la dialog box avec la vue personaliser + l'ajout des boutons positifs et négatif
        builder.setView(alertDialogView)
                .setPositiveButton(R.string.valider,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                int tag = 0;
                                Boolean erreur = false;

                                //On verifie que l'edit text de la dialog categorie n'est pas vide
                                ArrayList<Categorie> categorieArrayList;
                                categorieArrayList = (ArrayList<Categorie>) CategorieBddManager.getCategories();
                                if (categorieArrayList.size() < NB_MAX_CATEGORIE) {
                                    if (StringUtils.isNotBlank(edit_nomCategorie.getText())) {

                                        categorie.setNom(edit_nomCategorie.getText().toString());

                                        //On verifie que le nom de la categorie saisie dans l'edit ne correpond a aucune autre categorie de la bdd
                                        //Et si il y a une categorie qui porte le meme nom on verifie les id des deux categories
                                        //Si les id correpondent alors il n'y a pas d'erreur puisqu'on est en train de modifier une categorie
                                        //Si les id ne correspondent pas alors on informe l'utilisateur que la categorie qu'il a saisie existe deja dans la bdd
                                        for (int i = 0; i < categorieArrayList.size(); i++) {

                                            if (StringUtils.equalsIgnoreCase(categorie.getNom(), categorieArrayList.get(i).getNom()) && categorie.getId() != categorieArrayList.get(i).getId()) {
                                                //Si le nom existe deja et que les id sont different alors on passe un boolean erreur a true
                                                erreur = true;
                                                tag = 1;
                                            }
                                        }
                                        //Si le boolean erreur est a false et que l'utilisateur a saisie une couleur
                                        if (couleurChoisi != 0 && !erreur) {
                                            categorie.setCouleur(String.valueOf(couleurChoisi));
                                            //On verifie que la couleur choisie ne correpond pas a une couleur d'une autre categorie dans la bdd
                                            //Si il y a deux couleur iddentique on verifie les id des deux categorie
                                            //Si les id correpondent alors il n'y a pas d'erreur puisqu'on est en train de modifier une categorie
                                            //Si les id ne correspondent pas alors on informe l'utilisateur que la couleur qu'il a choisie est deja utiliser pour une
                                            // autre categorie dans la bdd
                                            for (int i = 0; i < categorieArrayList.size(); i++) {
                                                if (StringUtils.equalsIgnoreCase(categorie.getCouleur(), categorieArrayList.get(i).getCouleur()) && categorie.getId() !=
                                                        categorieArrayList.get(i).getId()) {
                                                    //Si la couleur est deja utiliser et que les id sont different alors on passe un boolean erreur a true
                                                    erreur = true;
                                                    tag = 2;
                                                }
                                            }
                                            if (!erreur) {
                                                //Si il n'y a pas d'erreur on envoie a l'aide du callback la categorie a ajouter a l'ecran reglage qui va l'ajouter en bdd
                                                dialogCategorieCallBack.dialogCategorieClicOnValider();
                                            }
                                            else {
                                                //Si il y a une erreur on envoie a l'aide un callback d'erreur qui va indiquer a l'utilisateur qu'il y a une erreur
                                                dialogCategorieCallBack.dialogCategorieClicOnValiderErreur(tag);
                                            }
                                        }
                                        //Si il n'y a pas de couleur saisie
                                        else {
                                            //Si il ya une couleur dans categorie on envoie via le callback en bdd la categorie
                                            if (categorie.getCouleur() != null) {
                                                dialogCategorieCallBack.dialogCategorieClicOnValider();
                                            }
                                            //Sinon on envoie un callback d'erreur
                                            else {
                                                dialogCategorieCallBack.dialogCategorieClicOnValiderErreur(tag);
                                            }
                                        }
                                    }
                                    //Si il n'y a pas de nom saisie on envoie un callback d'erreur
                                    else {
                                        dialogCategorieCallBack.dialogCategorieClicOnValiderErreur(tag);
                                    }
                                }
                                else {
                                    tag = 3;
                                    dialogCategorieCallBack.dialogCategorieClicOnValiderErreur(tag);
                                }
                            }
                        }

                                  ).setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Ensemble des taches a realiser quand l'utilisateur cliq sur annuler
                    }
                }

                                                     );

        //Au cliq du bouton on ouvre une nouvelle dialog box qui affiche le color picker
        btnChoisirCouleur.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        final ColorChooserDialog dialog = new ColorChooserDialog();
                        if (couleurChoisi != 0) {
                            dialog.setSelectedColor(couleurChoisi);
                        }
                        else if (StringUtils.isNotBlank(categorie.getCouleur())) {
                            dialog.setSelectedColor(Integer.parseInt(categorie.getCouleur()));
                        }
                        dialog.setColorListener(new ColorChooserDialog.ColorListener() {
                            @Override
                            public void OnColorClick(View v, int color) {
                                //Lorsque l'utilisateur selectionne une couleur on recupere la valeur int de la couleur choisie
                                couleurChoisi = color;

                                //On display la couleur choisie sur la dialog box precedente
                                btnChoisirCouleur.setColorFilter(couleurChoisi, PorterDuff.Mode.SRC_IN);

                                //On termine le picker color et on retourne sur la dialog box precedente
                                dialog.dismiss();
                            }
                        });
                        dialog.show(getFragmentManager(), "tag");
                    }
                }

                                            );

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

        void dialogCategorieClicOnValiderErreur(int tag);
    }
}
