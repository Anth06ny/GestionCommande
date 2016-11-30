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

/**
 * Created by Axel legué on 23/11/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Categorie> category_bean;
    private CategoryAdapterCallBack categoryAdapterCallBack;

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
    public void onBindViewHolder(final CategoryAdapter.ViewHolder holder, int position) {
        final Categorie categoriebean = category_bean.get(position);
        holder.displayCategory.setText(categoriebean.getNom());
        holder.displayColor.setText(categoriebean.getCouleur());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoriebean.setSelected(!categoriebean.isSelected());

                if (categoriebean.isSelected()) {
                    holder.displayModifyCategory.setVisibility(View.VISIBLE);
                    holder.displayDelete.setVisibility(View.VISIBLE);
                }
                else {
                    holder.displayModifyCategory.setVisibility(View.INVISIBLE);
                    holder.displayDelete.setVisibility(View.INVISIBLE);
                }

                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.displayModifyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryAdapterCallBack != null) {
                    categoryAdapterCallBack.clicOnModify(categoriebean);
                }
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
        void clicOnDeleteCallback(Categorie categorie);

        void clicOnModify(Categorie categorie);
    }
}

