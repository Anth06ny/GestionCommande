package vue;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;

import greendao.Produit;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ProductAffichageEnum choixAffichage;
    private ArrayList<Produit> getProduitArrayList = new ArrayList<>();
    private View v;
    private ProductAdapterCallBack productAdapterCallBack;

    // -------------------------------- CONSTRUCTOR -------------------------------------------------- //
    public ProductAdapter(ProductAffichageEnum choixAffichage, ArrayList<Produit> getProduitArrayList, ProductAdapterCallBack productAdapterCallBack) {
        this.choixAffichage = choixAffichage;
        this.getProduitArrayList = getProduitArrayList;
        this.productAdapterCallBack = productAdapterCallBack;
    }

    // --------------------------------  END CONSTRUCTOR -------------------------------------------------- //

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        switch (choixAffichage) {

            case Note:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_note_accueil, vg, false);
                break;
            case Accueil:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_accueil, vg, false);
                break;
            case Reglage:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_reglage, vg, false);
                break;
            case Bilan:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_reglage, vg, false);
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
        switch (choixAffichage) {

            case Note:
                holder.displayQuantite.setText("1");
                holder.displaylibelle.setText(produitbean.getNom());
                holder.displayTarif.setText(String.valueOf(produitbean.getPrix()));
                holder.displayMontant.setText("1");
                holder.displayDeleteProduit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnDeleteProduitNote(produitbean);
                    }
                });

                break;
            case Accueil:
                holder.produitAccueil.setText(produitbean.getNom());
                holder.produitAccueil.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(produitbean.getCategorie().getCouleur())));
                holder.produitAccueil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnProduitAcceuil(produitbean);
                    }
                });
                break;
            case Reglage:
                holder.displaylibelle.setText(produitbean.getNom());
                holder.displayTarif.setText(String.valueOf(produitbean.getPrix() + " €")); // A voir par Allan
                holder.displayLot.setText(String.valueOf(produitbean.getLot())); // A voir par Allan

                if (produitbean.isSelected()) {
                    holder.displayModifyProduit.setVisibility(View.VISIBLE);
                    holder.displayDeleteProduit.setVisibility(View.VISIBLE);
                }
                else {
                    holder.displayModifyProduit.setVisibility(View.INVISIBLE);
                    holder.displayDeleteProduit.setVisibility(View.INVISIBLE);
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
                            productAdapterCallBack.clicOnModifyProduit(produitbean);
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
                break;
            case Stock:
                holder.displaylibelle.setText(produitbean.getNom());
                holder.displayMin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnMinStock(produitbean);
                    }
                });
                holder.displayRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnRemoveStock(produitbean);
                    }
                });
                holder.displayAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnAddStock(produitbean);
                    }
                });
                holder.displayMax.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnMaxStock(produitbean);
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
        public Button displayModifyProduit;
        public TextView displayQuantite;
        public ImageView displayDeleteProduit;
        public View root;
        public Button produitAccueil;
        public TextView displayMin;
        public TextView displayMax;
        public TextView displayLotRecommande;
        public ImageView displayRemove;
        public ImageView displayAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            switch (choixAffichage) {
                case Note:
                    displayQuantite = (TextView) itemView.findViewById(R.id.quantite_note);
                    displaylibelle = (TextView) itemView.findViewById(R.id.libelle_note);
                    displayTarif = (TextView) itemView.findViewById(R.id.prix_u_note);
                    displayMontant = (TextView) itemView.findViewById(R.id.montant_note);
                    displayDeleteProduit = (ImageView) itemView.findViewById(R.id.delete_produit_note);
                    break;

                case Accueil:
                    produitAccueil = (Button) itemView.findViewById(R.id.root_produit_accueil);
                    break;

                case Reglage:
                    displaylibelle = (TextView) itemView.findViewById(R.id.txt_produit);
                    displayLot = (TextView) itemView.findViewById(R.id.txt_lot);
                    displayTarif = (TextView) itemView.findViewById(R.id.txt_tarif);
                    displayModifyProduit = (Button) itemView.findViewById(R.id.btn_modifiy_prod);
                    displayDeleteProduit = (ImageView) itemView.findViewById(R.id.img_prod);
                    root = itemView.findViewById(R.id.root_produit); // permet de recupèrer le clic sur le cardview
                    break;

                case Bilan:
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
        void clicOnModifyProduit(Produit produit);

        void clicOnProduit(Produit produit);

        void clicOnDeleteProduit(Produit produit);

        void clicOnProduitAcceuil(Produit produit);

        void clicOnDeleteProduitNote(Produit produit);

        void clicOnMinStock(Produit produit);

        void clicOnRemoveStock(Produit produit);

        void clicOnAddStock(Produit produit);

        void clicOnMaxStock(Produit produit);
    }
}

