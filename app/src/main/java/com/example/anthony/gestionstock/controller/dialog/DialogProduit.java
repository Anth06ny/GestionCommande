package com.example.anthony.gestionstock.controller.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import greendao.Categorie;
import greendao.Produit;

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

        //Je récupère l'icon des ressources et je la change de couleur
        Drawable icon = getResources().getDrawable(R.drawable.ic_note_add_black_48dp);
        icon.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        builder.setIcon(icon);
        builder.setTitle(produit.getId() == null ? R.string.dialog_categorie_title_new : R.string.dialog_categorie_title_edit);

        //Initialisation de la liste de catégories
        listCategories = new ArrayList<>();
        listCategories = CategorieBddManager.getCategories();

        produitArrayList = new ArrayList<>();
        produitArrayList = (ArrayList<Produit>) ProduitBddManager.getProduit();

        // instancie l'adapteur pour la liste déroulante
        spinnerTest = new com.example.anthony.gestionstock.vue.SpinnerAdapter(this.getActivity(), listCategories);

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
                .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.annuler, null);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean canCloseDialog = true;
                ArrayList<Produit> produitArrayList;
                produitArrayList = (ArrayList<Produit>) ProduitBddManager.getProduit();
                if (StringUtils.isBlank(editNom.getText())) {
                    Toast.makeText(getContext(), R.string.dialog_produit_toast_erreur_nom, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isBlank(editPrix.getText())) {
                    Toast.makeText(getContext(), R.string.dialog_produit_toast_erreur_prix, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isBlank(editLot.getText())) {
                    Toast.makeText(getContext(), R.string.dialog_produit_toast_erreur_lot, Toast.LENGTH_SHORT).show();
                    return;
                }
                produit.setNom(editNom.getText().toString());

                for (int i = 0; i < produitArrayList.size(); i++) {
                    if (StringUtils.equalsIgnoreCase(produit.getNom(), produitArrayList.get(i).getNom()) && produit.getId() !=
                            produitArrayList.get(i).getId()) {

                        canCloseDialog = false;
                        Toast.makeText(getContext(), R.string.dialog_categorie_toast_erreur_nom_double, Toast.LENGTH_SHORT).show();
                    }
                }

                if (canCloseDialog) {
                    produit.setPrix(Float.valueOf(String.valueOf(editPrix.getText())));
                    produit.setLot(Integer.valueOf(String.valueOf(editLot.getText())));
                    produit.setCategorieID(categorieSelected.getId());
                    produit.setFavori(checkBoxFavori.isChecked());
                    dialogProduitCallBack.dialogProduitClicOnValider();
                    dialog.dismiss();
                }
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

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawableResource(R.color.bg_color);
    }

    public interface DialogProduitCallBack {
        void dialogProduitClicOnValider();
    }

    public void setDialogProduitCallBack(DialogProduitCallBack dialogProduitCallBack) {
        this.dialogProduitCallBack = dialogProduitCallBack;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}