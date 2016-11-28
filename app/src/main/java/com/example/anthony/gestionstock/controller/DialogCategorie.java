package com.example.anthony.gestionstock.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.List;

import greendao.Categorie;
import greendao.CategorieDao;
import greendao.DaoMaster;
import greendao.DaoSession;

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
    // Dao --> Data Acces Object
    private CategorieDao categorieDao; // Sql acces object
    List<String> mResult = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_categorie, null);

        //Initalise la DAO
        categorieDao = setupDB();

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
                        Categorie catTest = new Categorie(null, categorieTest.getNom(), categorieTest.getCouleur(), null); // Class object, Id est auto incrémenté
                        SaveToSQL(catTest);
                        generateResult();
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

    // Récupère la liste des catégories présente en BDD
    public void generateResult() {
        List<Categorie> categoriesList = getFromSQL();
        int size = categoriesList.size();

        if (size > 0) {
            mResult.clear();
            for (int i = 0; i < size; i++) {
                Categorie currentItem = categoriesList.get(i);
                mResult.add(0, currentItem.getId() + "," + currentItem.getNom() + "," + currentItem.getCouleur());
                Log.v("test1", currentItem.getId() + "," + currentItem.getNom() + "," + currentItem.getCouleur());
            }
        }
    }

    // --------------------------------------  DB Setup Function ------------------------------------------- //
    //Return the Configured LogDao Object
    public CategorieDao setupDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.getContext(), "appli.db", null);//Création de la base de donnée fichier db si il existe pas
        SQLiteDatabase db = helper.getWritableDatabase(); // récupère  la base de donnée crée
        DaoMaster master = new DaoMaster(db); // creation de de masterDao
        DaoSession masterSession = master.newSession(); // création de la Session session
        return masterSession.getCategorieDao();
    }

    // --------------------------------------- '''' END DB Setup Function '''' --------------------- --- //

    // ------------------------------------ '' SQL QUERY Fnctions ''' -------------------------------- //

    public List<Categorie> getFromSQL() {
        List<Categorie> categories = categorieDao.queryBuilder().orderDesc(CategorieDao.Properties.Id).build().list();
        return categories;
    }

    public void SaveToSQL(Categorie categorie) {
        categorieDao.insert(categorie);
    }

    // --------------------------------- ''' END SQL QUERY ''' -----------------------------------//
}
