package vue;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.example.anthony.gestionstock.R;

/**
 * Created by Anthony on 15/12/2016.
 */
public class AlertDialogutils {

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
