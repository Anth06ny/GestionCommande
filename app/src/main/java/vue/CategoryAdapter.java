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
import model.CategorieBddManager;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Categorie> category_bean;
    private CategoryAdapterCallBack categoryAdapterCallBack;
    private ArrayList<Produit> produit_bean;
    private ProductAdapter productAdapter;
    private RecyclerView getRecyclerViewProduits;

    private CategorieBddManager categorieBddManager;

    public CategoryAdapter(ArrayList<Categorie> category_bean, CategoryAdapterCallBack categoryAdapterCallBack) {
        this.category_bean = category_bean;
        this.categoryAdapterCallBack = categoryAdapterCallBack;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_category, vg, false);
        return new CategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.ViewHolder holder, final int position) {
        final Categorie categoriebean = category_bean.get(position);

        holder.displayCategory.setText(categoriebean.getNom());
        holder.displayColor.setBackgroundColor(Integer.parseInt(categoriebean.getCouleur()));
        holder.displayModifyCategory.setVisibility(View.INVISIBLE);
        holder.displayDelete.setVisibility(View.INVISIBLE);

        if (!categoriebean.isSelected()) {
            holder.displayModifyCategory.setVisibility(View.INVISIBLE);
            holder.displayDelete.setVisibility(View.INVISIBLE);
        }
        else {
            holder.displayModifyCategory.setVisibility(View.VISIBLE);
            holder.displayDelete.setVisibility(View.VISIBLE);
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryAdapterCallBack.clicOnCategory(categoriebean);
            }
        });

        holder.displayModifyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryAdapterCallBack != null) {
                    categoryAdapterCallBack.clicOnModifyCategory(categoriebean);
                }
            }
        });

        holder.displayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryAdapterCallBack.clicOnDeleteCategory(categoriebean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return category_bean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView displayCategory;
        public TextView displayColor;
        public Button displayModifyCategory;
        public ImageView displayDelete;
        public View root;

        public ViewHolder(View itemView) {
            super(itemView);
            displayCategory = (TextView) itemView.findViewById(R.id.txt_category);
            displayColor = (TextView) itemView.findViewById(R.id.txt_color);
            displayModifyCategory = (Button) itemView.findViewById(R.id.btn_modifiy_cat);
            displayDelete = (ImageView) itemView.findViewById(R.id.img_cat);
            root = itemView.findViewById(R.id.root);
        }
    }

    public interface CategoryAdapterCallBack {

        void clicOnModifyCategory(Categorie categorie);

        void clicOnCategory(Categorie categorie);

        void clicOnDeleteCategory(Categorie categorie);
    }
}

