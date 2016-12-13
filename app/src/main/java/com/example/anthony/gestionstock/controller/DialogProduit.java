package com.example.anthony.gestionstock.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.anthony.gestionstock.R;

import org.apache.commons.lang3.StringUtils;

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
    private ArrayList<Produit> produitArrayList;
    private CheckBox checkBoxFavori;

    DialogProduitCallBack dialogProduitCallBack;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_produit, null);

        //Initialisation de la liste de catégories
        listCategories = new ArrayList<>();
        listCategories = CategorieBddManager.getCategories();

        produitArrayList = new ArrayList<>();
        produitArrayList = (ArrayList<Produit>) ProduitBddManager.getProduit();

        // instancie l'adapteur pour la liste déroulante
        spinnerTest = new vue.SpinnerAdapter(this.getActivity(), listCategories);

        //On recupere les elements graphique et leur valeur, Recupère ce qui est contenu dans le champ apres saisi de l'utilisateur
        editNom = (EditText) alertDialogView.findViewById(R.id.editNomProduit);
        editPrix = (EditText) alertDialogView.findViewById(R.id.editPrixProduit);
        editLot = (EditText) alertDialogView.findViewById(R.id.editLotProduit);
        editCategorie = (Spinner) alertDialogView.findViewById(R.id.editCategorieProduit);
        checkBoxFavori = (CheckBox) alertDialogView.findViewById(R.id.check_favori);

        if (produit.getNom() != null) {
            editNom.setText(produit.getNom());
        }
        if (produit.getPrix() != null) {
            editPrix.setText(String.valueOf(produit.getPrix()));
        }
        if (produit.getLot() != null) {
            editLot.setText(String.valueOf(produit.getLot()));
        }
        if (produit.getCategorieID() >= 0) {
            editCategorie.setSelection(0);
        }
        if (produit.getFavori() != null) {
            checkBoxFavori.setChecked(produit.getFavori());
        }
        //On build la dialog box avec la vue personaliser + l'ajout des boutons positifs et négatif
        builder.setView(alertDialogView)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int tag = 0;
                        Boolean erreur = false;
                        //On verifie que les edit text de la dialog produit ne sont pas vide
                        if (editNom.getText().length() != 0 && editPrix.getText().length() != 0 && editLot.getText().length() != 0) {
                            produit.setNom(editNom.getText().toString());

                            //On verifie que le nom du produit saisie dans l'edit ne correpond a aucun autre produit de la base de donnees
                            //Et si il y a un produit qui porte le meme nom on verifie les id des deux produits
                            //Si les id correpondent alors il n'y a pas d'erreur puisqu'on est en train de modifier un produit
                            //Si les id ne correspondent pas alors on informe l'utilisateur que le produit qu'il a saisie existe deja dans la bdd
                            for (int i = 0; i < produitArrayList.size(); i++) {
                                if (StringUtils.equalsIgnoreCase(produit.getNom().toLowerCase(), produitArrayList.get(i).getNom().toLowerCase()) && produit.getId() !=
                                        produitArrayList.get
                                                (i).getId()) {
                                    //Si le nom existe deja et que les id sont different alors on passe un boolean erreur a true
                                    erreur = true;
                                    tag = 1;
                                }
                            }
                            int countFavori = 0;
                            for (int j = 0; j < produitArrayList.size(); j++) {
                                if (produitArrayList.get(j).getFavori()) {
                                    countFavori++;
                                }
                                if (countFavori == 6) {
                                    erreur = true;
                                    tag = 2;
                                }
                            }
                            //Si le boolean erreur est a false alors on peut recupere les donnees saisie par l'utilisateur et les envoyer grace au callback a la
                            // page fragment reglage qui va envoye en bdd le produit
                            if (!erreur) {
                                produit.setPrix(Float.valueOf(String.valueOf(editPrix.getText())));
                                produit.setLot(Integer.valueOf(String.valueOf(editLot.getText())));
                                produit.setCategorieID(categorieSelected.getId());
                                produit.setFavori(checkBoxFavori.isChecked());
                                dialogProduitCallBack.dialogProduitClicOnValider();
                            }
                            //Si le boolean erreur est vrai alors on envoie un callback d'erreur qui va indiquer a l'utilisateur qu'il y a une erreur
                            else {
                                dialogProduitCallBack.dialogProduitClicOnValiderErreur(tag);
                            }
                        }
                        //Si le champ nom du produit n'est pas remplie on envoie un callback d'erreur qui va indiquer a l'utilisateur qu'il y a une erreur
                        else {
                            dialogProduitCallBack.dialogProduitClicOnValiderErreur(tag);
                        }
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

    public interface DialogProduitCallBack {
        void dialogProduitClicOnValider();

        void dialogProduitClicOnValiderErreur(int tag);
    }

    public void setDialogProduitCallBack(DialogProduitCallBack dialogProduitCallBack) {
        this.dialogProduitCallBack = dialogProduitCallBack;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}