package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;

import greendao.Categorie;
import greendao.Commande;
import greendao.Consomme;
import greendao.Produit;
import model.CategorieBddManager;
import model.ConsommeBddManager;
import model.ProduitBddManager;
import vue.AlertDialogutils;
import vue.ProductAffichageEnum;
import vue.adapter.ConsommeAdapter;
import vue.adapter.ProductAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAccueil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAccueil#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentAccueil extends Fragment implements View.OnClickListener, ProductAdapter.ProductAdapterCallBack, ConsommeAdapter.ConsommeAdapterCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters

    private final int NB_MAX_CATEGORIES = 6;
    private final int NB_MAX_FAVORIS = 6;
    private AppCompatButton btn_cancel;
    private AppCompatButton btn_note;
    private AppCompatButton btn_off_client;
    //GestionNote
    private ConsommeAdapter consommeAdapter;
    private ArrayList<Consomme> consommeArrayListNote;
    private RecyclerView recyclerViewNote;

    private ProductAdapter productAdapter;
    private ArrayList<Categorie> categorieArrayList;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewProduits;
    private ArrayList<Produit> produitArrayListFavoris;
    private ArrayList<Produit> arraylistProduits;

    private ArrayList<Commande> commandeArrayList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAccueil.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAccueil newInstance(String param1, String param2) {
        FragmentAccueil fragment = new FragmentAccueil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentAccueil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accueil, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        //Création de la liste de catégories
        //Remplissage de la liste
        categorieArrayList = (ArrayList<Categorie>) CategorieBddManager.getCategories();
        produitArrayListFavoris = (ArrayList<Produit>) ProduitBddManager.getProduitFavoris(); // remplissage de la liste de produits

        //RecyclerView PRODUIT
        //On instancie un grid layoutmanager qui prend en parametre le context et le nombre de colonne/ligne
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_accueilProduit);//on reucpere le recycler view
        recyclerViewProduits.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewProduits.setItemAnimator(new DefaultItemAnimator());

        //RecyvclerView NOTE
        consommeArrayListNote = new ArrayList<>(); // Instanciation de la liste
        consommeAdapter = new ConsommeAdapter(consommeArrayListNote, this);
        recyclerViewNote = (RecyclerView) v.findViewById(R.id.rv_accueilNote);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNote.setItemAnimator(new DefaultItemAnimator());

        btn_cancel = (AppCompatButton) v.findViewById(R.id.btn_deleteNote);
        btn_note = (AppCompatButton) v.findViewById(R.id.btn_printNote);
        btn_off_client = (AppCompatButton) v.findViewById(R.id.btn_offClient);

        btn_note.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_off_client.setOnClickListener(this);

        // Gestion des Boutons de CATEGORIES
        AppCompatButton[] buttons = new AppCompatButton[NB_MAX_CATEGORIES];
        for (int j = 0; j < NB_MAX_CATEGORIES; j++) {
            int idBtnCategories = j + 1;
            String buttonId = "btn_cat" + idBtnCategories;
            int resId = getResources().getIdentifier(buttonId, "id", "com.example.anthony.gestionstock");
            buttons[j] = ((AppCompatButton) v.findViewById(resId));
            buttons[j].setVisibility(View.INVISIBLE);
            final int finalJ = j;
            buttons[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // rempli l'arrayListe avec les produits de la catégorie
                    arraylistProduits = (ArrayList<Produit>) categorieArrayList.get(finalJ).getProduitList();
                    // instancie l'adpateur.
                    productAdapter = new ProductAdapter(ProductAffichageEnum.Accueil, arraylistProduits, FragmentAccueil.this, null);
                    // on passe l'adapter au recycler view
                    recyclerViewProduits.setAdapter(productAdapter);
                }
            });
        }
        for (int k = 0; k < categorieArrayList.size(); k++) {
            buttons[k].setText(categorieArrayList.get(k).getNom());
            buttons[k].setSupportBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(categorieArrayList.get(k).getCouleur())));
            buttons[k].setVisibility(View.VISIBLE);
        }
        //TODO conditions ajout produit dans la note

        //Gestion des boutons favoris
        AppCompatButton[] buttonsFavoris = new AppCompatButton[NB_MAX_FAVORIS]; // création du tableau de bouton
        for (int l = 0; l < NB_MAX_FAVORIS; l++) {
            int idBtnFavoris = l + 1;
            String buttonIdFavoris = "btn_prod_favori" + idBtnFavoris; // récupère en String la nomenclature de l'ID
            int resIdFavoris = getResources().getIdentifier(buttonIdFavoris, "id", "com.example.anthony.gestionstock");// Récupère la ressource R.id.btn_prod_favori+idBtnFavoris
            buttonsFavoris[l] = ((AppCompatButton) v.findViewById(resIdFavoris)); // récupère le bouton
            buttonsFavoris[l].setVisibility(View.INVISIBLE);
            final int finalL = l;
            buttonsFavoris[l].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v.getTag() == null) {
                        Toast.makeText(getContext(), "Le bouton " + v + " n'a pas de tag", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addProduitToNote((Long) v.getTag());
                    }
                }
            });
        }
        for (int m = 0; m < produitArrayListFavoris.size(); m++) {
            // Paramerage de l'affichage des boutons des produits favoris
            buttonsFavoris[m].setText(produitArrayListFavoris.get(m).getNom());
            //TODO modif Anthony à tester
            buttonsFavoris[m].setSupportBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(produitArrayListFavoris.get(m).getCategorie().getCouleur())));
            //TODO fin modif
            buttonsFavoris[m].setVisibility(View.VISIBLE);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cancel) {
            AlertDialogutils.showOkCancelDialog(getContext(), "Confirmation", "Supprimer la note ?", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    deleteNote();
                    Toast.makeText(getContext(), R.string.accueil_tost_cmd_del, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (v == btn_off_client) {
            saveCommande();
        }
        else if (v == btn_note) {
            Toast.makeText(getContext(), "Non implémenté", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clicOnModifyOrInsertProduit(Produit produit) {

    }

    @Override
    public void clicOnProduit(Produit produit) {
        addProduitToNote(produit.getId());
    }

    @Override
    public void clicOnDeleteProduit(Produit produit) {
        removeProduitToNote(produit.getId());
    }

    @Override
    public void clicOnDeleteNote(Consomme consomme) {
        removeProduitToNote(consomme.getProduit());
    }

    /* ---------------------------------
    //  Private
    // -------------------------------- */

    /**
     * Ajoute à la note le produit en param
     *
     * @param produitId
     */
    private void addProduitToNote(Long produitId) {
        if (produitId == null) {
            Toast.makeText(getContext(), "produitId a null", Toast.LENGTH_SHORT).show();
            return;
        }
        //On cherche dans l'arrayList si un consomme correspond au produit
        for (int i = 0; i < consommeArrayListNote.size(); i++) {
            Consomme consomme = consommeArrayListNote.get(i);
            if (consomme.getProduit() == produitId) {
                //on ajoute 1 à la quantité
                consomme.setQuantite(consomme.getQuantite() + 1);
                consommeAdapter.notifyItemInserted(0);
                return;
            }
        }
        //Si on ne l'a pas trouvé on l'ajoute
        consommeArrayListNote.add(0, new Consomme(null, 1L, produitId, null));
        consommeAdapter.notifyItemInserted(0);
    }

    private void removeProduitToNote(Long produitId) {
        if (produitId == null) {
            Toast.makeText(getContext(), "produitId a null", Toast.LENGTH_SHORT).show();
            return;
        }
        //On cherche dans l'arrayList si un consomme correspond au produit
        for (int i = 0; i < consommeArrayListNote.size(); i++) {
            Consomme consomme = consommeArrayListNote.get(i);
            if (consomme.getProduit() == produitId) {
                //on ajoute 1 à la quantité
                consomme.setQuantite(consomme.getQuantite() - 1);
                if (consomme.getQuantite() == 0) {
                    consommeArrayListNote.remove(i);
                    consommeAdapter.notifyItemRemoved(i);
                }
                else {
                    consommeAdapter.notifyItemChanged(i);
                }
                return;
            }
        }
    }

    private void deleteNote() {
        consommeAdapter.notifyItemRangeRemoved(0, consommeArrayListNote.size() - 1);
        consommeArrayListNote.clear();
    }

    private void saveCommande() {

        AlertDialogutils.showOkCancelDialog(getContext(), R.string.confirmation, R.string.accueil_confirm_message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (consommeArrayListNote.isEmpty()) {
                    Toast.makeText(getContext(), "Note vide", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    //On insert en base
                    ConsommeBddManager.insertConsommeList(consommeArrayListNote);
                    //Si l'insertion a reussi on efface la note
                    deleteNote();
                    Toast.makeText(getContext(), R.string.accueil_tost_cmd_save, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    showError(e);
                }
            }
        });
    }

    private void showError(Exception e) {
        AlertDialogutils.showOkDialog(getContext(), R.string.dialog_error_title, e.getMessage(), null);
    }
}
