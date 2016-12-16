package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.net.Uri;
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

import org.apache.commons.lang3.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import greendao.Commande;
import greendao.Consomme;
import greendao.Produit;
import com.example.anthony.gestionstock.model.CommandeBddManager;
import com.example.anthony.gestionstock.model.ConsommeBddManager;
import com.example.anthony.gestionstock.model.ProduitBddManager;
import com.example.anthony.gestionstock.vue.adapter.ProductAdapter;
import com.example.anthony.gestionstock.vue.ProductAffichageEnum;

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
    private AppCompatButton btnJour;
    private AppCompatButton btnSemaine;
    private AppCompatButton btnMois;
    private AppCompatButton btnAnnee;
    private AppCompatButton btnImprimer;
    private TextView textViewPrixTotal;
    private ArrayList<Produit> produitsArrayList;
    private TextView editDateDebut;
    private TextView editDateFin;
    private DatePickerFragment.DatePickerFragmentCallBack datePickerFragmentCallBack;
    private int choixDatePicker;
    private final int MOIS_FEVRIER = 02;
    private ArrayList<Commande> commandeArrayList;
    private ArrayList<Commande> commandeArrayListSelected;
    private ArrayList<Produit> produitArrayListSelected;
    private ArrayList<Consomme> consommeArrayList;
    private ArrayList<Long> idCommandes;
    private Date dateDebutSemaine;
    private Date dateFinSemaine;
    private HashMap<Produit, Long> quantiteHashMap;
    private float montantTotal;
    private Date dateDebutMois;
    private Date dateFinMois;
    private Date dateDebutAnnee;
    private Date dateFinAnnee;
    private Date dateDebut;
    private Date dateFin;
    private final String SYMBOLE_EURO = " €";

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
        //On recupere les elements graphique
        btnJour = (AppCompatButton) v.findViewById(R.id.btn_jour);
        btnSemaine = (AppCompatButton) v.findViewById(R.id.btn_semaine);
        btnMois = (AppCompatButton) v.findViewById(R.id.btn_mois);
        btnAnnee = (AppCompatButton) v.findViewById(R.id.btn_annee);
        btnImprimer = (AppCompatButton) v.findViewById(R.id.btn_imprimer_bilan);
        textViewPrixTotal = (TextView) v.findViewById(R.id.total_valeur);
        editDateDebut = (TextView) v.findViewById(R.id.champ_date_debut);
        editDateFin = (TextView) v.findViewById(R.id.champ_date_fin);

        //On recupere la liste de tous les produits
        produitsArrayList = new ArrayList<>();
        produitsArrayList = (ArrayList<Produit>) ProduitBddManager.getProduit();

        produitArrayListSelected = new ArrayList<>();
        quantiteHashMap = new HashMap<>();

        //On recupere le recycler view et on creer l'adapteur
        recyclerViewBilan = (RecyclerView) v.findViewById(R.id.rv_bilan);
        recyclerViewBilan.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewBilan.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(ProductAffichageEnum.Bilan, produitArrayListSelected, null, quantiteHashMap);
        recyclerViewBilan.setAdapter(productAdapter);
        datePickerFragmentCallBack = this;

        //Par defaut on affiche le bilan du jour
        affichageBilan(Calendar.getInstance().getTime(), Calendar.getInstance().getTime());

        //Initalisation des champs à la date du jour.
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        editDateDebut.setText(date);
        editDateFin.setText(date);

        commandeArrayList = new ArrayList<>();
        commandeArrayList = (ArrayList<Commande>) CommandeBddManager.getCommande();
        editDateDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouvre le date picker pour modifier la date du champ debut
                choixDatePicker = 0;
                launchDatePicker();
            }
        });

        editDateFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouvre le date picker pour modifier la date du champ fin
                choixDatePicker = 1;
                launchDatePicker();
            }
        });

        btnJour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouvre le date picker pour selectionner un jour en particulier
                choixDatePicker = 2;
                launchDatePicker();
            }
        });

        btnSemaine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Selectionne la semaine en cour
                choixDatePicker = 3;
                onSelectDate(null);
            }
        });

        btnMois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Selectionne le mois en cour
                choixDatePicker = 4;
                onSelectDate(null);
            }
        });

        btnAnnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Selectionne l'annee en cour
                choixDatePicker = 5;
                onSelectDate(null);
            }
        });

        btnImprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Gerer l'impression de tickets
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
    public void onSelectDate(Date date) {

        //Si la date passer en parametre est null on l'initialise par defaut a la date du jour
        if (date == null) {
            date = Calendar.getInstance().getTime();
        }

        quantiteHashMap = new HashMap<>();
        commandeArrayListSelected = new ArrayList<>();
        produitArrayListSelected = new ArrayList<>();

        switch (choixDatePicker) {
            case 0:
                //On recupere la date dans le champ date fin et on la format
                String dateTypageString = (String) editDateFin.getText();
                //Format de date à choisir
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                Date dateFin = null;
                try {
                    dateFin = dt.parse(dateTypageString);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                //On appel la methode affichage bilan avec comme parametre la date recuperer en entrer et la date qu'on recupere du champ
                affichageBilan(date, dateFin);
                break;

            case 1:
                //On recupere la date dans le champ date debut et on la format
                String dateTypageString2 = (String) editDateDebut.getText();
                //Format de date à choisir
                SimpleDateFormat dtDebut = new SimpleDateFormat("dd/MM/yyyy");
                Date dateDebut = null;
                try {
                    dateDebut = dtDebut.parse(dateTypageString2);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                //On appel la methode affichage bilan avec comme parametre la date recuperer en entrer et la date qu'on recupere du champ
                affichageBilan(dateDebut, date);
                break;

            case 2:
                //On appel la methode affichage bilan avec comme parametre la date recuperer en entrer
                affichageBilan(date, date);
                break;

            case 3:
                //On recupere la liste des dates de la semaine en cour
                ArrayList<Date> dateArrayListSemaine = getSemaine(date);
                dateDebutSemaine = new Date();
                dateDebutSemaine = dateArrayListSemaine.get(0);
                dateFinSemaine = new Date();
                dateFinSemaine = dateArrayListSemaine.get(dateArrayListSemaine.size() - 1);

                //On appel la methode affichage bilan avec comme parametre la date de debut de semaine et la date de fin de semaine
                affichageBilan(dateDebutSemaine, dateFinSemaine);

                break;
            case 4:
                //On recupere la liste des dates du mois en cour
                ArrayList<Date> dateArrayListMois = getMois(date);
                dateDebutMois = new Date();
                dateDebutMois = dateArrayListMois.get(0);
                dateFinMois = new Date();
                dateFinMois = dateArrayListMois.get(dateArrayListMois.size() - 1);

                //On appel la methode affichage bilan avec comme parametre la date de debut du mois et la date de fin du mois
                affichageBilan(dateDebutMois, dateFinMois);

                break;
            case 5:
                //On recupere la liste des dates de l'annee en cour
                ArrayList<Date> dateArrayListAnnee = getAnnee(date);
                dateDebutAnnee = new Date();
                dateDebutAnnee = dateArrayListAnnee.get(0);
                dateFinAnnee = new Date();
                dateFinAnnee = dateArrayListAnnee.get(dateArrayListAnnee.size() - 1);

                //On appel la methode affichage bilan avec comme parametre la date de debut d'annee et la date de fin d'annee
                affichageBilan(dateDebutAnnee, dateFinAnnee);
                break;
        }
    }

    public void launchDatePicker() {
        //Creer le dialog du date picker et l'affiche
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDatePickerFragmentCallBack(datePickerFragmentCallBack);
        datePickerFragment.show(getFragmentManager(), "Date");
    }

    //TODO faire ne sorte que la semaine commence tout le temps le LUNDI
    public static ArrayList<Date> getSemaine(Date date) {
        ArrayList<Date> dateArrayList = new ArrayList<>();
        String annee = new SimpleDateFormat("yyyy").format(date);
        String jour = new SimpleDateFormat("EEE").format(date);
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        int weekNo = c.get(Calendar.WEEK_OF_YEAR);
        c.set(Calendar.WEEK_OF_YEAR, weekNo);

        c.clear();

        c.set(Calendar.WEEK_OF_YEAR, weekNo);
        c.set(Calendar.YEAR, Integer.valueOf(annee));

        SimpleDateFormat formatter = new SimpleDateFormat("EEE dd/MM/yyyy");
        if ("lun.".equalsIgnoreCase(jour)) {
            c.add(Calendar.DATE, -1);
        }
        else {
            c.add(Calendar.DATE, 0);
        }

        for (int i = 0; i < 7; i++) {
            c.add(Calendar.DATE, 1);
            dateArrayList.add(c.getTime());
        }
        return dateArrayList;
    }

    public static ArrayList<Date> getMois(Date date) {
        ArrayList<Date> dateArrayList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 0);

        for (int i = 0; i < c.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            c.add(Calendar.DATE, 1);
            dateArrayList.add(c.getTime());
        }
        return dateArrayList;
    }

    public static ArrayList<Date> getAnnee(Date date) {
        ArrayList<Date> dateArrayList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, 0);

        for (int i = 0; i < c.getActualMaximum((Calendar.DAY_OF_YEAR)); i++) {
            c.add(Calendar.DATE, 1);
            dateArrayList.add(c.getTime());
        }
        return dateArrayList;
    }

    public void affichageBilan(Date dateDebut, Date dateFin) {

        //On recupere la liste des commande comprise entre les dates entrees en parametre
        commandeArrayListSelected = new ArrayList<>();
        commandeArrayListSelected = (ArrayList<Commande>) CommandeBddManager.getCommandeBetweenDate(dateDebut, dateFin);

        //On format les dates entrees en paramettre et on les affiche dans les champs date debut et date fin
        String fDateDebut = new SimpleDateFormat("dd/MM/yyyy").format(dateDebut);
        String fDateFin = new SimpleDateFormat("dd/MM/yyyy").format(dateFin);
        editDateDebut.setText(fDateDebut);
        editDateFin.setText(fDateFin);

        if (dateDebut.after(dateFin)) {
            Toast.makeText(getContext(), "La date de début est supérieur à la date de fin.", Toast.LENGTH_SHORT).show();
        }

        float montantTotal = 0;

        //On parcoure la liste des commandes comprise entre les dates entrees en parametre
        for (int i = 0; i < commandeArrayListSelected.size(); i++) {

            //On recupere la liste de tous les consommé de la bdd
            consommeArrayList = (ArrayList<Consomme>) ConsommeBddManager.getConsomme();

            //On parcours cette liste
            for (int j = 0; j < consommeArrayList.size(); j++) {

                //Si l'id de la commande de la liste consomme correspond a l'id de la commande a l'instant i
                if (ObjectUtils.equals(consommeArrayList.get(j).getCommande(), commandeArrayListSelected.get(i).getId())) {

                    //Alors on recupere l'id du produit qui correspond
                    Long produitId = consommeArrayList.get(j).getProduit();

                    //On parcours la liste de tous les produits
                    for (int k = 0; k < produitsArrayList.size(); k++) {

                        //Si l'id du produit recupere correspond a l'id du produit a l'instant k
                        if (ObjectUtils.equals(produitsArrayList.get(k).getId(), produitId)) {

                            //Si la hash map ne contient pas ce produit alors on lui ajoute avec la quantite qu'on recupere de la list consomme
                            //Et on ajoute le produit de l'instant k a la liste des produits selectionnees
                            if (!quantiteHashMap.containsKey(produitsArrayList.get(k))) {
                                quantiteHashMap.put(produitsArrayList.get(k), consommeArrayList.get(j).getQuantite());
                                produitArrayListSelected.add(produitsArrayList.get(k));
                            }
                            //Si la hash map contient ce produit alors on modifie sa quantite en recuperant l'ancienne valeur et en lui ajoutant la quantite
                            // recupere das la list consomme
                            else {
                                long quantite = quantiteHashMap.get(produitsArrayList.get(k));
                                quantite = quantite + consommeArrayList.get(j).getQuantite();
                                quantiteHashMap.put(produitsArrayList.get(k), quantite);
                            }
                        }
                    }
                }
            }
        }
        //On parcours la liste des produits selectionner, on recupere leur prix et leur quantites pour calculer le total d'un produit et mettre a jour le total de
        // tous les produits
        for (int i = 0; i < produitArrayListSelected.size(); i++) {
            montantTotal = montantTotal + (quantiteHashMap.get(produitArrayListSelected.get(i)) * produitArrayListSelected.get(i)
                    .getPrix());
        }
        textViewPrixTotal.setText(String.format("%s%s", montantTotal, SYMBOLE_EURO));

        productAdapter.notifyDataSetChanged();
    }
}
