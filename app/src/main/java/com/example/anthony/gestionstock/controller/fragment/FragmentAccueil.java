package com.example.anthony.gestionstock.controller.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.Utils;
import com.example.anthony.gestionstock.controller.MyApplication;
import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.model.bdd.ConsommeBddManager;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;
import com.example.anthony.gestionstock.vue.AlertDialogutils;
import com.example.anthony.gestionstock.vue.ProductAffichageEnum;
import com.example.anthony.gestionstock.vue.adapter.CategoryAdapter;
import com.example.anthony.gestionstock.vue.adapter.ConsommeAdapter;
import com.example.anthony.gestionstock.vue.adapter.GridAutofitLayoutManager;
import com.example.anthony.gestionstock.vue.adapter.ProductAdapter;

import java.util.ArrayList;

import greendao.Categorie;
import greendao.Consomme;
import greendao.Produit;

public class FragmentAccueil extends Fragment implements View.OnClickListener, ProductAdapter.ProductAdapterCallBack, ConsommeAdapter.ConsommeAdapterCallBack, CategoryAdapter.CategoryAdapterCallBack, View.OnLongClickListener {

    //bouton
    private AppCompatButton btn_cancel;
    private AppCompatButton btn_note;
    private AppCompatButton btn_off_client;
    private TextView tv_solde;

    //GestionNote
    private ConsommeAdapter consommeAdapter;
    private ArrayList<Consomme> consommeArrayListNote;
    private RecyclerView recyclerViewNote;

    //Gestion Categorie
    private RecyclerView rv_accueilCategorie;
    private ArrayList<Categorie> categorieArrayList;
    private CategoryAdapter categoryAdapter;

    //Gestion Produit
    private ProductAdapter productAdapter;
    private ArrayList<Produit> arraylistProduits;
    private RecyclerView recyclerViewProduits;

    //Gestion ProduitFavorie
    private ProductAdapter productAdapterFavoris;
    private ArrayList<Produit> produitArrayListFavoris;
    private RecyclerView rv_accueilProduitFavoris;

    public FragmentAccueil() {
        // Required empty public constructor
    }

    /* ---------------------------------
    // Cycle vie fragment
    // -------------------------------- */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accueil, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {

        //RecyvclerView NOTE
        consommeArrayListNote = new ArrayList<>(); // Instanciation de la liste
        consommeAdapter = new ConsommeAdapter(consommeArrayListNote, this);
        recyclerViewNote = (RecyclerView) v.findViewById(R.id.rv_accueilNote);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNote.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNote.setAdapter(consommeAdapter);

        //RecyclerView Category
        categorieArrayList = (ArrayList<Categorie>) CategorieBddManager.getCategories();
        categoryAdapter = new CategoryAdapter(categorieArrayList, CategoryAdapter.CATEGORY_TYPE.ACCUEIL, this);
        rv_accueilCategorie = (RecyclerView) v.findViewById(R.id.rv_accueilCategorie);
        rv_accueilCategorie.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_accueilCategorie.setItemAnimator(new DefaultItemAnimator());
        rv_accueilCategorie.setAdapter(categoryAdapter);

        //RecycleView produit par defaut à vide
        arraylistProduits = new ArrayList<>();
        productAdapter = new ProductAdapter(ProductAffichageEnum.Accueil, arraylistProduits, this, null);
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_accueilProduit);//on reucpere le recycler view
        int bt_width = getResources().getDimensionPixelSize(R.dimen.accueil_bt_product_width);
        //Permet un calcul auto du nombre de colonne
        recyclerViewProduits.setLayoutManager(new GridAutofitLayoutManager(getContext(), bt_width));
        recyclerViewProduits.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProduits.setAdapter(productAdapter);

        //RecycleView produit Favoris
        produitArrayListFavoris = (ArrayList<Produit>) ProduitBddManager.getProduitFavoris(); // remplissage de la liste de produits
        productAdapterFavoris = new ProductAdapter(ProductAffichageEnum.Accueil, produitArrayListFavoris, this, null);
        rv_accueilProduitFavoris = (RecyclerView) v.findViewById(R.id.rv_accueilProduitFavoris);//on reucpere le recycler view
        rv_accueilProduitFavoris.setLayoutManager(new GridAutofitLayoutManager(getContext(), bt_width));
        rv_accueilProduitFavoris.setItemAnimator(new DefaultItemAnimator());
        rv_accueilProduitFavoris.setAdapter(productAdapterFavoris);

        //Autre
        btn_cancel = (AppCompatButton) v.findViewById(R.id.btn_deleteNote);
        btn_note = (AppCompatButton) v.findViewById(R.id.btn_printNote);
        btn_off_client = (AppCompatButton) v.findViewById(R.id.btn_offClient);
        tv_solde = (TextView) v.findViewById(R.id.tv_solde);

        btn_note.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_off_client.setOnClickListener(this);

