package com.example.anthony.gestionstock.vue.custom_composant.material_color_picker;

/**
 * Created by Anthony on 13/12/2016.
 */

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.anthony.gestionstock.R;

import java.util.ArrayList;
import java.util.List;

public class ColorChooserDialog extends DialogFragment {

    private AppCompatImageButton one;
    private AppCompatImageButton two;
    private AppCompatImageButton three;
    private AppCompatImageButton four;
    private AppCompatImageButton five;
    private AppCompatImageButton six;
    private AppCompatImageButton seven;
    private AppCompatImageButton eight;
    private AppCompatImageButton nine;
    private AppCompatImageButton ten;
    private AppCompatImageButton eleven;
    private AppCompatImageButton twelve;
    private AppCompatImageButton thirteen;
    private AppCompatImageButton fourteen;
    private AppCompatImageButton fifteen;
    private AppCompatImageButton sixteen;
    private AppCompatImageButton seventeen;
    private AppCompatImageButton eighteen;
    private AppCompatImageButton nineteen;
    private AppCompatImageButton twenty;

    private List<Integer> colors;
    private List<ImageButton> buttons;

    private ColorListener myColorListener;
    private Integer selectedColor = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.color_picker_dialog, null);

        builder.setView(view);

        one = (AppCompatImageButton) view.findViewById(R.id.b1);
        two = (AppCompatImageButton) view.findViewById(R.id.b2);
        three = (AppCompatImageButton) view.findViewById(R.id.b3);
        four = (AppCompatImageButton) view.findViewById(R.id.b4);
        five = (AppCompatImageButton) view.findViewById(R.id.b5);
        six = (AppCompatImageButton) view.findViewById(R.id.b6);
        seven = (AppCompatImageButton) view.findViewById(R.id.b7);
        eight = (AppCompatImageButton) view.findViewById(R.id.b8);
        nine = (AppCompatImageButton) view.findViewById(R.id.b9);
        ten = (AppCompatImageButton) view.findViewById(R.id.b10);
        eleven = (AppCompatImageButton) view.findViewById(R.id.b11);
        twelve = (AppCompatImageButton) view.findViewById(R.id.b12);
        thirteen = (AppCompatImageButton) view.findViewById(R.id.b13);
        fourteen = (AppCompatImageButton) view.findViewById(R.id.b14);
        fifteen = (AppCompatImageButton) view.findViewById(R.id.b15);
        sixteen = (AppCompatImageButton) view.findViewById(R.id.b16);
        seventeen = (AppCompatImageButton) view.findViewById(R.id.b17);
        eighteen = (AppCompatImageButton) view.findViewById(R.id.b18);
        nineteen = (AppCompatImageButton) view.findViewById(R.id.b19);
        twenty = (AppCompatImageButton) view.findViewById(R.id.b20);

        colors = new ArrayList<>();
        colors.add(getContext().getResources().getColor(R.color.red));
        colors.add(getContext().getResources().getColor(R.color.pink));
        colors.add(getContext().getResources().getColor(R.color.purple));
        colors.add(getContext().getResources().getColor(R.color.deep_purple));
        colors.add(getContext().getResources().getColor(R.color.indigo));
        colors.add(getContext().getResources().getColor(R.color.blue));
        colors.add(getContext().getResources().getColor(R.color.light_blue));
        colors.add(getContext().getResources().getColor(R.color.cyan));
        colors.add(getContext().getResources().getColor(R.color.teal));
        colors.add(getContext().getResources().getColor(R.color.green));
        colors.add(getContext().getResources().getColor(R.color.light_green));
        colors.add(getContext().getResources().getColor(R.color.lime));
        colors.add(getContext().getResources().getColor(R.color.yellow));
        colors.add(getContext().getResources().getColor(R.color.amber));
        colors.add(getContext().getResources().getColor(R.color.orange));
        colors.add(getContext().getResources().getColor(R.color.deep_orange));
        colors.add(getContext().getResources().getColor(R.color.brown));
        colors.add(getContext().getResources().getColor(R.color.green));
        colors.add(getContext().getResources().getColor(R.color.blue_grey));
        colors.add(getContext().getResources().getColor(R.color.black));

        buttons = new ArrayList<>();
        buttons.add(one);
        buttons.add(two);
        buttons.add(three);
        buttons.add(four);
        buttons.add(five);
        buttons.add(six);
        buttons.add(seven);
        buttons.add(eight);
        buttons.add(nine);
        buttons.add(ten);
        buttons.add(eleven);
        buttons.add(twelve);
        buttons.add(thirteen);
        buttons.add(fourteen);
        buttons.add(fifteen);
        buttons.add(sixteen);
        buttons.add(seventeen);
        buttons.add(eighteen);
        buttons.add(nineteen);
        buttons.add(twenty);

        builder.setTitle(R.string.dialog_colorpicker_title);

        //ICON
        Drawable icon = getContext().getResources().getDrawable(R.drawable.ic_format_color_fill_black_48dp);
        icon.setColorFilter(getContext().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        builder.setIcon(icon);

        builder.setNegativeButton(R.string.annuler, null);

        Colorize();

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (myColorListener != null) {
                myColorListener.OnColorClick(v, (int) v.getTag());
            }
            dismiss();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //On définit la taille de la fenetre, sinon un bug fait qu'elle est trop large
        int dialogWidth = getResources().getDimensionPixelSize(R.dimen.color_picker_width);
        getDialog().getWindow().setLayout(dialogWidth, getDialog().getWindow().getAttributes().height);
    }

    private void Colorize() {

        //On met un fond gris sur la couleur préséléctionné et rien sur les autre
        for (int i = 0; i < buttons.size(); i++) {
            ImageButton ib = buttons.get(i);
            int color = colors.get(i);
            ib.setTag(color);
            ib.setOnClickListener(listener);
            if (selectedColor != null && color == selectedColor) {
                ib.setBackgroundColor(getContext().getResources().getColor(R.color.selected_cellule_bg));
            }
            ib.setVisibility(View.INVISIBLE);
            ib.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

        animate();
    }

    private void animate() {
        Log.e("animate", "true");
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                Log.e("animator 1", "r");
                animator(one);
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                animator(two);
                animator(six);
            }
        };

        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                animator(three);
                animator(seven);
                animator(eleven);
            }
        };

        Runnable r4 = new Runnable() {
            @Override
            public void run() {
                animator(four);
                animator(eight);
                animator(twelve);
                animator(sixteen);
            }
        };

        Runnable r5 = new Runnable() {
            @Override
            public void run() {
                animator(five);
                animator(nine);
                animator(thirteen);
                animator(seventeen);
            }
        };

        Runnable r6 = new Runnable() {
            @Override
            public void run() {
                animator(ten);
                animator(fourteen);
                animator(eighteen);
            }
        };

        Runnable r7 = new Runnable() {
            @Override
            public void run() {
                animator(fifteen);
                animator(nineteen);
            }
        };

        Runnable r8 = new Runnable() {
            @Override
            public void run() {
                animator(twenty);
            }
        };

        Runnable r9 = new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                animation.setInterpolator(new AccelerateInterpolator());
                animation.start();
            }
        };

        android.os.Handler handler = new android.os.Handler();
        int counter = 85;
        handler.postDelayed(r1, counter);
        handler.postDelayed(r2, counter * 2);
        handler.postDelayed(r3, counter * 3);
        handler.postDelayed(r4, counter * 4);
        handler.postDelayed(r5, counter * 5);
        handler.postDelayed(r6, counter * 6);
        handler.postDelayed(r7, counter * 7);
        handler.postDelayed(r8, counter * 8);
        handler.postDelayed(r9, counter * 9);
    }

    private void animator(final ImageButton imageButton) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.color_item);
        animation.setInterpolator(new AccelerateInterpolator());
        imageButton.setAnimation(animation);
        imageButton.setVisibility(View.VISIBLE);
        animation.start();
    }

    public void setColorListener(ColorListener listener) {
        this.myColorListener = listener;
    }

    public void setSelectedColor(Integer selectedColor) {
        this.selectedColor = selectedColor;
    }

    public interface ColorListener {
        void OnColorClick(View v, int color);
    }
}

