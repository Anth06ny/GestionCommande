package com.example.anthony.gestionstock.controller.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.controller.DateBean;
import com.example.anthony.gestionstock.controller.MyApplication;
import com.example.anthony.gestionstock.controller.dialog.DialogCategorie;
import com.example.anthony.gestionstock.controller.dialog.DialogHistoriqueDate;
import com.example.anthony.gestionstock.controller.dialog.DialogProduit;
import com.example.anthony.gestionstock.model.bdd.CategorieBddManager;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;
import com.example.anthony.gestionstock.model.webservice.WSUtils;
import com.example.anthony.gestionstock.vue.AlertDialogutils;
import com.example.anthony.gestionstock.vue.ProductAffichageEnum;
import com.example.anthony.gestionstock.vue.adapter.CategoryAdapter;
import com.example.anthony.gestionstock.vue.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.Date;

import greendao.Categorie;
import greendao.Produit;

public class FragmentReglage extends Fragment implements View.OnClickListener, CategoryAdapter.CategoryAdapterCallBack, ProductAdapter.ProductAdapterCallBack, DialogHistoriqueDate.DialogHistoriqueDateCallBack {

    private AppCompatButton btnAddCategorie;
    private AppCompatButton btnAddProduit;
    private static final String tag = "fragment";
    private CategoryAdapter categoryAdapter;
    private ArrayList<Categorie> categorieList;
    private ArrayList<Produit> produitList;
    private RecyclerView recyclerViewCategories;

    private ProductAdapter productAdapter;
    private RecyclerView recyclerViewProduits;
    private View v;
    private DialogHistoriqueDate dialogHistoriqueDate = new DialogHistoriqueDate();

    public FragmentReglage() {
        // Required empty public constructor
    }

