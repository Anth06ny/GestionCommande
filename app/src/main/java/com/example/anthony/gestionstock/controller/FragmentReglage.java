package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;

import greendao.Categorie;
import greendao.Produit;
import model.CategorieBddManager;
import model.ProduitBddManager;
import vue.CategoryAdapter;
import vue.ProductAdapter;
import vue.ProductAffichageEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentReglage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentReglage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReglage extends Fragment implements View.OnClickListener, CategoryAdapter.CategoryAdapterCallBack, ProductAdapter.ProductAdapterCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnAddCategorie;
    private Button btnAddProduit;
    private static final String tag = "fragment";
    private CategoryAdapter categoryAdapter;
    private ArrayList<Categorie> categorieList;
    private ArrayList<Produit> produitList;
    private RecyclerView recyclerViewCategories;

    private ProductAdapter productAdapter;
    private RecyclerView recyclerViewProduits;

    private View v;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentReglage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentReglage newInstance(String param1, String param2) {
        FragmentReglage fragment = new FragmentReglage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentReglage() {
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
        v = inflater.inflate(R.layout.fragment_reglage, container, false);

        // Recupere la vue pour ce fragment
        initUI(v);

        return v;
    }

    // initUI permet de recupere les elements graphique et faire des operations sur ces elements
    private void initUI(View v) {
        categorieList = new ArrayList<Categorie>();
        categorieList = (ArrayList<Categorie>) CategorieBddManager.getCategories();
        categoryAdapter = new CategoryAdapter(categorieList, this);
        recyclerViewCategories = (RecyclerView) v.findViewById(R.id.rv_categorie);
        recyclerViewCategories.setAdapter(categoryAdapter);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());

        btnAddCategorie = (Button) v.findViewById(R.id.btn_addCategorie);

        btnAddCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On appel clicOnModifyProduit avec null en paramatre car il n'y a pas de categorie deja existante quand on ajoute une categorie
                clicOnModifyCategory(null);
            }
        });

        btnAddProduit = (Button) v.findViewById(R.id.btn_addProduit);
        btnAddProduit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clicOnModifyProduit(null);
            }
        });
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

    /////////////// Call Back Categorie ///////////////
    @Override
    public void clicOnModifyCategory(Categorie cat) {
        //On instancie la dialog Categorie
        DialogCategorie newFragment = new DialogCategorie();

        //On creer une categorie ou on recupere la categorie entrer en parametre
        final Categorie finalCategorie = cat == null ? new Categorie() : cat;

        //On envoie la categorie dans la dialog Categorie
        newFragment.setCategorie(finalCategorie);
        newFragment.setDialogCategorieCallBack(new DialogCategorie.DialogCategorieCallBack() {
            @Override
            public void dialogCategorieClicOnValider() {
                //On insert ou on update la categorie que l'on a ajouter ou modifier
                CategorieBddManager.insertOrUpdate(finalCategorie);

                //On recupere la position de cette categorie dans la liste des categories
                int positionCategorie = categorieList.indexOf(finalCategorie);
                if (positionCategorie >= 0) {
                    //Si la categorie est a une position de 0 ou plus c'est que la categorie existait deja dans la liste
                    //Donc on recharge le recycle view avec un item changed
                    categoryAdapter.notifyItemChanged(positionCategorie);
                }
                else {
                    //Sinon cela indique que la categorie n'appartenait pas a la liste
                    //Donc on insert la categorie dans la liste et on recharge le recycle view avec un item inserted
                    categorieList.add(finalCategorie);
                    categoryAdapter.notifyItemInserted(categorieList.size() - 1);
                }
            }

            @Override
            public void dialogCategorieClicOnValiderErreur(int tag) {
                //On creer un alert dialog pour indiquer que les valeurs saisie par l'utilisateur sont incorrect
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                //On set tous les elements et on display la dialog box
                alertDialogBuilder.setTitle("Erreur");
                switch (tag) {
                    case 0:
                        alertDialogBuilder
                                .setMessage("Erreur lors de l'envoie des données saisie veuillez réessayer");
                        break;
                    case 1:
                        alertDialogBuilder
                                .setMessage("Catégorie déjà existante");
                        break;
                    case 2:
                        alertDialogBuilder
                                .setMessage("Couleur déjà utiliser");
                        break;
                }

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        newFragment.show(getFragmentManager(), tag);
    }

    @Override
    public void clicOnCategory(Categorie categorie) {
        //Au clic sur une categorie on recupere la liste de produit qui lui est associer
        produitList = new ArrayList<>();
        produitList = (ArrayList<Produit>) categorie.getProduitList();

        //On set selected la categorie choisie a true et les autres a false pour display seulement les boutons de la categorie choisie
        for (int i = 0; i < categorieList.size(); i++) {
            categorieList.get(i).setSelected(false);
            if (categorieList.get(i) == categorie) {
                categorieList.get(i).setSelected(true);
            }
        }

        //On recharge le recycle view
        categoryAdapter.notifyDataSetChanged();

        //On creer l'adapteur et on set le recycler view des produits avec la liste de produits qui correpondent a la categorie choisie
        productAdapter = new ProductAdapter(ProductAffichageEnum.Reglage, produitList, this);
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_produit);
        recyclerViewProduits.setAdapter(productAdapter);
        recyclerViewProduits.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewProduits.setItemAnimator(new DefaultItemAnimator());
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void clicOnDeleteCategory(final Categorie categorie) {
        //On creer un alert dialog pour confirmer la suppression de la categorie
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        //On set tous les elements et on display la dialog box
        alertDialogBuilder.setTitle("Confirmation");

        alertDialogBuilder
                .setMessage("Voulez-vous supprimer cette catégorie ?");

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //On recupere la position de la categorie dans la liste qu'on veut supprimer
                        int positionCategorie = categorieList.indexOf(categorie);

                        //On recupere la liste des produits qui appartiennent a cette categorie
                        ArrayList<Produit> categorieProduitList = (ArrayList<Produit>) categorie.getProduitList();

                        //On fait une boucle pour supprimer de la liste et de la bdd tous les produits de cette categorie
                        while (categorieProduitList.size() > 0) {
                            ProduitBddManager.deleteProduit(categorieProduitList.get(0));
                            categorieProduitList.remove(0);
                        }

                        //On met a jour le recycle view
                        productAdapter.notifyDataSetChanged();

                        //On supprime la categorie de la liste de categorie et on recharche le recycle view
                        categorieList.remove(positionCategorie);
                        categoryAdapter.notifyItemRemoved(positionCategorie);

                        //On supprime la categorie de la bdd
                        CategorieBddManager.deleteCategorie(categorie);
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /////////////// Call Back Produit ///////////////
    @Override
    public void clicOnModifyProduit(final Produit produit) {
        //On instancie la dialog produit
        final DialogProduit newFragment = new DialogProduit();

        //On creer un produit ou on recupere le produit entrer en parametre
        final Produit finalProduit = produit == null ? new Produit() : produit;

        //On envoie la categorie dans la dialog Categorie
        newFragment.setProduit(finalProduit);
        newFragment.setDialogProduitCallBack(new DialogProduit.DialogProduitCallBack() {
            @Override
            public void dialogProduitClicOnValider() {
                ProduitBddManager.insertOrUpdate(finalProduit);
                Categorie categorieSelected = new Categorie();
                for (int i = 0; i < categorieList.size(); i++) {
                    if (categorieList.get(i).getId() == finalProduit.getCategorieID()) {
                        categorieSelected = categorieList.get(i);
                    }
                }

                if (categorieSelected.getProduitList() == produitList) {
                    if (produitList.size() != 0) {

                        int positionProduit = produitList.indexOf(finalProduit);
                        if (positionProduit >= 0) {
                            productAdapter.notifyItemChanged(positionProduit);
                        }
                        else {
                            produitList.add(finalProduit);
                            productAdapter.notifyItemInserted(produitList.size() - 1);
                        }
                    }
                    else {
                        produitList.add(finalProduit);
                        productAdapter.notifyItemInserted(produitList.size() - 1);
                    }
                }
            }

            @Override
            public void dialogProduitClicOnValiderErreur(int tag) {

                //On creer un alert dialog pour indiquer que les valeurs saisie par l 'utilisateur sont incorrect
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                //On set tous les elements et on display la dialog box
                alertDialogBuilder.setTitle("Erreur");
                switch (tag) {
                    case 0:
                        alertDialogBuilder
                                .setMessage("Erreur lors de l'envoie des données saisie veuillez réessayer");
                        break;
                    case 1:
                        alertDialogBuilder
                                .setMessage("Produit déjà existant");
                        break;
                }

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        newFragment.show(getFragmentManager(), tag);
    }

    @Override
    public void clicOnProduit(Produit produit) {

        //On set selected le produit choisi a true et les autres a false pour display seulement les boutons du produit choisi
        for (int i = 0; i < produitList.size(); i++) {
            produitList.get(i).setSelected(false);
            if (produitList.get(i) == produit) {
                produitList.get(i).setSelected(true);
            }
        }

        //On recharge le recycle view
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void clicOnDeleteProduit(final Produit produit) {
        //On creer un alert dialog pour confirmer la suppression du produit
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        //On set tous les elements et on display la dialog box
        alertDialogBuilder.setTitle("Confirmation");

        alertDialogBuilder
                .setMessage("Voulez-vous supprimer ce produit ?");

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //On recupere la position du produit qu'on veut delete dans la liste
                        int positionProduit = produitList.indexOf(produit);
                        //On retire le produit de la liste et on recharche le recycle view
                        produitList.remove(positionProduit);
                        productAdapter.notifyItemRemoved(positionProduit);
                        //On supprime le produit de la bdd
                        ProduitBddManager.deleteProduit(produit);
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
