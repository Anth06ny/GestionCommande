package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import vue.CategoryAdapter;
import vue.ProductAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentReglage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentReglage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReglage extends Fragment implements View.OnClickListener, CategoryAdapter.CategoryAdapterCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnAddCategorie;
    private Button btnAddProduit;
    private static final String tag = "fragment";
    private CategoryAdapter categoryAdapter;
    private ArrayList<Categorie> categorieList;
    private RecyclerView recyclerViewCategories;

    private ProductAdapter productAdapter;
    private ArrayList<Produit> produitList;
    private RecyclerView getRecyclerViewProduits;

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
        View v = inflater.inflate(R.layout.fragment_reglage, container, false);

        // Recupere la vue pour ce fragment
        initUI(v);

        return v;
    }

    // initUI permet de recupere les elements graphique et faire des operations sur ces elements
    private void initUI(View v) {
        categorieList = new ArrayList<Categorie>();
        categorieList = (ArrayList<Categorie>) CategorieBddManager.getCategories();
        categoryAdapter = new CategoryAdapter(categorieList, this, this.getContext(), v);
        recyclerViewCategories = (RecyclerView) v.findViewById(R.id.rv_categorie);
        recyclerViewCategories.setAdapter(categoryAdapter);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());

        btnAddCategorie = (Button) v.findViewById(R.id.btn_addCategorie);
        btnAddCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add Categorie", Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new DialogCategorie();
                newFragment.show(getFragmentManager(), tag);
            }
        });

        btnAddProduit = (Button) v.findViewById(R.id.btn_addProduit);
        btnAddProduit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add Produit", Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new DialogProduit();
                newFragment.show(getFragmentManager(), tag);
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

    @Override
    public void clicOnDeleteCallback(Categorie categorie) {
        //supp de green dao
        //la retirer de l'arrayList en recup sa position
        // categoryAdapter.notifyItemRemoved(position);
    }

    @Override
    public void clicOnModify(Categorie categorie) {
        DialogFragment newFragment = new DialogCategorie();

        Bundle args = new Bundle();
        args.putLong("id", categorie.getId());
        args.putString("nom", categorie.getNom());
        args.putString("couleur", categorie.getCouleur());
        args.putBoolean("choix", true);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), tag);
    }
}
