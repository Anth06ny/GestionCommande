package com.example.anthony.gestionstock.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;

/**
 * Created by Axel legué on 13/12/2016.
 */

public class AdminInfo extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button btn_conection;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UserSession session;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.admin_info);

        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.mot_de_passe);
        btn_conection = (Button) findViewById(R.id.btn_conection_admin);

        sharedPreferences = getApplicationContext().getSharedPreferences("Admin", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btn_conection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                String mdp = password.getText().toString();

                if (userName.getText().length() <= 0) {
                    Toast.makeText(AdminInfo.this, "Entrez le nom", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().length() <= 0) {
                    Toast.makeText(AdminInfo.this, "Entrez le Mot de passe", Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putString("Name", name);
                    editor.putString("MotDePasse", mdp);
                    editor.commit();
                }

                Intent ob = new Intent(AdminInfo.this, Login.class);
                startActivity(ob);
            }
        });
    }
}
