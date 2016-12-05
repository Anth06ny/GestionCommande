package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;

import greendao.Categorie;
import greendao.Produit;
import model.CategorieBddManager;
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
    private Context context;

    private View v;
    private Button btn_cancel;
    private Button btn_note;
    private Button btn_off_client;
    private ProductAdapter productAdapter;
    private ArrayList<Categorie> categorieArrayList;
    private ArrayList<Produit> produitArrayList;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewProduits;
    private GridLayoutManager layoutManager;
    private ArrayList<Produit> produitArrayListFavoris;
    private Button btn_cat1;
    private Button btn_cat2;
    private Button btn_cat3;
    private Button btn_cat4;
    private Button btn_cat5;
    private Button btn_cat6;

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
        categorieArrayList = new ArrayList<Categorie>();
        //Remplissage de la liste
        categorieArrayList = (ArrayList<Categorie>) CategorieBddManager.getCategories();

        produitArrayList = new ArrayList<Produit>(); // Instanciation de la liste de produits
        produitArrayList = (ArrayList<Produit>) ProduitBddManager.getProduit(); // remplissage de la liste de produits
        for (int i = 0; i < produitArrayList.size(); i++) {
            if (produitArrayList.get(i).getFavori()) {
                produitArrayListFavoris.add(produitArrayList.get(i));
            }
        }

        //On instancie un grid layoutmanager qui prend en parametre le context et le nombre de colonne/ligne
        layoutManager = new GridLayoutManager(getContext(), 3);

        //on reucpere le recycler view
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_accueilProduit);

        // on passe le layout manager au recyclerview
        recyclerViewProduits.setLayoutManager(layoutManager);

        // instancie l'adpateur.
        productAdapter = new ProductAdapter(ProductAffichageEnum.Accueil, produitArrayList, this);

        // on passe l'adapter au recycler view
        recyclerViewProduits.setAdapter(productAdapter);

        btn_cancel = (Button) v.findViewById(R.id.btn_deleteNote);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });

        // Gestion des Boutons de CATEGORIES
        Button[] buttons = new Button[10];
        for (int j = 1; j <= 6; j++) {
            String buttonId = "btn_cat" + j;
            int resId = getResources().getIdentifier(buttonId, "id", "com.example.anthony.gestionstock");
            buttons[j] = ((Button) v.findViewById(resId));
            buttons[j].setVisibility(View.INVISIBLE);
            final int finalJ = j;
            buttons[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Clic " + finalJ, Toast.LENGTH_SHORT).show();
                }
            });
        }
        for (int k = 0; k < categorieArrayList.size(); k++) {
            buttons[k + 1].setText(categorieArrayList.get(k).getNom());
            buttons[k + 1].setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(categorieArrayList.get(k).getCouleur())));
            buttons[k + 1].setVisibility(View.VISIBLE);
        }
        //TODO récupérer les boutons et afficher les favoris
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
        testEnum(ProductAffichageEnum.Accueil);
    }

    public void testEnum(ProductAffichageEnum choixAffichage) {
        switch (choixAffichage) {

            case Note:
                break;
            case Accueil:
                break;
            case Reglage:
                break;
            case Bilan:
                break;
        }
    }

    @Override
    public void clicOnModifyProduit(Produit produit) {

    }

    @Override
    public void clicOnProduit(Produit produit) {

    }

    @Override
    public void clicOnDeleteProduit(Produit produit) {

    }

    public void addNameToButtonCategories(int index, Button button) {
        if (categorieArrayList.get(index).getNom().length() > 0) {
            button.setText(categorieArrayList.get(index).getNom());
            button.setVisibility(View.VISIBLE);
        }
        else {
            button.setVisibility(View.INVISIBLE);
        }
    }
}
