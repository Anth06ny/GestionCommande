package com.example.anthony.gestionstock.vue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.gestionstock.R;

import java.util.List;

import greendao.Categorie;

/**
 * Created by Axel legué on 28/11/2016.
 */
// adapteur de la liste déroulante.
public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    private Activity activity;
    private List<Categorie> listCategories;

    public SpinnerAdapter(Activity activity, List<Categorie> listCategories) {
        this.activity = activity;
        this.listCategories = listCategories;

    }

    @Override
    public int getCount() {
        return listCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return listCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listCategories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View spinView;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            spinView = inflater.inflate(R.layout.cellule_spinner, null);
        }
        else {
            spinView = convertView;
        }
        Categorie categorie = listCategories.get(position);

        TextView tv_categorie = (TextView) spinView.findViewById(R.id.tv_categorie);
        ImageView iv_color = (ImageView) spinView.findViewById(R.id.iv_color);

        tv_categorie.setText(categorie.getNom());
        iv_color.setColorFilter(Integer.parseInt(categorie.getCouleur()));
        return spinView;
    }
}
