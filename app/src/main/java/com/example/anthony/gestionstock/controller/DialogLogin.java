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
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                String motdepasse = editTextMotDePasse.getText().toString();

                // Validate if username, password is filled
                if (motdepasse.trim().length() > 0) {
                    String uPsw = null;

                    if (sharedPreferences.contains("MotDePasse")) {
                        uPsw = sharedPreferences.getString("MotDePasse", "");
                    }

                    if (motdepasse.equals((uPsw))) {
                        userSession.createUserLoginSession(uPsw);
                        wantToCloseDialog = true;
                    }
                    else {
                        // password doesn't match&
                        Toast.makeText(getContext(),
                                "Password is incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    // user didn't entered password
                    Toast.makeText(getContext(),
                            "Please enter password",
                            Toast.LENGTH_LONG).show();
                }
                if (wantToCloseDialog) {
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
