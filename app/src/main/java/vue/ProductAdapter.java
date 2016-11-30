package vue;

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
    private ArrayList<Produit> getProduitArrayList;
    private View v;

    public ProductAdapter(ProductAffichageEnum choixAffichage, ArrayList<Produit> getProduitArrayList) {
        this.choixAffichage = choixAffichage;
        this.getProduitArrayList = getProduitArrayList;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        switch (choixAffichage) {

            case Note:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_reglage, vg, false);
                break;
            case Accueil:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_reglage, vg, false);
            case Reglage:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_reglage, vg, false);
                break;
            case Bilan:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_produit_reglage, vg, false);
                break;
        }

        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder holder, int position) {
        final Produit produitbean = getProduitArrayList.get(position);
        holder.displaylibelle.setText(produitbean.getNom());
        holder.displayTarif.setText(String.valueOf(produitbean.getPrix())); // A voir par Allan
        holder.displayLot.setText(String.valueOf(produitbean.getLot())); // A voir par Allan
    }

    @Override
    public int getItemCount() {
        return getProduitArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displaylibelle;
        public TextView displayLot;
        public TextView displayTarif;
        public Button displayModifyProduit;
        public ImageView displayDeleteProduit;
        public View root;

        public ViewHolder(View itemView) {
            super(itemView);
            displaylibelle = (TextView) itemView.findViewById(R.id.txt_produit);
            displayLot = (TextView) itemView.findViewById(R.id.txt_lot);
            displayTarif = (TextView) itemView.findViewById(R.id.txt_tarif);
            displayModifyProduit = (Button) itemView.findViewById(R.id.btn_modifiy_prod);
            displayDeleteProduit = (ImageView) itemView.findViewById(R.id.img_prod);
            root = itemView.findViewById(R.id.root_produit);
        }
    }
}

