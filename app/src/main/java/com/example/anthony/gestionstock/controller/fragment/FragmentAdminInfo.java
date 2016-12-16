package com.example.anthony.gestionstock.controller.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.model.sharedPreference.SharedPreferenceUtils;

public class FragmentAdminInfo extends Fragment {
    private View v;
    private EditText userName;
    private EditText password;
    private Button btn_conection;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SharedPreferenceUtils session;

    public FragmentAdminInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.admin_info, container, false);
        // Recupere la vue pour ce fragment
        initUI(v);

        return v;
    }

    private void initUI(View v) {
        userName = (EditText) v.findViewById(R.id.user_name);
        password = (EditText) v.findViewById(R.id.mot_de_passe);
        btn_conection = (Button) v.findViewById(R.id.btn_conection_admin);

        sharedPreferences = getActivity().getSharedPreferences("Admin", 0);
        editor = sharedPreferences.edit();

        btn_conection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                String mdp = password.getText().toString();

                if (userName.getText().length() <= 0) {
                    Toast.makeText(getActivity(), "Entrez le nom", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().length() <= 0) {
                    Toast.makeText(getActivity(), "Entrez le Mot de passe", Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putString("Name", name);
                    editor.putString("MotDePasse", mdp);
                    editor.commit();
                }


                /*getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        // lancer un autre fragment
                        .replace(R.id.flContent, fragmentLogin, "string")
                        .addToBackStack(null)
                        .commit();
                Intent ob = new Intent(getContext(), Login.class);
                startActivity(ob);*/
            }
        });
    }
}
