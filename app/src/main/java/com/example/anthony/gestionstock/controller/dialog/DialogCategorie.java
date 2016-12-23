package com.example.anthony.gestionstock.controller.dialog;

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
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.vue.custom_composant.material_color_picker.ColorChooserDialog;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import greendao.Categorie;

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
            couleurChoisi = Integer.parseInt(categorie.getCouleur());
        }
        else {
            couleurChoisi = getContext().getResources().getColor(R.color.black);
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

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean canCloseDialog = true;
                ArrayList<Categorie> categorieArrayList;
                categorieArrayList = (ArrayList<Categorie>) CategorieBddManager.getCategories();

                if (categorieArrayList.size() < NB_MAX_CATEGORIE) {
                    if (StringUtils.isBlank(edit_nomCategorie.getText())) {
                        Toast.makeText(getContext(), R.string.dialog_categorie_toast_erreur_nom, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    categorie.setNom(String.valueOf(edit_nomCategorie.getText()));
                    for (int i = 0; i < categorieArrayList.size(); i++) {
                        if (StringUtils.equalsIgnoreCase(categorie.getNom(), categorieArrayList.get(i).getNom()) && categorie.getId() !=
                                categorieArrayList.get(i).getId()) {

                            //Si la couleur est deja utiliser et que les id sont different alors on passe un boolean canClose a false
                            canCloseDialog = false;
                            Toast.makeText(getContext(), R.string.dialog_categorie_toast_erreur_nom_double, Toast.LENGTH_SHORT).show();
                        }
                    }

                    categorie.setCouleur(String.valueOf(couleurChoisi));

                    //On verifie que la couleur choisie ne correpond pas a une couleur d'une autre categorie dans la bdd
                    //Si il y a deux couleur iddentique on verifie les id des deux categorie
                    //Si les id correpondent alors il n'y a pas d'erreur puisqu'on est en train de modifier une categorie
                    //Si les id ne correspondent pas alors on informe l'utilisateur que la couleur qu'il a choisie est deja utiliser pour une
                    // autre categorie dans la bdd
                    for (int i = 0; i < categorieArrayList.size(); i++) {
                        if (StringUtils.equalsIgnoreCase(categorie.getCouleur(), categorieArrayList.get(i).getCouleur()) && categorie.getId() !=
                                categorieArrayList.get(i).getId()) {

                            //Si la couleur est deja utiliser et que les id sont different alors on passe un boolean canClose a false
                            canCloseDialog = false;
                            Toast.makeText(getContext(), R.string.dialog_categorie_toast_erreur_couleur, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (canCloseDialog) {

                        dialogCategorieCallBack.dialogCategorieClicOnValider();
                        dialog.dismiss();
                    }
                }
                else {
                    Toast.makeText(getContext(), R.string.dialog_categorie_toast_erreur_nb_max, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return dialog;
    }

    public void setDialogCategorieCallBack(DialogCategorieCallBack dialogCategorieCallBack) {
        this.dialogCategorieCallBack = dialogCategorieCallBack;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public interface DialogCategorieCallBack {
        void dialogCategorieClicOnValider();
    }
}
