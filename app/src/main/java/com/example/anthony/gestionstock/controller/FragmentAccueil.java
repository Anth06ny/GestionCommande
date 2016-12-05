package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private View v;
    private Button btn_cancel;
    private Button btn_note;
    private Button btn_off_client;
    private ProductAdapter productAdapter;
    private ArrayList<Categorie> categorieArrayList;
    private ArrayList<Produit> produitArrayList;
    private RecyclerView recyclerViewProduits;
    private OnFragmentInteractionListener mListener;
    private Button btn_cat1;
    private Button btn_cat2;
    private Button btn_cat3;
    private Button btn_cat4;
    private Button btn_cat5;
    private Button btn_cat6;
    private Button btn_cat7;

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

        btn_cat1 = (Button) v.findViewById(R.id.btn_cat1);
        btn_cat2 = (Button) v.findViewById(R.id.btn_cat2);
        btn_cat3 = (Button) v.findViewById(R.id.btn_cat3);
        btn_cat4 = (Button) v.findViewById(R.id.btn_cat4);
        btn_cat5 = (Button) v.findViewById(R.id.btn_cat5);
        btn_cat6 = (Button) v.findViewById(R.id.btn_cat6);
        btn_cat7 = (Button) v.findViewById(R.id.btn_cat7);

        btn_cat1.setOnClickListener(this);
        btn_cat2.setOnClickListener(this);
        btn_cat3.setOnClickListener(this);
        btn_cat4.setOnClickListener(this);
        btn_cat5.setOnClickListener(this);
        btn_cat6.setOnClickListener(this);
        btn_cat7.setOnClickListener(this);

        for (int i = 0; i < categorieArrayList.size(); i++) {
            String test = "btn_cat" + i;
        }

        produitArrayList = new ArrayList<Produit>(); // Instanciation de la liste de produits
        produitArrayList = (ArrayList<Produit>) ProduitBddManager.getProduit(); // remplissage de la liste de produits
        productAdapter = new ProductAdapter(ProductAffichageEnum.Accueil, produitArrayList, this); // instanciation de l'adpateur de produit
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_accueilProduit);
        recyclerViewProduits.setAdapter(productAdapter);
        recyclerViewProduits.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewProduits.setItemAnimator(new DefaultItemAnimator());

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

        if (v == btn_cat1) {

        }
        if (v == btn_cat2) {

        }
        if (v == btn_cat3) {

        }
        if (v == btn_cat4) {

        }
        if (v == btn_cat5) {

        }
        if (v == btn_cat6) {

        }
        if (v == btn_cat7) {

        }
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
}