        btn_cancel.setOnLongClickListener(this);
        btn_off_client.setOnLongClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshSold();
    }

    /* ---------------------------------
    // CallBack bouton
    // -------------------------------- */

    @Override
    public void onClick(View v) {
        //Vibration au clic
        Utils.vibration(getContext());

        if (v == btn_cancel) {
            AlertDialogutils.showOkCancelDialog(getContext(), "Confirmation", "Supprimer la note ?", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    deleteNote();
                    Toast.makeText(getContext(), R.string.accueil_tost_cmd_del, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (v == btn_off_client) {
            AlertDialogutils.showOkCancelDialog(getContext(), R.string.confirmation, R.string.accueil_confirm_message, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveCommande();
                }
            });
        }
        else if (v == btn_note) {
            Toast.makeText(getContext(), "Non implémenté", Toast.LENGTH_SHORT).show();
            AlertDialogutils.loginDialog(getContext());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        //Vibration au clic
        Utils.vibration(getContext());
        if (v == btn_cancel) {
            //le long clic efface la note sans confirmation
            deleteNote();
            Toast.makeText(getContext(), R.string.accueil_tost_cmd_del, Toast.LENGTH_SHORT).show();
            //True pour dire qu'on a traiter le long clic, sinon il va considerer que c'est aussi un clic classique
            return true;
        }
        else if (v == btn_off_client) {
            //le long clic valide sans confirmation
            saveCommande();
            //True pour dire qu'on a traiter le long clic, sinon il va considerer que c'est aussi un clic classique
            return true;
        }
        return false;
    }
    /* ---------------------------------
    // Callback ProductAdapter
    // -------------------------------- */



    @Override
    public void clicOnModifyOrInsertProduit(Produit produit) {
        //Non utilisée ici
    }

    @Override
    public void clicOnProduit(Produit produit) {
        //Vibration au clic
        Utils.vibration(getContext());
        addProduitToNote(produit.getId());
        refreshSold();
    }

    @Override
    public void clicOnDeleteProduit(Produit produit) {
        //non utilisée ici
    }


    /* ---------------------------------
    // CallBack ConsommeAdapter
    // -------------------------------- */

    @Override
    public void clicOnDeleteNote(Consomme consomme) {
        removeProduitToNote(consomme.getProduit());
    }

    /* ---------------------------------
    // CallBack CategoryAdapter
    // -------------------------------- */

    @Override
    public void clicOnModifyCategory(Categorie categorie) {
        //non utilisée ici
    }

    @Override
    public void clicOnCategory(Categorie categorie) {
        //Vibration au clic
        Utils.vibration(getContext());
        //on clic sur une category, on modifie la liste de produit et on actualise l'ecran
        arraylistProduits.clear();
        arraylistProduits.addAll(categorie.getProduitList());
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void clicOnDeleteCategory(Categorie categorie) {
        //non utilisée ici
    }
    /* ---------------------------------
    //  Private
    // -------------------------------- */

    private void refreshSold() {
        double solde = 0;
        for (Consomme consomme : consommeArrayListNote) {
            solde += consomme.getQuantite() * consomme.getProduitRef().getPrix();
        }

        tv_solde.setText(Utils.formatToMoney(solde) + " €");
    }

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
                consommeAdapter.notifyItemChanged(i);
                return;
            }
        }
        //Si on ne l'a pas trouvé on l'ajoute
        Consomme consomme = new Consomme(null, 1L, produitId, null);
        //On passe la daoSession car c'est nous qui construisons l'objet et que sinon
        //on ne pourra pas faire de getProduit avec (ou autre relation entre table dessus)
        //tant qu'on ne l'aura pas sauvegardé
        consomme.__setDaoSession(MyApplication.getDaoSession());
        consommeArrayListNote.add(0, consomme);
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

        refreshSold();
    }

    private void deleteNote() {
        consommeAdapter.notifyItemRangeRemoved(0, consommeArrayListNote.size());
        consommeArrayListNote.clear();
        refreshSold();
    }

    private void saveCommande() {
        if (consommeArrayListNote.isEmpty()) {
            Toast.makeText(getContext(), "Note vide", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //On parours la liste des consomme de la note
            for (int i = 0; i < consommeArrayListNote.size(); i++) {
                //On recupere le produit qui correspond a l'id du produit de la consomme a l'instant i
                Produit produit = ProduitBddManager.getProduitById(consommeArrayListNote.get(i).getProduit());

                //On set consommation en recuperant l'ancienne valeur de la consommation et en lui ajoutant la nouvelle qui est dans la liste de consomme
                produit.setConsommation((int) (produit.getConsommation() + consommeArrayListNote.get(i).getQuantite()));

                //On insert en bdd la modification de la consommation
                ProduitBddManager.insertOrUpdate(produit);
            }
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

    private void showError(Exception e) {
        AlertDialogutils.showOkDialog(getContext(), R.string.dialog_error_title, e.getMessage(), null);
    }
}
