package com.example.anthony.gestionstock.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.anthony.gestionstock.R;

public class DialogLogin extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_produit, null);


        return super.onCreateDialog(savedInstanceState);
    }
}
