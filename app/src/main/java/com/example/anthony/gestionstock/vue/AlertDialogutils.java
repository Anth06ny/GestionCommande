package com.example.anthony.gestionstock.vue;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.vue.login_dialog.PassCodeView;

/**
 * Created by Anthony on 15/12/2016.
 */
public class AlertDialogutils {

    public static void loginDialog(final Context context) {

        //On creer un alert dialog pour confirmer la suppression du produit
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        //On set tous les elements et on display la dialog box
        alertDialogBuilder.setTitle(R.string.dialog_connexion_titre);
        //alertDialogBuilder.setView(R.layout.dialog_login);

        //alertDialogBuilder.setView(R);
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_report_problem_white_48dp);
        drawable.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        alertDialogBuilder.setIcon(drawable);
        AlertDialog alertDialog = alertDialogBuilder.setCancelable(true).create();

        View view = alertDialog.getLayoutInflater().inflate(R.layout.dialog_login, null);
        final PassCodeView passCodeView = (PassCodeView) view.findViewById(R.id.pass_code_view);
        passCodeView.setOnTextChangeListener(new PassCodeView.TextChangeListener() {
            @Override
            public void onTextChanged(String text) {
                if (text.length() == 4) {
                    if (text.equals("1234")) {
                        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        passCodeView.setError(true);
                        Toast.makeText(context, "pas ok", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.setView(view);

        //la font
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Font-Bold.ttf");
        passCodeView.setTypeFace(typeFace);
        alertDialog.show();
    }

    /* ---------------------------------
    // OKDialog
    // -------------------------------- */

    public static void showOkDialog(Context context, @StringRes int stringResId, @StringRes int message, DialogInterface.OnClickListener onClickListener) {
        showOkDialog(context, context.getString(stringResId), context.getString(message), onClickListener);
    }

    public static void showOkDialog(Context context, @StringRes int stringResId, String message, DialogInterface.OnClickListener onClickListener) {
        showOkDialog(context, context.getString(stringResId), message, onClickListener);
    }

    /**
     * Affiche une fenetre avec juste un bouton Valider
     *
     * @param context
     * @param title
     * @param message
     * @param onClickListener
     */
    public static void showOkDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        //On creer un alert dialog pour confirmer la suppression du produit
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        //On set tous les elements et on display la dialog box
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_report_problem_white_48dp);
        drawable.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        alertDialogBuilder.setIcon(drawable);
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton(R.string.valider, onClickListener).create().show();
    }

     /* ---------------------------------
    // OK Cancel Dialog
    // -------------------------------- */

    public static void showOkCancelDialog(Context context, @StringRes int stringResId, @StringRes int message, DialogInterface.OnClickListener onClickListener) {
        showOkCancelDialog(context, context.getString(stringResId), context.getString(message), onClickListener);
    }

    public static void showOkCancelDialog(Context context, @StringRes int stringResId, String message, DialogInterface.OnClickListener onClickListener) {
        showOkCancelDialog(context, context.getString(stringResId), message, onClickListener);
    }

    public static void showOkCancelDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        //On creer un alert dialog pour confirmer la suppression du produit
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        //On set tous les elements et on display la dialog box
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_report_problem_white_48dp);
        drawable.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        alertDialogBuilder.setIcon(drawable);
        alertDialogBuilder.setCancelable(true).setNegativeButton(R.string.annuler, null)
                .setPositiveButton(R.string.valider, onClickListener).create().show();
    }
}
