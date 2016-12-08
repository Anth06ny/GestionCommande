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
import android.widget.TextView;

import com.example.anthony.gestionstock.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import greendao.Commande;
import greendao.Consomme;
import greendao.Produit;
import model.CommandeBddManager;
import model.ProduitBddManager;
import vue.ProductAdapter;
import vue.ProductAffichageEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBilan.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBilan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBilan extends Fragment implements DatePickerFragment.DatePickerFragmentCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View v;
    private RecyclerView recyclerViewBilan;
    private ProductAdapter productAdapter;
    private Button btnJour;
    private Button btnSemaine;
    private Button btnMois;
    private Button btnAnnee;
    private Button btnImprimer;
    private TextView textViewPrixTotal;
    private ArrayList<Produit> produitArrayListBilan;
    private TextView editDateDebut;
    private TextView editDateFin;
    private DatePickerFragment.DatePickerFragmentCallBack datePickerFragmentCallBack;
    private int choixDatePicker;
    private final int MOIS_FEVRIER = 02;
    private ArrayList<Commande> commandeArrayList;
    private ArrayList<Commande> commandeArrayListSelected;
    private ArrayList<Produit> produitArrayListSelected;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBilan.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBilan newInstance(String param1, String param2) {
        FragmentBilan fragment = new FragmentBilan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBilan() {
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
        v = inflater.inflate(R.layout.fragment_bilan, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        btnJour = (Button) v.findViewById(R.id.btn_jour);
        btnSemaine = (Button) v.findViewById(R.id.btn_semaine);
        btnMois = (Button) v.findViewById(R.id.btn_mois);
        btnAnnee = (Button) v.findViewById(R.id.btn_annee);
        btnImprimer = (Button) v.findViewById(R.id.btn_imprimer_bilan);
        textViewPrixTotal = (TextView) v.findViewById(R.id.total_valeur);
        editDateDebut = (TextView) v.findViewById(R.id.champ_date_debut);
        editDateFin = (TextView) v.findViewById(R.id.champ_date_fin);

        produitArrayListBilan = new ArrayList<>();
        produitArrayListBilan = (ArrayList<Produit>) ProduitBddManager.getProduit();
        productAdapter = new ProductAdapter(ProductAffichageEnum.Bilan, produitArrayListBilan, null);

        recyclerViewBilan = (RecyclerView) v.findViewById(R.id.rv_bilan);
        recyclerViewBilan.setAdapter(productAdapter);
        recyclerViewBilan.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewBilan.setItemAnimator(new DefaultItemAnimator());
        datePickerFragmentCallBack = this;

        //Initalisation des champs à la date du jour.
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        editDateDebut.setText(date);
        editDateFin.setText(date);

        commandeArrayList = new ArrayList<>();
        commandeArrayList = (ArrayList<Commande>) CommandeBddManager.getCommande();
        btnJour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixDatePicker = 2;
                launchDatePicker();
            }
        });

        btnSemaine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixDatePicker = 3;
                launchDatePicker();
            }
        });

        btnMois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixDatePicker = 4;
                launchDatePicker();
            }
        });

        btnAnnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixDatePicker = 5;
                launchDatePicker();
            }
        });

        btnImprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixDatePicker = 0;
                launchDatePicker();
            }
        });

        editDateFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixDatePicker = 1;
                launchDatePicker();
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
    public void onSelectDate(Date date) {
        String fDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        commandeArrayListSelected = new ArrayList<>();
        produitArrayListSelected = new ArrayList<>();
        switch (choixDatePicker) {
            case 0:
                editDateDebut.setText(fDate);
                break;
            case 1:
                editDateFin.setText(fDate);
                break;
            case 2:
                editDateDebut.setText(fDate);
                editDateFin.setText(fDate);

                for (int i = 0; i < commandeArrayList.size(); i++) {
                    if (Objects.equals(new SimpleDateFormat("dd/MM/yyyy").format(commandeArrayList.get(i).getDate()), fDate)) {
                        commandeArrayListSelected.add(commandeArrayList.get(i));
                        ArrayList<Consomme> consommeArrayList = (ArrayList<Consomme>) commandeArrayList.get(i).getCommandeRef();
                        for (int j = 0; j < consommeArrayList.size(); j++) {
                            long produitId = commandeArrayList.get(i).getCommandeRef().get(j).getProduit();
                            for (int k = 0; k < produitArrayListBilan.size(); k++) {
                                if (produitArrayListBilan.get(k).getId() == produitId) {
                                    produitArrayListSelected.add(produitArrayListBilan.get(k));
                                }
                            }
                        }
                    }
                }
                productAdapter = new ProductAdapter(ProductAffichageEnum.Bilan, produitArrayListSelected, null);
                recyclerViewBilan.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

                break;
            case 3:
                editDateDebut.setText(fDate);
                editDateFin.setText(fDate);
                String fDateJour = new SimpleDateFormat("dd").format(date);
                String fDateMoisSelect = new SimpleDateFormat("MM").format(date);
                String fDateAnneeSelectSemaine = new SimpleDateFormat("yyyy").format(date);

                int calculSemaine = Integer.valueOf(fDateJour) + 6;
                if (Integer.valueOf(fDateMoisSelect) != MOIS_FEVRIER) {
                    if (Integer.valueOf(fDateMoisSelect) % 2 == 0) {
                        if (calculSemaine > 30) {
                            int reste = calculSemaine - 31;
                            String resteFormat = "0" + String.valueOf(reste);
                            if (Integer.valueOf(fDateMoisSelect) == 12) {
                                String annee = String.valueOf(Integer.valueOf(fDateAnneeSelectSemaine) + 1);

                                editDateFin.setText(resteFormat + "/01/" + annee);
                            }
                            else {
                                String mois = String.valueOf(Integer.valueOf(fDateMoisSelect) + 1);
                                editDateFin.setText(resteFormat + "/" + mois + "/" + fDateAnneeSelectSemaine);
                            }
                        }
                        else {
                            editDateFin.setText(calculSemaine + "/" + fDateMoisSelect + "/" + fDateAnneeSelectSemaine);
                        }
                    }
                    else {
                        if (calculSemaine > 31) {
                            int reste = calculSemaine - 30;
                            String resteFormat = "0" + String.valueOf(reste);
                            editDateFin.setText(resteFormat + "/" + fDateMoisSelect + "/" + fDateAnneeSelectSemaine);
                        }
                        else {
                            editDateFin.setText(calculSemaine + "/" + fDateMoisSelect + "/" + fDateAnneeSelectSemaine);
                        }
                    }
                }
                else {

                }

                break;
            case 4:
                String fDateMois = new SimpleDateFormat("MM").format(date);
                String fDateAnnee = new SimpleDateFormat("yyyy").format(date);

                editDateDebut.setText("01/" + fDateMois + "/" + fDateAnnee);

                if (Integer.valueOf(fDateMois) != MOIS_FEVRIER) {
                    if (Integer.valueOf(fDateMois) % 2 == 0) {
                        editDateFin.setText("30/" + fDateMois + "/" + fDateAnnee);
                    }
                    else {
                        editDateFin.setText("31/" + fDateMois + "/" + fDateAnnee);
                    }
                }
                else {
                    editDateFin.setText("29/" + fDateMois + "/" + fDateAnnee);
                }
                break;
            case 5:
                String fDateSelectAnnee = new SimpleDateFormat("yyyy").format(date);
                editDateDebut.setText(String.format("01/01/%s", fDateSelectAnnee));
                editDateFin.setText(String.format("31/12/%s", fDateSelectAnnee));
                break;
        }
    }

    public void launchDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDatePickerFragmentCallBack(datePickerFragmentCallBack);
        datePickerFragment.show(getFragmentManager(), "Date fin");
    }
}
