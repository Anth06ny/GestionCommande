package vue.adapter;

import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;
import java.util.HashMap;

import greendao.Produit;
import model.ProduitBddManager;
import vue.ProductAffichageEnum;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ProductAffichageEnum choixAffichage;
    private ArrayList<Produit> getProduitArrayList;
    private ProductAdapterCallBack productAdapterCallBack;
    private ArrayList<Produit> produitArrayListAll;
    private HashMap<Produit, Long> quantiteHashMap;
    private HashMap<Produit, Integer> lotRecommandeHashMap = new HashMap<>();
    private final String SYMBOLE_EURO = " €";

    // -------------------------------- CONSTRUCTOR -------------------------------------------------- //
    public ProductAdapter(ProductAffichageEnum choixAffichage, ArrayList<Produit> getProduitArrayList, ProductAdapterCallBack productAdapterCallBack, HashMap<Produit, Long> quantiteHashMap) {
        this.choixAffichage = choixAffichage;
        this.getProduitArrayList = getProduitArrayList;
        this.productAdapterCallBack = productAdapterCallBack;
        this.quantiteHashMap = quantiteHashMap;
    }

    // --------------------------------  END CONSTRUCTOR -------------------------------------------------- //

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View v = null;
        switch (choixAffichage) {
            case Accueil:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_accueil, vg, false);
                break;
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
    public void onBindViewHolder(final ProductAdapter.ViewHolder holder, int position) {

        final Produit produitbean = getProduitArrayList.get(position);
        produitArrayListAll = (ArrayList<Produit>) ProduitBddManager.getProduit();

        switch (choixAffichage) {

            case Accueil:
                holder.produitAccueil.setText(produitbean.getNom());
                holder.produitAccueil.setSupportBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(produitbean.getCategorie().getCouleur())));
                holder.produitAccueil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnProduit(produitbean);
                    }
                });
                break;

            case Reglage:
                holder.displaylibelle.setText(produitbean.getNom());
                holder.displayTarif.setText(String.valueOf(produitbean.getPrix() + SYMBOLE_EURO));
                holder.displayLot.setText(String.valueOf(produitbean.getLot()));

                if (produitbean.isSelected()) {
                    holder.displayModifyProduit.setVisibility(View.VISIBLE);
                    holder.displayDeleteProduit.setVisibility(View.VISIBLE);
                    holder.cv_bg.setCardBackgroundColor(holder.cv_bg.getResources().getColor(R.color.selected_cellule_bg));
                }
                else {
                    holder.displayModifyProduit.setVisibility(View.INVISIBLE);
                    holder.displayDeleteProduit.setVisibility(View.INVISIBLE);
                    holder.cv_bg.setCardBackgroundColor(holder.cv_bg.getResources().getColor(R.color.unselected_cellule_bg));
                }
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnProduit(produitbean);
                    }
                });
                holder.displayModifyProduit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (productAdapterCallBack != null) {
                            productAdapterCallBack.clicOnModifyOrInsertProduit(produitbean);
                        }
                    }
                });
                holder.displayDeleteProduit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnDeleteProduit(produitbean);
                    }
                });
                break;

            case Bilan:
                holder.displaylibelle.setText(produitbean.getNom());
                holder.displayTarif.setText(String.valueOf(produitbean.getPrix()) + SYMBOLE_EURO);
                if (quantiteHashMap != null) {
                    holder.displayQuantite.setText(String.valueOf(quantiteHashMap.get(produitbean)));
                    holder.displayMontant.setText(String.valueOf((quantiteHashMap.get(produitbean) * produitbean.getPrix())) + SYMBOLE_EURO);
                }

                break;

            case Stock:

                if (produitbean.getLotRecommande() == null) {
                    produitbean.setLotRecommande(0);
                }

                holder.displaylibelle.setText(produitbean.getNom());
                if (produitbean.getConsommation() != null) {
                    holder.displayQuantite.setText(String.valueOf(produitbean.getConsommation()));
                    holder.displayLot.setText(String.valueOf(produitbean.getConsommation() / produitbean.getLot()));
                }
                else {
                    holder.displayQuantite.setText("0");
                    holder.displayLot.setText("0");
                }

                holder.displayLotRecommande.setText(String.valueOf(produitbean.getLotRecommande()));

                holder.displayMin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        produitbean.setLotRecommande(0);
                        holder.displayLotRecommande.setText(String.valueOf(produitbean.getLotRecommande()));
                    }
                });
                holder.displayRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.valueOf(String.valueOf(holder.displayLotRecommande.getText())) > 0) {
                            produitbean.setLotRecommande(produitbean.getLotRecommande() - 1);
                            holder.displayLotRecommande.setText(String.valueOf(produitbean.getLotRecommande()));
                        }
                    }
                });
                holder.displayAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.valueOf(String.valueOf(holder.displayLotRecommande.getText())) < Integer.valueOf(String.valueOf(holder.displayLot.getText()))) {
                            produitbean.setLotRecommande(produitbean.getLotRecommande() + 1);
                            holder.displayLotRecommande.setText(String.valueOf(produitbean.getLotRecommande()));
                        }
                    }
                });
                holder.displayMax.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        produitbean.setLotRecommande(Integer.valueOf((String) holder.displayLot.getText()));
                        holder.displayLotRecommande.setText(String.valueOf(produitbean.getLotRecommande()));
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return getProduitArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displaylibelle;
        public TextView displayLot;
        public TextView displayTarif;
        public TextView displayMontant;
        public AppCompatButton displayModifyProduit;
        public TextView displayQuantite;
        public ImageView displayDeleteProduit;
        public View root;
        public AppCompatButton produitAccueil;
        public TextView displayMin;
        public TextView displayMax;
        public TextView displayLotRecommande;
        public ImageView displayRemove;
        public ImageView displayAdd;
        public CardView cv_bg;

        public ViewHolder(View itemView) {
            super(itemView);

            switch (choixAffichage) {
                case Accueil:
                    produitAccueil = (AppCompatButton) itemView.findViewById(R.id.root_produit_accueil);
                    break;

                case Reglage:
                    displaylibelle = (TextView) itemView.findViewById(R.id.txt_produit);
                    displayLot = (TextView) itemView.findViewById(R.id.txt_lot);
                    displayTarif = (TextView) itemView.findViewById(R.id.txt_tarif);
                    displayModifyProduit = (AppCompatButton) itemView.findViewById(R.id.btn_modifiy_prod);
                    displayDeleteProduit = (ImageView) itemView.findViewById(R.id.img_prod);
                    displayDeleteProduit.setColorFilter(displayDeleteProduit.getResources().getColor(R.color.red));
                    root = itemView.findViewById(R.id.root_produit); // permet de recupèrer le clic sur le cardview
                    cv_bg = (CardView) itemView.findViewById(R.id.cv_bg);
                    break;

                case Bilan:
                    displaylibelle = (TextView) itemView.findViewById(R.id.libelle_bilan);
                    displayQuantite = (TextView) itemView.findViewById(R.id.quantite_bilan);
                    displayTarif = (TextView) itemView.findViewById(R.id.prix_u_bilan);
                    displayMontant = (TextView) itemView.findViewById(R.id.prix_total_ligne_bilan);
                    break;

                case Stock:
                    displaylibelle = (TextView) itemView.findViewById(R.id.libelle_stock);
                    displayQuantite = (TextView) itemView.findViewById(R.id.quantite_consomme_stock);
                    displayLot = (TextView) itemView.findViewById(R.id.lot_consomme_stock);
                    displayMin = (TextView) itemView.findViewById(R.id.min_stock);
                    displayRemove = (ImageView) itemView.findViewById(R.id.img_remove_stock);
                    displayLotRecommande = (TextView) itemView.findViewById(R.id.lot_recommande_stock);
                    displayAdd = (ImageView) itemView.findViewById(R.id.img_add_stock);
                    displayMax = (TextView) itemView.findViewById(R.id.max_stock);
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

