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
import android.widget.Toast;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;

import greendao.Produit;
import model.ProduitBddManager;
import vue.ProductAdapter;
import vue.ProductAffichageEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentStock.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentStock#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentStock extends Fragment implements ProductAdapter.ProductAdapterCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View v;
    private Button btnMettreZero;
    private Button btnMettreMax;
    private Button btnValiderStock;
    private RecyclerView recyclerViewStock;
    private ProductAdapter productAdapter;
    private ArrayList<Produit> produitArrayListStock;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentStock.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentStock newInstance(String param1, String param2) {
        FragmentStock fragment = new FragmentStock();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentStock() {
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

        v = inflater.inflate(R.layout.fragment_stock, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        btnMettreZero = (Button) v.findViewById(R.id.btn_mettre_zero);
        btnMettreMax = (Button) v.findViewById(R.id.btn_mettre_max);
        btnValiderStock = (Button) v.findViewById(R.id.btn_valider_stock);
        recyclerViewStock = (RecyclerView) v.findViewById(R.id.rv_stock);

        btnMettreZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMettreMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnValiderStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        produitArrayListStock = new ArrayList<>();
        produitArrayListStock = (ArrayList<Produit>) ProduitBddManager.getProduit();

        productAdapter = new ProductAdapter(ProductAffichageEnum.Stock, produitArrayListStock, this);
        recyclerViewStock.setAdapter(productAdapter);
        recyclerViewStock.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewStock.setItemAnimator(new DefaultItemAnimator());
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
    public void clicOnModifyOrInsertProduit(Produit produit) {

    }

    @Override
    public void clicOnProduit(Produit produit) {

    }

    @Override
    public void clicOnDeleteProduit(Produit produit) {

    }

    @Override
    public void clicOnProduitAcceuil(Produit produit) {

    }

    @Override
    public void clicOnDeleteProduitNote(Produit produit) {

    }

    @Override
    public void clicOnMinStock(Produit produit) {
        Toast.makeText(this.getContext(), "Min", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clicOnRemoveStock(Produit produit) {
        Toast.makeText(this.getContext(), "Remove", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clicOnAddStock(Produit produit) {
        Toast.makeText(this.getContext(), "Add", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clicOnMaxStock(Produit produit) {
        Toast.makeText(this.getContext(), "Max", Toast.LENGTH_SHORT).show();
    }
}
