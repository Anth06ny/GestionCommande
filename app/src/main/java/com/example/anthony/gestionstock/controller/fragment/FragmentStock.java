package com.example.anthony.gestionstock.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;
import com.example.anthony.gestionstock.vue.ProductAffichageEnum;
import com.example.anthony.gestionstock.vue.adapter.ProductAdapter;

import java.util.ArrayList;

import greendao.Produit;

public class FragmentStock extends Fragment implements View.OnClickListener {

    private View v;
    private AppCompatButton btnMettreZero;
    private AppCompatButton btnMettreMax;
    private AppCompatButton btnValiderStock;
    private RecyclerView recyclerViewStock;
    private ProductAdapter productAdapter;
    private ArrayList<Produit> produitArrayListStock;

    public FragmentStock() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_stock, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        //On recupere les elements graphique
        btnMettreZero = (AppCompatButton) v.findViewById(R.id.btn_mettre_zero);
        btnMettreMax = (AppCompatButton) v.findViewById(R.id.btn_mettre_max);
        btnValiderStock = (AppCompatButton) v.findViewById(R.id.btn_valider_stock);
        recyclerViewStock = (RecyclerView) v.findViewById(R.id.rv_stock);

        //On recupere la list des produit qui ont une consommation differente de 0 ou de null
        produitArrayListStock = (ArrayList<Produit>) ProduitBddManager.getProduitConsommation();

        //Bouton qui permet de mettre a 0 tous les lots recommandés
        btnMettreZero.setOnClickListener(this);

        //Bouton qui permet de mettre au maxipmun tous les lots recommandés
        btnMettreMax.setOnClickListener(this);

        //Bouton qui permet de mettre a jour la consommation du produit et d'envoyer les nouvelles valeurs en bdd
        btnValiderStock.setOnClickListener(this);

        productAdapter = new ProductAdapter(ProductAffichageEnum.Stock, produitArrayListStock, null, null);
        recyclerViewStock.setAdapter(productAdapter);
        recyclerViewStock.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewStock.setItemAnimator(new DefaultItemAnimator());
    }

    /* ---------------------------------
    // clic
    // -------------------------------- */

    @Override
    public void onClick(View v) {
        if (v == btnMettreZero) {
            for (Produit produit : produitArrayListStock) {
                //On set le lot recommande a 0 pour chaque produit de la liste
                produit.setLotRecommande(0);
            }
            productAdapter.notifyDataSetChanged();
        }
        else if (v == btnMettreMax) {
            for (Produit produit : produitArrayListStock) {
                produit.setLotRecommande(produit.getConsommation() / produit.getLot());
            }

            productAdapter.notifyDataSetChanged();
        }
        else if (v == btnValiderStock) {
            valider();
        }
    }

    /* ---------------------------------
    // Private
    // -------------------------------- */

    private void valider() {

        for (int i = 0; i < produitArrayListStock.size(); i++) {
            Produit produit = produitArrayListStock.get(i);
            if (produit.getLotRecommande() > 0) {
                //On met a jour la consommation, en recuperant l'ancienne valeur et en lui retirant la valeur du lot recommande multiplier par la taille
                // d'un lot
                produit.setConsommation(produit.getConsommation() - (produit.getLotRecommande() * produit.getLot()));

                //Si la consommation une fois mise a jour est toujours superieur a 0 alors on remet par defaut le lot recommande a 0 et on envoie le
                // produit en bdd
                if (produit.getConsommation() != 0) {
                    produit.setLotRecommande(0);
                    productAdapter.notifyItemChanged(i);
                    ProduitBddManager.insertOrUpdate(produit);
                }
                //Si la consommation une fois mise a jour est égal a 0 alors on remet on remet par defaut le lot recommande a 0, on envoie le
                // produit en bdd et on le retire de la liste des produits
                else {
                    produit.setLotRecommande(0);
                    ProduitBddManager.insertOrUpdate(produit);
                    produitArrayListStock.remove(produit);
                    productAdapter.notifyItemRemoved(i);
                }
            }
        }
    }
}
