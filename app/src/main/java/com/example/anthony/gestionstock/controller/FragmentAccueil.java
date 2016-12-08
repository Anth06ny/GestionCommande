package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;
import java.util.Calendar;

import greendao.Categorie;
import greendao.Commande;
import greendao.Consomme;
import greendao.Produit;
import model.CategorieBddManager;
import model.CommandeBddManager;
import model.ConsommeBddManager;
import model.ProduitBddManager;
import vue.ProductAdapter;
import vue.ProductAffichageEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAccueil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAccueil#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentAccueil extends Fragment implements View.OnClickListener, ProductAdapter.ProductAdapterCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final int NB_MAX_CATEGORIES = 6;
    private final int NB_MAX_FAVORIS = 6;
    private View v;
    private Button btn_cancel;
    private Button btn_note;
    private Button btn_off_client;
    private ProductAdapter productAdapter;
    private ProductAdapter productAdapterNote;
    private ArrayList<Categorie> categorieArrayList;
    private ArrayList<Produit> produitArrayList;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewProduits;
    private GridLayoutManager layoutManager;
    private ArrayList<Produit> produitArrayListFavoris;
    private ArrayList<Produit> arraylistProduits;
    private ProductAdapter.ProductAdapterCallBack productAdapterCallBack;
    private RecyclerView recyclerViewNote;
    private ArrayList<Produit> produitArrayListNote;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_accueil, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        //Création de la liste de catégories
        categorieArrayList = new ArrayList<>();
        //Remplissage de la liste
        categorieArrayList = (ArrayList<Categorie>) CategorieBddManager.getCategories();

        produitArrayList = new ArrayList<>(); // Instanciation de la liste de produits
        produitArrayList = (ArrayList<Produit>) ProduitBddManager.getProduit(); // remplissage de la liste de produits
        produitArrayListFavoris = new ArrayList<>();
        for (int i = 0; i < produitArrayList.size(); i++) {
            if (produitArrayList.get(i).getFavori()) {
                produitArrayListFavoris.add(produitArrayList.get(i));
            }
        }

        //RecyclerView PRODUIT
        layoutManager = new GridLayoutManager(getContext(), 3);//On instancie un grid layoutmanager qui prend en parametre le context et le nombre de colonne/ligne
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_accueilProduit);//on reucpere le recycler view
        recyclerViewProduits.setLayoutManager(layoutManager);// on passe le layout manager au recyclerview

        //RecyvclerView NOTE
        recyclerViewNote = (RecyclerView) v.findViewById(R.id.rv_accueilNote);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewNote.setItemAnimator(new DefaultItemAnimator());

        btn_cancel = (Button) v.findViewById(R.id.btn_deleteNote);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On creer un alert dialog pour confirmer la suppression du produit
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                //On set tous les elements et on display la dialog box
                alertDialogBuilder.setTitle("Confirmation");

                alertDialogBuilder
                        .setMessage("Voulez-vous annuler la commande ?");

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i = 0; i < produitArrayListNote.size(); i++) {
                                    for (int j = 0; j < produitArrayListNote.get(i).getProduitRef().size(); j++) {
                                        //Si Id commande est null, alors on se situe sur la commande en cours
                                        if (produitArrayListNote.get(i).getProduitRef().get(j).getCommande() == null) {
                                            produitArrayListNote.get(i).getProduitRef().get(j).setQuantite((long) 0);
                                        }
                                    }
                                }

                                produitArrayListNote.clear();
                                productAdapterNote.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        btn_note = (Button) v.findViewById(R.id.btn_printNote);
        btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_off_client = (Button) v.findViewById(R.id.btn_offClient);
        btn_off_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Commande commande = new Commande();

                //On creer un alert dialog pour confirmer la suppression du produit
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                //On set tous les elements et on display la dialog box
                alertDialogBuilder.setTitle("Confirmation");

                alertDialogBuilder
                        .setMessage("Voulez-vous terminer et valider la commande ?");

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO formattage date
              /*DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String date = df.format(Calendar.getInstance().getTime());*/
                                commande.setDate(Calendar.getInstance().getTime());
                                CommandeBddManager.insertOrUpdate(commande);

                                commandeArrayList = (ArrayList<Commande>) CommandeBddManager.getCommande();
                                int positionCommande = commandeArrayList.indexOf(commande);

                                for (int i = 0; i < produitArrayListNote.size(); i++) {
                                    for (int j = 0; j < produitArrayListNote.get(i).getProduitRef().size(); j++) {
                                        if (produitArrayListNote.get(i).getProduitRef().get(j).getCommande() == null) {
                                            produitArrayListNote.get(i).getProduitRef().get(j).setCommande(commandeArrayList.get(positionCommande).getId());
                                            ConsommeBddManager.insertOrUpdate(produitArrayListNote.get(i).getProduitRef().get(j));
                                        }
                                    }
                                }
                                produitArrayListNote.clear();
                                productAdapterNote.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        productAdapterCallBack = this; // Instanciation du CallBack
        produitArrayListNote = new ArrayList<>(); // Instanciation de la liste
        // Gestion des Boutons de CATEGORIES
        Button[] buttons = new Button[NB_MAX_CATEGORIES];
        for (int j = 0; j < NB_MAX_CATEGORIES; j++) {
            int idBtnCategories = j + 1;
            String buttonId = "btn_cat" + idBtnCategories;
            int resId = getResources().getIdentifier(buttonId, "id", "com.example.anthony.gestionstock");
            buttons[j] = ((Button) v.findViewById(resId));
            buttons[j].setVisibility(View.INVISIBLE);
            final int finalJ = j;
            buttons[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // rempli l'arrayListe avec les produits de la catégorie
                    arraylistProduits = (ArrayList<Produit>) categorieArrayList.get(finalJ).getProduitList();
                    // instancie l'adpateur.
                    productAdapter = new ProductAdapter(ProductAffichageEnum.Accueil, arraylistProduits, productAdapterCallBack);
                    // on passe l'adapter au recycler view
                    recyclerViewProduits.setAdapter(productAdapter);
                }
            });
        }
        for (int k = 0; k < categorieArrayList.size(); k++) {
            buttons[k].setText(categorieArrayList.get(k).getNom());
            buttons[k].setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(categorieArrayList.get(k).getCouleur())));
            buttons[k].setVisibility(View.VISIBLE);
        }
        //TODO conditions ajout produit dans la note

        //Gestion des boutons favoris
        Button[] buttonsFavoris = new Button[NB_MAX_FAVORIS]; // création du tableau de bouton
        for (int l = 0; l < NB_MAX_FAVORIS; l++) {
            int idBtnFavoris = l + 1;
            String buttonIdFavoris = "btn_prod_favori" + idBtnFavoris; // récupère en String la nomenclature de l'ID
            int resIdFavoris = getResources().getIdentifier(buttonIdFavoris, "id", "com.example.anthony.gestionstock");// Récupère la ressource R.id.btn_prod_favori+idBtnFavoris
            buttonsFavoris[l] = ((Button) v.findViewById(resIdFavoris)); // récupère le bouton
            buttonsFavoris[l].setVisibility(View.INVISIBLE);
            final int finalL = l;
            buttonsFavoris[l].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Consomme> consommeArrayList = (ArrayList<Consomme>) produitArrayListFavoris.get(finalL).getProduitRef(); /* getProduitRef renvoi une liste de
                    consomme par produit */

                    // consomme
                    Consomme consomme = new Consomme();
                    consomme.setProduit(produitArrayListFavoris.get(finalL).getId()); // on donne l'id du produit à l'objet consomme
                    int position = -1;
                    for (int i = 0; i < consommeArrayList.size(); i++) {
                        if (consommeArrayList.get(i).getCommande() == null) {
                            position = i;
                        }
                    }
                    if (position >= 0) {

                        consommeArrayList.get(position).setQuantite(consommeArrayList.get(position).getQuantite() + 1);
                    }
                    else {
                        consomme.setQuantite((long) 1);
                        consommeArrayList.add(consomme);
                    }

                    //On ajoute le produit favoris à la liste des produits de la note
                    int positionProduitNote;
                    positionProduitNote = produitArrayListNote.indexOf(produitArrayListFavoris.get(finalL));

                    if (productAdapterNote != null) {
                        if (positionProduitNote < 0) {
                            produitArrayListNote.add(produitArrayListFavoris.get(finalL));
                            productAdapterNote.notifyItemInserted(produitArrayListNote.size() - 1);
                        }
                        else {
                            productAdapterNote.notifyItemChanged(positionProduitNote);
                        }
                    }
                    else {
                        // On affiche la note dans le recycler
                        produitArrayListNote.add(produitArrayListFavoris.get(finalL));
                        productAdapterNote = new ProductAdapter(ProductAffichageEnum.Note, produitArrayListNote, productAdapterCallBack);
                        recyclerViewNote.setAdapter(productAdapterNote);
                    }
                }
            });
        }
        for (int m = 0; m < produitArrayListFavoris.size(); m++) {
            // Paramerage de l'affichage des boutons des produits favoris
            buttonsFavoris[m].setText(produitArrayListFavoris.get(m).getNom());
            buttonsFavoris[m].setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(produitArrayListFavoris.get(m).getCategorie().getCouleur())));
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
     * <p>
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

    }

    @Override
    public void clicOnModifyOrInsertProduit(Produit produit) {

    }

    @Override
    public void clicOnProduit(Produit produit) {

    }

    @Override
    public void clicOnDeleteProduit(Produit produit) {

    }

    public void clicOnProduitAcceuil(Produit produit) {
        ArrayList<Consomme> consommeArrayList = (ArrayList<Consomme>) produit.getProduitRef(); /* getProduitRef renvoi une liste de
                    consomme par produit */

        // consomme
        Consomme consomme = new Consomme();
        consomme.setProduit(produit.getId()); // on donne l'id du produit à l'objet consomme
        int position = -1;
        for (int i = 0; i < consommeArrayList.size(); i++) {
            if (consommeArrayList.get(i).getCommande() == null) {
                position = i;
            }
        }
        if (position >= 0) {

            consommeArrayList.get(position).setQuantite(consommeArrayList.get(position).getQuantite() + 1);
        }
        else {
            consomme.setQuantite((long) 1);
            consommeArrayList.add(consomme);
        }

        // On ajoute le produit sur lequel on clic dans la liste des produits de la note
        int positionProduitNote = produitArrayListNote.indexOf(produit);
        if (productAdapterNote != null) {
            if (positionProduitNote < 0) {
                produitArrayListNote.add(produit);
                productAdapterNote.notifyItemInserted(produitArrayListNote.size() - 1);
            }
            else {
                productAdapterNote.notifyItemChanged(positionProduitNote);
            }
        }
        else {
            produitArrayListNote.add(produit);
            productAdapterNote = new ProductAdapter(ProductAffichageEnum.Note, produitArrayListNote, productAdapterCallBack);
            recyclerViewNote.setAdapter(productAdapterNote);
        }
    }

    @Override
    public void clicOnDeleteProduitNote(Produit produit) {
        for (int i = 0; i < produit.getProduitRef().size(); i++) {
            if (produit.getProduitRef().get(i).getCommande() == null) {
                produit.getProduitRef().get(i).setQuantite((long) 0);
            }
        }
        int positionProduitNote = produitArrayListNote.indexOf(produit);
        produitArrayListNote.remove(positionProduitNote);
        productAdapterNote.notifyItemRemoved(positionProduitNote);
    }

    @Override
    public void clicOnMinStock(Produit produit) {

    }

    @Override
    public void clicOnRemoveStock(Produit produit) {

    }

    @Override
    public void clicOnAddStock(Produit produit) {

    }

    @Override
    public void clicOnMaxStock(Produit produit) {

    }
}
