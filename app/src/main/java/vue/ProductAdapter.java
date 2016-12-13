package vue;

import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import greendao.Consomme;
import greendao.Produit;
import model.ProduitBddManager;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ProductAffichageEnum choixAffichage;
    private ArrayList<Produit> getProduitArrayList;
    private View v;
    private ProductAdapterCallBack productAdapterCallBack;
    private ArrayList<Long> idCommandes;
    private String quantiteTest;
    private HashMap<Produit, Long> quantiteHashMap;
    private ArrayList<Produit> produitArrayListAll;
    private Boolean mettreZero = false;
    private Boolean mettreMax = false;
    private HashMap<Produit, Integer> lotRecommandeHashMap = new HashMap<>();

    // -------------------------------- CONSTRUCTOR -------------------------------------------------- //
    public ProductAdapter(ProductAffichageEnum choixAffichage, ArrayList<Produit> getProduitArrayList, HashMap<Produit, Long> quantiteHashMap,
                          ProductAdapterCallBack
                                  productAdapterCallBack, Boolean mettreZero, Boolean mettreMax) {
        this.choixAffichage = choixAffichage;
        this.getProduitArrayList = getProduitArrayList;
        this.productAdapterCallBack = productAdapterCallBack;
        this.quantiteHashMap = quantiteHashMap;
        this.mettreZero = mettreZero;
        this.mettreMax = mettreMax;
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

            case Note:
                ArrayList<Consomme> consommeArrayList = (ArrayList<Consomme>) produitbean.getProduitRef();
                int positionNote = -1;
                float montantLigne = 0;
                for (int i = 0; i < consommeArrayList.size(); i++) {
                    if (consommeArrayList.get(i).getCommande() == null) {
                        positionNote = i;
                        montantLigne = consommeArrayList.get(i).getQuantite() * produitbean.getPrix();
                    }
                }
                holder.displayQuantite.setText(String.valueOf(consommeArrayList.get(positionNote).getQuantite()));
                holder.displaylibelle.setText(produitbean.getNom());
                holder.displayTarif.setText(String.valueOf(produitbean.getPrix()));
                holder.displayMontant.setText(String.valueOf(montantLigne));
                holder.displayDeleteProduit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productAdapterCallBack.clicOnDeleteProduit(produitbean);
                    }
                });

                break;
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
                holder.displayTarif.setText(String.valueOf(produitbean.getPrix()));
                if (quantiteHashMap != null) {
                    holder.displayQuantite.setText(String.valueOf(quantiteHashMap.get(produitbean)));
                    holder.displayMontant.setText(String.valueOf((quantiteHashMap.get(produitbean) * produitbean.getPrix())));
                }

                break;

            case Stock:

                if (!lotRecommandeHashMap.containsKey(produitbean)) {
                    int lotRecommande = 0;
                    lotRecommandeHashMap.put(produitbean, lotRecommande);
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
                holder.displayLotRecommande.setText(String.valueOf(lotRecommandeHashMap.get(produitbean)));
                if (mettreZero) {
                    for (Map.Entry entry : lotRecommandeHashMap.entrySet()) {
                        int lotRecommande = 0;
                        lotRecommandeHashMap.put((Produit) entry.getKey(), lotRecommande);
                    }
                    holder.displayLotRecommande.setText(String.valueOf(lotRecommandeHashMap.get(produitbean)));
                }
                if (mettreMax) {
                    for (Map.Entry entry : lotRecommandeHashMap.entrySet()) {
                        int positionProduitHash = getProduitArrayList.indexOf(entry.getKey());
                        int lotRecommande = getProduitArrayList.get(positionProduitHash).getConsommation() / getProduitArrayList.get(positionProduitHash).getLot();
                        lotRecommandeHashMap.put((Produit) entry.getKey(), lotRecommande);
                    }
                    holder.displayLotRecommande.setText(holder.displayLot.getText());
                }
                holder.displayMin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lotRecommande = 0;
                        lotRecommandeHashMap.put(produitbean, lotRecommande);
                        holder.displayLotRecommande.setText(String.valueOf(lotRecommandeHashMap.get(produitbean)));
                    }
                });
                holder.displayRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.valueOf(String.valueOf(holder.displayLotRecommande.getText())) > 0) {
                            int lotRecommande = 0;
                            lotRecommande = lotRecommandeHashMap.get(produitbean) - 1;
                            lotRecommandeHashMap.put(produitbean, lotRecommande);
                            holder.displayLotRecommande.setText(String.valueOf(lotRecommandeHashMap.get(produitbean)));
                        }
                    }
                });
                holder.displayAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.valueOf(String.valueOf(holder.displayLotRecommande.getText())) < Integer.valueOf(String.valueOf(holder.displayLot.getText()))) {
                            int lotRecommande;
                            lotRecommande = lotRecommandeHashMap.get(produitbean) + 1;
                            lotRecommandeHashMap.put(produitbean, lotRecommande);
                            holder.displayLotRecommande.setText(String.valueOf(lotRecommandeHashMap.get(produitbean)));
                        }
                    }
                });
                holder.displayMax.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lotRecommande = 0;
                        lotRecommande = Integer.valueOf((String) holder.displayLot.getText());
                        lotRecommandeHashMap.put(produitbean, lotRecommande);
                        holder.displayLotRecommande.setText(String.valueOf(lotRecommandeHashMap.get(produitbean)));
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
                    produitAccueil = (AppCompatButton) itemView.findViewById(R.id.root_produit_accueil);
                    break;

                case Reglage:
                    displaylibelle = (TextView) itemView.findViewById(R.id.txt_produit);
                    displayLot = (TextView) itemView.findViewById(R.id.txt_lot);
                    displayTarif = (TextView) itemView.findViewById(R.id.txt_tarif);
                    displayModifyProduit = (AppCompatButton) itemView.findViewById(R.id.btn_modifiy_prod);
                    displayDeleteProduit = (ImageView) itemView.findViewById(R.id.img_prod);
                    root = itemView.findViewById(R.id.root_produit); // permet de recupèrer le clic sur le cardview
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

        void clicOnMinStock(Produit produit);

        void clicOnRemoveStock(Produit produit);

        void clicOnAddStock(Produit produit);

        void clicOnMaxStock(Produit produit);
    }
}

