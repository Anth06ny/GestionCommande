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

public class Login extends AppCompatActivity {

    private static final String PREFER_NAME = "Admin";

    private Button btn_login;
    private EditText username;
    private EditText password;
    private SharedPreferences sharedPreferences;
    private UserSession userSession;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.login_info);

        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.mot_de_passe);
        btn_login = (Button) findViewById(R.id.btn_conection_admin);
        userSession = new UserSession();
        //TODO modifier avec session
        Toast.makeText(this, "User Login Status" + userSession.isUserLoggedIn(), Toast.LENGTH_SHORT).show();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomUtilisateur = username.getText().toString();
                String motdepasse = password.getText().toString();

                if (nomUtilisateur.trim().length() > 0 && motdepasse.trim().length() > 0) {
                    String uName = null;
                    String uPsw = null;

                    if (sharedPreferences.contains("Name")) {
                        uName = sharedPreferences.getString("Name", "");
                    }
                    if (sharedPreferences.contains("MotDePasse")) {
                        uPsw = sharedPreferences.getString("MotDePasse", "");
                    }

                    if (nomUtilisateur.equals(uName) && motdepasse.equals((uPsw))) {
                        userSession.createUserLoginSession(uName, uPsw);

                        Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();
                    }
                    else {
                        // username / password doesn't match&
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                            "Please enter username and password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
