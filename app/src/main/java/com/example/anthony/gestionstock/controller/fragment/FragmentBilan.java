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
import android.widget.TextView;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.Utils;
import com.example.anthony.gestionstock.controller.DateUtils;
import com.example.anthony.gestionstock.model.bdd.CommandeBddManager;
import com.example.anthony.gestionstock.vue.ProductAffichageEnum;
import com.example.anthony.gestionstock.vue.adapter.ProductAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import greendao.Commande;
import greendao.Consomme;
import greendao.Produit;

public class FragmentBilan extends Fragment implements DatePickerFragment.DatePickerFragmentCallBack, View.OnClickListener {

    private static final int REQ_CODE_DATE_DEBUT = 1;   //Pour le datePicker
    private static final int REQ_CODE_DATE_FIN = 2;

    //Composant graphique
    private AppCompatButton btnJour;
    private AppCompatButton btnSemaine;
    private AppCompatButton btnMois;
    private AppCompatButton btnAnnee;
    private AppCompatButton btnImprimer;
    private RecyclerView recyclerViewBilan;
    private TextView textViewPrixTotal;
    private TextView editDateDebut;
    private TextView editDateFin;

    //Data
    private SimpleDateFormat simpleDateFormat;
    private ProductAdapter productAdapter;
    private Date dateDebut;
    private Date dateFin;
    private ArrayList<Produit> produitArrayList;
    private final String SYMBOLE_EURO = " €";

    public FragmentBilan() {
        // Required empty public constructor
    }

    /* ---------------------------------
    // Cycle de vie Fragment
    // -------------------------------- */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bilan, container, false);
        initUI(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.bilan_title);
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
        produitArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(ProductAffichageEnum.Bilan, produitArrayList, null);

        //On recupere le recycler view et on creer l'adapteur
        recyclerViewBilan = (RecyclerView) v.findViewById(R.id.rv_bilan);
        recyclerViewBilan.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewBilan.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBilan.setAdapter(productAdapter);

        //Par defaut date du jour
        dateDebut = new Date();
        dateFin = new Date();

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        editDateDebut.setOnClickListener(this);
        editDateFin.setOnClickListener(this);
        btnJour.setOnClickListener(this);
        btnSemaine.setOnClickListener(this);
        btnMois.setOnClickListener(this);
        btnAnnee.setOnClickListener(this);

        btnImprimer.setOnClickListener(this);

        refreshScreen();
    }

    /* ---------------------------------
    // Click
    // -------------------------------- */

    @Override
    public void onClick(View v) {
        if (v == editDateDebut) {
            launchDatePicker(REQ_CODE_DATE_DEBUT);
        }
        else if (v == editDateFin) {
            launchDatePicker(REQ_CODE_DATE_FIN);
        }
        else if (v == btnJour) {
            dateDebut = new Date();
            dateFin = new Date();
            refreshScreen();
        }
        else if (v == btnSemaine) {
            dateDebut = DateUtils.get1erJourSemaineEnCours();
            dateFin = new Date();
            refreshScreen();
        }
        else if (v == btnMois) {
            dateDebut = DateUtils.get1erJourMoisEnCours();
            dateFin = new Date();
            refreshScreen();
        }
        else if (v == btnAnnee) {
            dateDebut = DateUtils.get1erJourAnneeEnCours();
            dateFin = new Date();
            refreshScreen();
        }
    }

    /* ---------------------------------
    // CallBack datePicker
    // -------------------------------- */

    @Override
    public void onSelectDate(int reqCode, Date date) {

        //Si la date passer en parametre est null on l'initialise par defaut a la date du jour
        if (date == null) {
            return;
        }
        else if (reqCode == REQ_CODE_DATE_FIN) {
            dateFin = date;
            refreshScreen();
        }
        else if (reqCode == REQ_CODE_DATE_DEBUT) {
            dateDebut = date;
            refreshScreen();
        }
    }

    /* ---------------------------------
    // Private
    // -------------------------------- */
    private void launchDatePicker(int reqCode) {
        //Creer le dialog du date picker et l'affiche
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDatePickerFragmentCallBack(this);
        datePickerFragment.setReqCode(reqCode);
        datePickerFragment.show(getFragmentManager(), "Date");
    }

    /**
     * Actualise l'ecran en fonction de dateDebut et dateFin
     */
    private void refreshScreen() {
        //ON met à jour les champs de texte
        editDateDebut.setText(simpleDateFormat.format(dateDebut));
        editDateFin.setText(simpleDateFormat.format(dateFin));

        //ON vide la liste de produit
        HashMap<Long, Produit> produitHashMap = new HashMap<>();

        //On récupere la liste des commandes et des consommes sur la date
        List<Commande> commandes = CommandeBddManager.getCommandeBetweenDate(dateDebut, dateFin);
        //ON parcours cette liste et on ajoute à la hashmap
        for (Commande commande : commandes) {
            for (Consomme consomme : commande.getCommandeRef()) {
                //On cherche le produit dans la HashMaps
                Produit produit = produitHashMap.get(consomme.getProduit());
                //s'il n'y est pas on l'ajoute à la HashMap
                if (produit == null) {
                    produit = consomme.getProduitRef();
                    produit.setConsommation(0);
                    produitHashMap.put(produit.getId(), produit);
                }
                //On ajoute sa quantité
                produit.setConsommation((int) (produit.getConsommation() + consomme.getQuantite()));
            }
        }

        //On ajoute tous les produits à l'AL de produit
        produitArrayList.clear();
        produitArrayList.addAll(produitHashMap.values());
        //On trie la liste par nom
        Collections.sort(produitArrayList, new Comparator<Produit>() {
            @Override
            public int compare(Produit o1, Produit o2) {
                return o1.getNom().compareTo(o2.getNom());
            }
        });

        //On met à jour l'adapter
        productAdapter.notifyDataSetChanged();

        //On met à jour le solde total
        double montantTotal = 0;
        for (Produit p : produitArrayList) {
            montantTotal += p.getConsommation() * p.getPrix();
        }
        textViewPrixTotal.setText(Utils.formatToMoney(montantTotal) + SYMBOLE_EURO);
    }
}
