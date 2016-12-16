package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import greendao.Produit;
import com.example.anthony.gestionstock.model.CategorieBddManager;
import com.example.anthony.gestionstock.model.ProduitBddManager;
import com.example.anthony.gestionstock.vue.AlertDialogutils;
import com.example.anthony.gestionstock.vue.ProductAffichageEnum;
import com.example.anthony.gestionstock.vue.adapter.CategoryAdapter;
import com.example.anthony.gestionstock.vue.adapter.ProductAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentReglage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentReglage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReglage extends Fragment implements View.OnClickListener, CategoryAdapter.CategoryAdapterCallBack, ProductAdapter.ProductAdapterCallBack,
        DialogLogin.DialogLoginCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatButton btnAddCategorie;
    private AppCompatButton btnAddProduit;
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

        //On creer un dialog pour que l'utilisateur puisse rentrer son mot de passe et acceder aux reglages
        DialogLogin dialogLogin = new DialogLogin();
        dialogLogin.setDialogLoginCallBack(FragmentReglage.this);
        dialogLogin.setCancelable(false);
        dialogLogin.show(getFragmentManager(), "tag");

        //On recupere la liste des categories
        categorieList = (ArrayList<Categorie>) CategorieBddManager.getCategories();

        //On recupere le recycler view des categories et on lui passe l'adapteur qui contient la liste de toutes les categories
        categoryAdapter = new CategoryAdapter(categorieList, CategoryAdapter.CATEGORY_TYPE.REGLAGE, this);
        recyclerViewCategories = (RecyclerView) v.findViewById(R.id.rv_categorie);
        recyclerViewCategories.setAdapter(categoryAdapter);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());

        produitList = new ArrayList<>();

        //On creer l'adapteur et on set le recycler view des produits avec la liste de produits qui correpondent a la categorie choisie quand on clic sur une
        // categorie
        productAdapter = new ProductAdapter(ProductAffichageEnum.Reglage, produitList, this, null);
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_produit);
        recyclerViewProduits.setAdapter(productAdapter);
        recyclerViewProduits.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerViewProduits.setItemAnimator(new DefaultItemAnimator());

        btnAddCategorie = (AppCompatButton) v.findViewById(R.id.btn_addCategorie);
        btnAddCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On appel clicOnModifyOrInsertCategorie avec null en paramatre car il n'y a pas de categorie deja existante quand on ajoute une categorie
                clicOnModifyCategory(null);
            }
        });

        btnAddProduit = (AppCompatButton) v.findViewById(R.id.btn_addProduit);
        btnAddProduit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //On appel clicOnModifyOrInsertProduit avec null en paramatre car il n'y a pas de produit deja existant quand on ajoute un produit
                clicOnModifyOrInsertProduit(null);
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
                String erreur;
                //On set tous les elements et on display la dialog box
                switch (tag) {
                    case 0:
                        erreur = "Erreur lors de l'envoie des données saisie veuillez réessayer";
                        break;
                    case 1:
                        erreur = "Catégorie déjà existante";
                        break;
                    case 2:
                        erreur = "Couleur déjà utilisé";
                        break;
                    case 3:
                        erreur = "Nombre maximal de catégorie atteint";
                        break;
                    default:
                        erreur = "Case inconnu";
                        break;
                }

                AlertDialogutils.showOkCancelDialog(getContext(), R.string.dialog_error_title, erreur, null);
            }
        });
        newFragment.show(getFragmentManager(), tag);
    }

    @Override
    public void clicOnCategory(Categorie categorie) {
        //TODO Impossible de mettre a jour la liste de produit a afficher si on est sur une autre que categorie que celle ou l'on insert le produit

        //Au clic sur une categorie on recupere la liste de produit qui lui est associer

        produitList.clear();

        categorie.resetProduitList();
        MyApplication.getDaoSession().clear();
        produitList.addAll(categorie.getProduitList());
        productAdapter.notifyDataSetChanged();

        // les autres categories a false pour display seulement les boutons de la categorie choisie
        Categorie oldCategorieATrue = null;
        for (Categorie c : categorieList) {
            if (c.isSelected()) {
                oldCategorieATrue = c;
            }
            c.setSelected(false);
        }
        //On set selected la categorie choisie a true
        categorie.setSelected(true);

        //On recharge le recycle view
        if (oldCategorieATrue != null) {
            categoryAdapter.notifyItemChanged(categorieList.indexOf(oldCategorieATrue));
        }
        categoryAdapter.notifyItemChanged(categorieList.indexOf(categorie));

        //TODO delete
        categoryAdapter.notifyDataSetChanged();
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
                        //On supprime la categorie de la bdd
                        CategorieBddManager.deleteCategorie(categorie);
                        //On supprime la categorie de la liste de categorie et on recharche le recycle view
                        categorieList.remove(positionCategorie);
                        categoryAdapter.notifyItemRemoved(positionCategorie);

                        produitList.clear();
                        //On met a jour le recycle view
                        productAdapter.notifyDataSetChanged();
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
    public void clicOnModifyOrInsertProduit(final Produit produit) {
        //On instancie la dialog produit
        final DialogProduit newFragment = new DialogProduit();

        //On creer un produit ou on recupere le produit entrer en parametre
        final Produit finalProduit = produit == null ? new Produit() : produit;

        //On envoie la categorie dans la dialog Categorie
        newFragment.setProduit(finalProduit);
        newFragment.setDialogProduitCallBack(new DialogProduit.DialogProduitCallBack() {
            @Override
            public void dialogProduitClicOnValider() {
                Categorie categorieSelected = null; //catégorie séléctionnée
                Categorie categorieToInsertProduit = null;//Catégorie ou on va inserer le produit
                for (int i = 0; i < categorieList.size(); i++) {
                    if (categorieList.get(i).getId() == finalProduit.getCategorieID()) {
                        categorieToInsertProduit = categorieList.get(i);
                    }
                    if (categorieList.get(i).isSelected()) {
                        categorieSelected = categorieList.get(i);
                    }
                }
                if (categorieToInsertProduit == null) {
                    Toast.makeText(getContext(), "Erreur à l'insertion du produit, categorie non trouvé", Toast.LENGTH_SHORT).show();
                }
                else {
                    //une insertion
                    if (finalProduit.getId() == null) {
                        finalProduit.setConsommation(0);
                        finalProduit.setLotRecommande(0);
                        ProduitBddManager.insertOrUpdate(finalProduit);
                        if (categorieSelected != null && categorieSelected.getId() == finalProduit.getCategorieID()) {
                            produitList.add(finalProduit);
                            productAdapter.notifyItemInserted(produitList.size() - 1);
                        }
                    }
                    else {
                        //une modification
                        if (finalProduit.getConsommation() == null) {
                            finalProduit.setConsommation(0);
                        }
                        finalProduit.setLotRecommande(0);
                        ProduitBddManager.insertOrUpdate(finalProduit);
                        productAdapter.notifyItemChanged(produitList.indexOf(finalProduit));
                    }
                    categorieToInsertProduit.resetProduitList();
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
                    case 2:
                        alertDialogBuilder.setMessage("Nombre de produit favoris maximal atteint");
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
        Produit oldProduitATrue = null;
        for (Produit p : produitList) {
            if (p.isSelected()) {
                oldProduitATrue = p;
            }
            p.setSelected(false);
        }
        produit.setSelected(true);

        //On recharge le recycle view
        productAdapter.notifyItemChanged(produitList.indexOf(produit));
        if (oldProduitATrue != null) {
            productAdapter.notifyItemChanged(produitList.indexOf(oldProduitATrue));
        }
        //TODO delete
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
                        productAdapter.notifyItemRemoved(produitList.indexOf(produit));
                        //On recupere la position du produit qu'on veut delete dans la liste
                        //On retire le produit de la liste et on recharche le recycle view
                        produitList.remove(produit);
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

    @Override
    public void onCancelLogin(Boolean cancel) {
        //Quand on clic sur le bouton annuler du dialog login on redirige l'utilisateur vers la page d'accueil
        FragmentAccueil fragmentAcceuil = new FragmentAccueil();

        //Lance un autre fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, fragmentAcceuil, "string")
                .addToBackStack(null)
                .commit();
    }
}
