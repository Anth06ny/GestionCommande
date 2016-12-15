package com.example.anthony.gestionstock.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;

public class DialogLogin extends DialogFragment {

    private EditText editTextMotDePasse;
    private DialogLoginCallBack dialogLoginCallBack;
    private SharedPreferences sharedPreferences;
    private static final String PREFER_NAME = "Admin";
    private UserSession userSession;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_login, null);

        //On recupere les elements graphique
        editTextMotDePasse = (EditText) alertDialogView.findViewById(R.id.mot_de_passe_login);
        sharedPreferences = getActivity().getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        userSession = new UserSession(getContext());

        builder.setView(alertDialogView).setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogLoginCallBack.onCancelLogin(true);
                    }
                }

                            );

        final AlertDialog dialog = builder.create();
        dialog.show();

        //On override la methode onClick du bouton positif pour eviter que la dialog se ferme sans que les conditions soit respecter
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean conditionRemplie = false;
                String motdepasse = editTextMotDePasse.getText().toString();

                //On verifie que le champ mot de passe soit remplie
                if (motdepasse.trim().length() > 0) {
                    String uPsw = null;

                    if (sharedPreferences.contains("MotDePasse")) {
                        uPsw = sharedPreferences.getString("MotDePasse", "");
                    }

                    //On compare le mot de passe saisie avec celui qui est sauvegarde
                    if (motdepasse.equals((uPsw))) {
                        userSession.createUserLoginSession(uPsw);
                        conditionRemplie = true;
                    }
                    else {
                        // password doesn't match&
                        Toast.makeText(getContext(),
                                "Mot de passe incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    // user didn't entered password
                    Toast.makeText(getContext(),
                            "Entrer le mot de passe",
                            Toast.LENGTH_LONG).show();
                }
                if (conditionRemplie) {
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }

    interface DialogLoginCallBack {
        void onCancelLogin(Boolean cancel);
    }

    public void setDialogLoginCallBack(DialogLoginCallBack dialogLoginCallBack) {
        this.dialogLoginCallBack = dialogLoginCallBack;
    }
}
