package com.example.anthony.gestionstock.vue.adapter;

import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.gestionstock.Constante;
import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.Utils;
import com.example.anthony.gestionstock.vue.ProductAffichageEnum;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.HashMap;

import greendao.Produit;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ProductAffichageEnum choixAffichage;
    private ArrayList<Produit> getProduitArrayList;
    private ProductAdapterCallBack productAdapterCallBack;
    private HashMap<Produit, Long> quantiteHashMap;
    private final String SYMBOLE_EURO = "€";

    // -------------------------------- CONSTRUCTOR -------------------------------------------------- //
    public ProductAdapter(ProductAffichageEnum choixAffichage, ArrayList<Produit> getProduitArrayList, ProductAdapterCallBack productAdapterCallBack, HashMap<Produit, Long> quantiteHashMap) {
        this.choixAffichage = choixAffichage;
        this.getProduitArrayList = getProduitArrayList;
        this.productAdapterCallBack = productAdapterCallBack;
        this.quantiteHashMap = quantiteHashMap;
    }

    // --------------------------------  END CONSTRUCTOR -------------------------------------------------- //

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View v = null;
        switch (choixAffichage) {
            case Accueil:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_accueil, vg, false);
                return new ProductAdapter.ViewHolderAccueil(v);
            case Reglage:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_reglage, vg, false);
                break;
            case Bilan:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_bilan, vg, false);
                break;
            case Stock:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_stock, vg, false);
                break;
        }

        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder vh, final int position) {

        final Produit produitbean = getProduitArrayList.get(position);

        switch (choixAffichage) {
            case Accueil:
                ProductAdapter.ViewHolderAccueil viewHolderAccueil = (ViewHolderAccueil) vh;

                String text = produitbean.getNom().toUpperCase() + "\n(" + Utils.formatToMoney(produitbean.getPrix()) + "€)";
                viewHolderAccueil.root.setText(text);
                viewHolderAccueil.root.setSupportBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(produitbean.getCategorie().getCouleur())));
                viewHolderAccueil.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnProduit(produitbean);
                    }
                });
                break;

            case Reglage:
                ProductAdapter.ViewHolder holderReglage = (ViewHolder) vh;
                holderReglage.txt_produit.setText(produitbean.getNom());
                holderReglage.txt_tarif.setText(Utils.formatToMoney(produitbean.getPrix()) + SYMBOLE_EURO);
                holderReglage.txt_lot.setText(holderReglage.txt_lot.getContext().getString(R.string.reglage_produit_cellule_lot, produitbean.getLot()));

                if (produitbean.isSelected()) {
                    holderReglage.btn_modifiy_prod.setVisibility(View.VISIBLE);
                    holderReglage.img_prod.setVisibility(View.VISIBLE);
                    holderReglage.cv_bg.setCardBackgroundColor(holderReglage.cv_bg.getResources().getColor(R.color.selected_cellule_bg));
                }
                else {
                    holderReglage.btn_modifiy_prod.setVisibility(View.INVISIBLE);
                    holderReglage.img_prod.setVisibility(View.INVISIBLE);
                    holderReglage.cv_bg.setCardBackgroundColor(holderReglage.cv_bg.getResources().getColor(R.color.unselected_cellule_bg));
                }
                holderReglage.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnProduit(produitbean);
                    }
                });
                holderReglage.btn_modifiy_prod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (productAdapterCallBack != null) {
                            productAdapterCallBack.clicOnModifyOrInsertProduit(produitbean);
                        }
                    }
                });
                holderReglage.img_prod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnDeleteProduit(produitbean);
                    }
                });
                break;

            case Bilan:
                ProductAdapter.ViewHolder holder2 = (ViewHolder) vh;
                holder2.txt_produit.setText(produitbean.getNom());
                holder2.txt_tarif.setText(Utils.formatToMoney(produitbean.getPrix()) + SYMBOLE_EURO);
                if (quantiteHashMap != null) {
                    holder2.displayQuantite.setText(String.valueOf(quantiteHashMap.get(produitbean)));
                    holder2.displayMontant.setText(String.valueOf((quantiteHashMap.get(produitbean) * produitbean.getPrix())) + SYMBOLE_EURO);
                }

                break;

            case Stock:
                final ProductAdapter.ViewHolder holder3 = (ViewHolder) vh;
                //le nom
                holder3.txt_produit.setText(produitbean.getNom());

                //quantite
                int quantite = produitbean.getConsommation() == null ? 0 : produitbean.getConsommation();

                int nbLot = 0;
                if (produitbean.getLot() != null || produitbean.getLot() != 0) {
                    nbLot = quantite / produitbean.getLot();
                }
                //S'appelle tarif mais affiche la quantité
                holder3.txt_tarif.setText("" + quantite);
                holder3.txt_lot.setText("" + nbLot);

                //Si pas assez de quantite pour fair un lot on n'affiche pas le picker
                if (nbLot == 0) {
                    holder3.np.setVisibility(View.INVISIBLE);
                    holder3.tv_recommande.setVisibility(View.INVISIBLE);
                }
                else {
                    holder3.np.setVisibility(View.VISIBLE);
                    holder3.tv_recommande.setVisibility(View.VISIBLE);
                }

                //Valeur min du picker
                holder3.np.setMinValue(0);
                //Nombre de valeur + faire des précommande
                holder3.np.setMaxValue(nbLot + Constante.NB_LOT_EN_AVANCE);
                holder3.np.setValue(produitbean.getLotRecommande());
                //On affiche combien d'untié représente 1 lot
                holder3.tv_lot.setText(holder3.tv_lot.getContext().getString(R.string.stock_1lot, produitbean.getLot()));
                //                holder3.np.setColor(Color.BLACK, Color.YELLOW);
                //                holder3.np.setOverValue(lot);

                holder3.np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        //On met à jour la recomande
                        produitbean.setLotRecommande(newVal);
                    }
                });

                break;
        }
    }

    @Override
    public int getItemCount() {
        return getProduitArrayList.size();
    }

    public class ViewHolderAccueil extends RecyclerView.ViewHolder {

        public AppCompatButton root;

        public ViewHolderAccueil(View itemView) {
            super(itemView);

            root = (AppCompatButton) itemView.findViewById(R.id.root);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_produit;
        public TextView txt_lot;
        public TextView txt_tarif;
        public TextView displayMontant;
        public AppCompatButton btn_modifiy_prod;
        public TextView displayQuantite;
        public TextView tv_recommande;
        public TextView tv_lot;
        public ImageView img_prod;
        public View root;
        public CardView cv_bg;
        public NumberPicker np;

        public ViewHolder(View itemView) {
            super(itemView);

            switch (choixAffichage) {

                case Reglage:
                    txt_produit = (TextView) itemView.findViewById(R.id.txt_produit);
                    txt_lot = (TextView) itemView.findViewById(R.id.txt_lot);
                    txt_tarif = (TextView) itemView.findViewById(R.id.txt_tarif);
                    btn_modifiy_prod = (AppCompatButton) itemView.findViewById(R.id.btn_modifiy_prod);
                    img_prod = (ImageView) itemView.findViewById(R.id.img_prod);
                    img_prod.setColorFilter(img_prod.getResources().getColor(R.color.red));
                    root = itemView.findViewById(R.id.root_produit); // permet de recupèrer le clic sur le cardview

                    cv_bg = (CardView) itemView.findViewById(R.id.cv_bg);
                    break;

                case Bilan:
                    txt_produit = (TextView) itemView.findViewById(R.id.libelle_bilan);
                    displayQuantite = (TextView) itemView.findViewById(R.id.quantite_bilan);
                    txt_tarif = (TextView) itemView.findViewById(R.id.prix_u_bilan);
                    displayMontant = (TextView) itemView.findViewById(R.id.prix_total_ligne_bilan);
                    break;

                case Stock:
                    txt_produit = (TextView) itemView.findViewById(R.id.txt_produit);
                    txt_lot = (TextView) itemView.findViewById(R.id.txt_lot);
                    txt_tarif = (TextView) itemView.findViewById(R.id.txt_tarif);
                    tv_lot = (TextView) itemView.findViewById(R.id.tv_lot);
                    tv_recommande = (TextView) itemView.findViewById(R.id.tv_recommande);
                    cv_bg = (CardView) itemView.findViewById(R.id.cv_bg);
                    np = (NumberPicker) itemView.findViewById(R.id.np);
                    break;
            }
        }
    }

    public interface ProductAdapterCallBack {
        void clicOnModifyOrInsertProduit(Produit produit);

        void clicOnProduit(Produit produit);

        void clicOnDeleteProduit(Produit produit);
    }
}

