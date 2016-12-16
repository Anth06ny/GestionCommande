package com.example.anthony.gestionstock.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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

public class FragmentStock extends Fragment {
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
        produitArrayListStock = new ArrayList<>();
        produitArrayListStock = (ArrayList<Produit>) ProduitBddManager.getProduitConsommation();

        //Bouton qui permet de mettre a 0 tous les lots recommandés
        btnMettreZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < produitArrayListStock.size(); i++) {
                    //On set le lot recommande a 0 pour chaque produit de la liste
                    produitArrayListStock.get(i).setLotRecommande(0);
                }
                productAdapter.notifyDataSetChanged();
            }
        });

        //Bouton qui permet de mettre au maxipmun tous les lots recommandés
        btnMettreMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < produitArrayListStock.size(); i++) {
                    //On set le lot recommande au maximun en recuperant la consommation diviser par la taille d'un lot, pour chaque produit de la liste
                    produitArrayListStock.get(i).setLotRecommande(produitArrayListStock.get(i).getConsommation() / produitArrayListStock.get(i).getLot());
                }
                productAdapter.notifyDataSetChanged();
            }
        });

        //Bouton qui permet de mettre a jour la consommation du produit et d'envoyer les nouvelles valeurs en bdd
        btnValiderStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < produitArrayListStock.size(); i++) {
                    //On parcours la liste de produit et on verifie si le lot recommande est different de 0
                    if (produitArrayListStock.get(i).getLotRecommande() > 0) {
                        //On met a jour la consommation, en recuperant l'ancienne valeur et en lui retirant la valeur du lot recommande multiplier par la taille
                        // d'un lot
                        produitArrayListStock.get(i).setConsommation(produitArrayListStock.get(i).getConsommation() - (produitArrayListStock.get(i).getLotRecommande
                                () * produitArrayListStock.get(i).getLot()));

                        //Si la consommation une fois mise a jour est toujours superieur a 0 alors on remet par defaut le lot recommande a 0 et on envoie le
                        // produit en bdd
                        if (produitArrayListStock.get(i).getConsommation() != 0) {
                            produitArrayListStock.get(i).setLotRecommande(0);
                            productAdapter.notifyDataSetChanged();
                            ProduitBddManager.insertOrUpdate(produitArrayListStock.get(i));
                        }

                        //Si la consommation une fois mise a jour est égal a 0 alors on remet on remet par defaut le lot recommande a 0, on envoie le
                        // produit en bdd et on le retire de la liste des produits
                        else {
                            produitArrayListStock.get(i).setLotRecommande(0);
                            ProduitBddManager.insertOrUpdate(produitArrayListStock.get(i));
                            produitArrayListStock.remove(i);
                            productAdapter.notifyItemRemoved(i);
                        }
                    }
                }
            }
        });

        productAdapter = new ProductAdapter(ProductAffichageEnum.Stock, produitArrayListStock, null, null);
        recyclerViewStock.setAdapter(productAdapter);
        recyclerViewStock.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewStock.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.stock_title);
    }
}
