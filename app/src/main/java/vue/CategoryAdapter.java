package vue;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.controller.ProductAffichageEnum;

import java.util.ArrayList;

import greendao.Categorie;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ProductAffichageEnum choixAffichage;
    private ArrayList<Categorie> category_bean;
    private Context context;

    public CategoryAdapter(Context context, ProductAffichageEnum choixAffichage, ArrayList<Categorie> category_bean) {
        this.context = context;
        this.choixAffichage = choixAffichage;
        this.category_bean = category_bean;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
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
        return new CategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Categorie categoriebean = category_bean.get(position);


        switch (choixAffichage) {

            case Note:

                break;
            case Accueil:

                break;
            case Reglage:
                break;
            case Bilan:
                break;
            default:

                holder.displayCategory.setText(categoriebean.getNom());
                holder.displayColor.setText(categoriebean.getCouleur());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displayCategory;
        public TextView displayColor;
        public Button displayModifyCategory;
        public ImageView displayDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            displayCategory = (TextView) itemView.findViewById(R.id.txt_category);
            displayColor = (TextView) itemView.findViewById(R.id.txt_color);
            displayModifyCategory = (Button) itemView.findViewById(R.id.btn_modifiy_cat);
            displayDelete = (ImageView) itemView.findViewById(R.id.img_cat);
        }
    }

    public class ViewHolderAccueil extends RecyclerView.ViewHolder {
        public TextView displayCategory;
        public TextView displayColor;
        public Button displayModifyCategory;
        public ImageView displayDelete;

        public ViewHolderAccueil(View itemView) {
            super(itemView);
            displayCategory = (TextView) itemView.findViewById(R.id.txt_category);
            displayColor = (TextView) itemView.findViewById(R.id.txt_color);
            displayModifyCategory = (Button) itemView.findViewById(R.id.btn_modifiy_cat);
            displayDelete = (ImageView) itemView.findViewById(R.id.img_cat);
        }
    }
}