    /* ---------------------------------
    // Cycle Vie Fragment
    // -------------------------------- */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reglage, container, false);

        // Recupere la vue pour ce fragment
        initUI(v);

        return v;
    }

    // initUI permet de recupere les elements graphique et faire des operations sur ces elements
    private void initUI(View v) {

        //Sans cette ligne la méthode de création du menu n'est pas appelé
        setHasOptionsMenu(true);

        //On recupere la liste des categories
        categorieList = (ArrayList<Categorie>) CategorieBddManager.getCategories();

        //On recupere le recycler view des categories et on lui passe l'adapteur qui contient la liste de toutes les categories
        categoryAdapter = new CategoryAdapter(categorieList, CategoryAdapter.CATEGORY_TYPE.REGLAGE, this);
        recyclerViewCategories = (RecyclerView) v.findViewById(R.id.rv_categorie);
        recyclerViewCategories.setAdapter(categoryAdapter);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());

        produitList = new ArrayList<>();

        //On creer l'adapteur et on set le recycler view des produits avec la liste de produits qui correpondent a la categorie choisie quand on clic sur une
        // categorie
        productAdapter = new ProductAdapter(ProductAffichageEnum.Reglage, produitList, this);
        recyclerViewProduits = (RecyclerView) v.findViewById(R.id.rv_produit);
        recyclerViewProduits.setAdapter(productAdapter);
        recyclerViewProduits.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerViewProduits.setItemAnimator(new DefaultItemAnimator());

        btnAddCategorie = (AppCompatButton) v.findViewById(R.id.btn_addCategorie);
        btnAddCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On appel clicOnModifyOrInsertCategorie avec null en paramatre car il n'y a pas de categorie deja existante quand on ajoute une categorie
                clicOnModifyCategory(null);
            }
        });

        btnAddProduit = (AppCompatButton) v.findViewById(R.id.btn_addProduit);
        btnAddProduit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //On appel clicOnModifyOrInsertProduit avec null en paramatre car il n'y a pas de produit deja existant quand on ajoute un produit
                clicOnModifyOrInsertProduit(null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.reglage_title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_reglamge_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                new MonAT(CHOIX.SAVE).execute();
                return true;
            case R.id.menu_load:
                AlertDialogutils.showOkCancelDialog(getContext(), R.string.confirmation, R.string.dialog_reglage_ask_confirm_load, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MonAT(CHOIX.LOAD).execute();
                    }
                });

                return true;
            case R.id.menu_load_date:
                new MonAT(CHOIX.LOAD_DATE).execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



   /* ---------------------------------
    // Clic
    // -------------------------------- */

    @Override
    public void onClick(View v) {

    }

    /* ---------------------------------
    // CallBack Categorie
    // -------------------------------- */
    @Override
    public void clicOnModifyCategory(Categorie cat) {
        //On instancie la dialog Categorie
        DialogCategorie newFragment = new DialogCategorie();

        //On creer une categorie ou on recupere la categorie entrer en parametre
        final Categorie finalCategorie = cat == null ? new Categorie() : cat;

        //On envoie la categorie dans la dialog Categorie
        newFragment.setCategorie(finalCategorie);
        newFragment.setDialogCategorieCallBack(new DialogCategorie.DialogCategorieCallBack() {
            @Override
            public void dialogCategorieClicOnValider() {
                //On insert ou on update la categorie que l'on a ajouter ou modifier
                CategorieBddManager.insertOrUpdate(finalCategorie);

                //On recupere la position de cette categorie dans la liste des categories
                int positionCategorie = categorieList.indexOf(finalCategorie);
                if (positionCategorie >= 0) {
                    //Si la categorie est a une position de 0 ou plus c'est que la categorie existait deja dans la liste
                    //Donc on recharge le recycle view avec un item changed
                    categoryAdapter.notifyItemChanged(positionCategorie);
                }
                else {
                    //Sinon cela indique que la categorie n'appartenait pas a la liste
                    //Donc on insert la categorie dans la liste et on recharge le recycle view avec un item inserted
                    categorieList.add(finalCategorie);
                    categoryAdapter.notifyItemInserted(categorieList.size() - 1);
                }
            }
        });
        newFragment.show(getFragmentManager(), tag);
    }

    @Override
    public void clicOnCategory(Categorie categorie) {

        //Au clic sur une categorie on recupere la liste de produit qui lui est associer
        produitList.clear();

        categorie.resetProduitList();
        MyApplication.getDaoSession().clear();
        produitList.addAll(categorie.getProduitList());
        productAdapter.notifyDataSetChanged();

        // les autres categories a false pour display seulement les boutons de la categorie choisie
        Categorie oldCategorieATrue = null;
        for (Categorie c : categorieList) {
            if (c.isSelected()) {
                oldCategorieATrue = c;
            }
            c.setSelected(false);
        }
        //On set selected la categorie choisie a true
        categorie.setSelected(true);

        //On recharge le recycle view
        if (oldCategorieATrue != null) {
            categoryAdapter.notifyItemChanged(categorieList.indexOf(oldCategorieATrue));
        }
        categoryAdapter.notifyItemChanged(categorieList.indexOf(categorie));

        //TODO delete
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void clicOnDeleteCategory(final Categorie categorie) {
        //On creer un alert dialog pour confirmer la suppression de la categorie
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        //On set tous les elements et on display la dialog box
        alertDialogBuilder.setTitle("Confirmation");

        alertDialogBuilder
                .setMessage("Voulez-vous supprimer cette catégorie ?");

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //On recupere la position de la categorie dans la liste qu'on veut supprimer
                        int positionCategorie = categorieList.indexOf(categorie);
                        //On supprime la categorie de la bdd
                        CategorieBddManager.deleteCategorie(categorie);
                        //On supprime la categorie de la liste de categorie et on recharche le recycle view
                        categorieList.remove(positionCategorie);
                        categoryAdapter.notifyItemRemoved(positionCategorie);

                        produitList.clear();
                        //On met a jour le recycle view
                        productAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /* ---------------------------------
    // Callback produit
    // -------------------------------- */
    @Override
    public void clicOnModifyOrInsertProduit(final Produit produit) {
        //On instancie la dialog produit
        final DialogProduit newFragment = new DialogProduit();

        //On creer un produit ou on recupere le produit entrer en parametre
        final Produit finalProduit = produit == null ? new Produit() : produit;

        //On envoie la categorie dans la dialog Categorie
        newFragment.setProduit(finalProduit);
        newFragment.setDialogProduitCallBack(new DialogProduit.DialogProduitCallBack() {
            @Override
            public void dialogProduitClicOnValider() {
                Categorie categorieSelected = null; //catégorie séléctionnée
                Categorie categorieToInsertProduit = null;//Catégorie ou on va inserer le produit
                for (int i = 0; i < categorieList.size(); i++) {
                    if (categorieList.get(i).getId() == finalProduit.getCategorieID()) {
                        categorieToInsertProduit = categorieList.get(i);
                    }
                    if (categorieList.get(i).isSelected()) {
                        categorieSelected = categorieList.get(i);
                    }
                }
                if (categorieToInsertProduit == null) {
                    Toast.makeText(getContext(), "Erreur à l'insertion du produit, categorie non trouvé", Toast.LENGTH_SHORT).show();
                }
                else {
                    //une insertion
                    if (finalProduit.getId() == null) {
                        finalProduit.setConsommation(0);
                        finalProduit.setLotRecommande(0);
                        ProduitBddManager.insertOrUpdate(finalProduit);
                        if (categorieSelected != null && categorieSelected.getId() == finalProduit.getCategorieID()) {
                            produitList.add(finalProduit);
                            productAdapter.notifyItemInserted(produitList.size() - 1);
                        }
                    }
                    else {
                        //une modification
                        if (finalProduit.getConsommation() == null) {
                            finalProduit.setConsommation(0);
                        }
                        finalProduit.setLotRecommande(0);
                        ProduitBddManager.insertOrUpdate(finalProduit);

                        //On regarde le produit à changé de catégorie
                        if (categorieSelected.getId() == categorieToInsertProduit.getId()) {
                            productAdapter.notifyItemChanged(produitList.indexOf(finalProduit));
                        }
                        else {
                            int index = produitList.indexOf(produit);
                            produitList.remove(index);
                            productAdapter.notifyItemRemoved(index);
                        }
                    }
                    categorieSelected.resetProduitList();
                    categorieToInsertProduit.resetProduitList();
                }
            }
        });
        newFragment.show(getFragmentManager(), tag);
    }

    @Override
    public void clicOnProduit(Produit produit) {

        //On set selected le produit choisi a true et les autres a false pour display seulement les boutons du produit choisi
        Produit oldProduitATrue = null;
        for (Produit p : produitList) {
            if (p.isSelected()) {
                oldProduitATrue = p;
            }
            p.setSelected(false);
        }
        produit.setSelected(true);

        //On recharge le recycle view
        productAdapter.notifyItemChanged(produitList.indexOf(produit));
        if (oldProduitATrue != null) {
            productAdapter.notifyItemChanged(produitList.indexOf(oldProduitATrue));
        }
        //TODO delete
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void clicOnDeleteProduit(final Produit produit) {
        //On creer un alert dialog pour confirmer la suppression du produit
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        //On set tous les elements et on display la dialog box
        alertDialogBuilder.setTitle("Confirmation");

        alertDialogBuilder
                .setMessage("Voulez-vous supprimer ce produit ?");

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        productAdapter.notifyItemRemoved(produitList.indexOf(produit));
                        //On recupere la position du produit qu'on veut delete dans la liste
                        //On retire le produit de la liste et on recharche le recycle view
                        produitList.remove(produit);
                        //On supprime le produit de la bdd
                        ProduitBddManager.deleteProduit(produit);
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /* ---------------------------------
    // AT
    // -------------------------------- */

    private enum CHOIX {SAVE, LOAD, LOAD_DATE}

    private class MonAT extends AsyncTask<Void, Void, Exception> {

        private Exception exception = null;
        private ProgressDialog progressDialog;
        private CHOIX choix;
        private ArrayList<DateBean> dateArrayListBean = new ArrayList<>();

        public MonAT(CHOIX choix) {
            this.choix = choix;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switch (choix) {
                case SAVE:
                    progressDialog = ProgressDialog.show(getContext(), "", getContext().getString(R.string.reglage_save_message), true, false);
                    break;
                case LOAD:
                    progressDialog = ProgressDialog.show(getContext(), "", getContext().getString(R.string.reglage_load_message), true, false);
                    break;
                case LOAD_DATE:
                    progressDialog = ProgressDialog.show(getContext(), "", getContext().getString(R.string.reglage_load_message), true, false);
                    break;
            }
        }

        @Override
        protected Exception doInBackground(Void... params) {
            try {
                switch (choix) {

                    case SAVE:
                        WSUtils.saveData();
                        break;
                    case LOAD:
                        WSUtils.loadData();
                        break;
                    case LOAD_DATE:
                        dateArrayListBean.addAll(WSUtils.loadHistoriqueDate());
                        break;
                }

                return null;
            }
            catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            progressDialog.dismiss();
            if (e != null) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Une erreur est survenue " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                switch (choix) {

                    case SAVE:
                        Toast.makeText(getContext(), R.string.reglage_toast_save, Toast.LENGTH_SHORT).show();
                        break;
                    case LOAD:
                        for (Categorie categorie : categorieList) {
                            categorie.setSelected(false);
                        }
                        //vide la liste des produits et on reset la liste des categories avec les nouvelles donnees de la base
                        reloadData();

                        Toast.makeText(getContext(), R.string.reglage_toast_load, Toast.LENGTH_SHORT).show();

                        break;
                    case LOAD_DATE:
                        ArrayList<Date> dateArrayList = new ArrayList<>();
                        for (int i = 0; i < dateArrayListBean.size(); i++) {
                            Date date = new Date();
                            date.setTime(dateArrayListBean.get(i).getDateSave());
                            dateArrayList.add(date);
                        }

                        dialogHistoriqueDate.setDateArrayList(dateArrayList);
                        dialogHistoriqueDate.setDialogHistoriqueDateCallBack(FragmentReglage.this);
                        dialogHistoriqueDate.show(getActivity().getFragmentManager(), "");
                        break;
                }
            }
        }
    }

    public void reloadData() {
        produitList.clear();
        categorieList.clear();
        categorieList.addAll(CategorieBddManager.getCategories());
        //on acutalise les adapter
        categoryAdapter.notifyDataSetChanged();
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFinish() {
        dialogHistoriqueDate.dismiss();
        reloadData();
    }
}
