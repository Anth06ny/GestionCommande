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

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;

import greendao.Categorie;

/**
 * Created by Axel legué on 23/11/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static enum CATEGORY_TYPE {
        REGLAGE, ACCUEIL
    }

    private ArrayList<Categorie> category_bean;
    private CATEGORY_TYPE category_type;
    private CategoryAdapterCallBack categoryAdapterCallBack;

    public CategoryAdapter(ArrayList<Categorie> category_bean, CATEGORY_TYPE category_type, CategoryAdapterCallBack categoryAdapterCallBack) {
        this.category_bean = category_bean;
        this.category_type = category_type;
        this.categoryAdapterCallBack = categoryAdapterCallBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View v = null;
        switch (category_type) {

            case REGLAGE:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_category_reglage, vg, false);
                return new CategoryAdapter.ViewHolderReglage(v);
            case ACCUEIL:
                v = LayoutInflater.from(vg.getContext()).inflate(R.layout.cellule_category_accueil, vg, false);
                return new CategoryAdapter.ViewHolderAcceuil(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder vh, final int position) {

        final Categorie categoriebean = category_bean.get(position);

        switch (category_type) {
            case REGLAGE:
                CategoryAdapter.ViewHolderReglage holder = (ViewHolderReglage) vh;
                holder.displayCategory.setText(categoriebean.getNom());
                holder.displayColor.setColorFilter(Integer.parseInt(categoriebean.getCouleur()));
                holder.displayModifyCategory.setVisibility(View.INVISIBLE);
                holder.displayDelete.setVisibility(View.INVISIBLE);

                if (categoriebean.isSelected()) {
                    holder.displayModifyCategory.setVisibility(View.VISIBLE);
                    holder.displayDelete.setVisibility(View.VISIBLE);
                    holder.cv_bg.setCardBackgroundColor(holder.cv_bg.getResources().getColor(R.color.selected_cellule_bg));
                }
                else {
                    holder.displayModifyCategory.setVisibility(View.INVISIBLE);
                    holder.displayDelete.setVisibility(View.INVISIBLE);
                    holder.cv_bg.setCardBackgroundColor(holder.cv_bg.getResources().getColor(R.color.unselected_cellule_bg));
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
                break;
            case ACCUEIL:
                CategoryAdapter.ViewHolderAcceuil viewHolderAcceuil = (ViewHolderAcceuil) vh;
                viewHolderAcceuil.root.setText(categoriebean.getNom());
                viewHolderAcceuil.root.setSupportBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(categoriebean.getCouleur())));
                viewHolderAcceuil.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (categoryAdapterCallBack != null) {
                            categoryAdapterCallBack.clicOnCategory(categoriebean);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return category_bean.size();
    }

    public class ViewHolderReglage extends RecyclerView.ViewHolder {
        public TextView displayCategory;
        public ImageView displayColor;
        public AppCompatButton displayModifyCategory;
        public ImageView displayDelete;
        public View root;
        public CardView cv_bg;

        public ViewHolderReglage(View itemView) {
            super(itemView);
            displayCategory = (TextView) itemView.findViewById(R.id.txt_category);
            displayColor = (ImageView) itemView.findViewById(R.id.txt_color);
            displayModifyCategory = (AppCompatButton) itemView.findViewById(R.id.btn_modifiy_cat);
            displayDelete = (ImageView) itemView.findViewById(R.id.img_cat);
            root = itemView.findViewById(R.id.root);
            cv_bg = (CardView) itemView.findViewById(R.id.cv_bg);

            displayDelete.setColorFilter(displayDelete.getResources().getColor(R.color.red));
        }
    }

    public class ViewHolderAcceuil extends RecyclerView.ViewHolder {
        public AppCompatButton root;

        public ViewHolderAcceuil(View itemView) {
            super(itemView);
            root = (AppCompatButton) itemView.findViewById(R.id.root);
        }
    }

    public interface CategoryAdapterCallBack {

        void clicOnModifyCategory(Categorie categorie);

        void clicOnCategory(Categorie categorie);

        void clicOnDeleteCategory(Categorie categorie);
    }
}

