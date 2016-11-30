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

import greendao.Categorie;
import greendao.Produit;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ProductAffichageEnum choixAffichage;
    private ArrayList<Produit> produitArrayList;

    public ProductAdapter(ProductAffichageEnum choixAffichage, ArrayList<Categorie> category_bean) {
        this.choixAffichage = choixAffichage;
        //    this.category_bean = category_bean;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        switch (choixAffichage) {

            case Note:

                break;
            case Accueil:
                View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_category, vg, false);
            case Reglage:
                break;
            case Bilan:
                break;
        }

        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_category, vg, false);
        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
      /*  Categorie categoriebean = category_bean.get(position);

        if (holder.product_libelle != null) {
            holder.product_libelle.setText(categoriebean.getNom());
        }*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView product_libelle;
        public TextView displayColor;
        public Button displayModifyCategory;
        public ImageView displayDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            product_libelle = (TextView) itemView.findViewById(R.id.txt_category);
        }
    }
}

