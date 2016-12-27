package com.example.anthony.gestionstock.controller;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.controller.fragment.FragmentAccueil;
import com.example.anthony.gestionstock.controller.fragment.FragmentBilan;
import com.example.anthony.gestionstock.controller.fragment.FragmentReglage;
import com.example.anthony.gestionstock.controller.fragment.FragmentStock;
import com.example.anthony.gestionstock.model.bdd.ProduitBddManager;
import com.example.anthony.gestionstock.model.sharedPreference.SharedPreferenceUtils;
import com.example.anthony.gestionstock.vue.AlertDialogutils;

import org.apache.commons.lang3.StringUtils;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AlertDialogutils.LoginDialogResponse, View.OnClickListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Fragment currentFragment; //fragment en cours
    private boolean securiseMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        securiseMenu = true;
        setSupportActionBar(toolbar);

        ////////////////////////////////////////////////////////////////////////////////////////////

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //toggle.setToolbarNavigationClickListener(this);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toggle.setToolbarNavigationClickListener(this);
        toolbar.setNavigationOnClickListener(this);

        //Changement des couleurs des icones du menu
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().findItem(R.id.Accueil).getIcon().setColorFilter(getResources().getColor(R.color.purple), PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.Stock).getIcon().setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.Bilan).getIcon().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.Reglage).getIcon().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.SecuriserMenu).getIcon().setColorFilter(getResources().getColor(R.color.light_blue), PorterDuff.Mode.SRC_IN);

        //On lance la page d'acceuil par defaut (un controle est fait dans la méthode)
        gotoAccueil();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            //On ne sort de l'application que si on est sur l'ecran d'accueil
            if (currentFragment == null || currentFragment.getTag().equalsIgnoreCase(R.id.Accueil + "")) {
                super.onBackPressed();
            }
            //sinon on revient dessus
            else {
                gotoAccueil();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Close the navigation drawer
        mDrawer.closeDrawers();

        switch (item.getItemId()) {
            case R.id.Accueil:
                gotoAccueil();
                break;

            case R.id.Reglage:
                gotoReglage(true);
                break;

            case R.id.Bilan:
                gotoBilan();
                break;

            case R.id.Stock:
                gotoStock();
                break;

            case R.id.SecuriserMenu:
                securiseMenu = true;
                onOptionsItemSelected(navigationView.getMenu().findItem(R.id.Accueil));
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        //Est ce qu'on a le droit ?
        if (securiseMenu) {
            //On demande le mot de passe
            AlertDialogutils.loginDialog(this, this);
        }
        else {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    private void changeFragment(int fragmentId) {
        //le menu correspondant au fragment
        MenuItem item = navigationView.getMenu().findItem(fragmentId);

        //On ne change de fragment que si c'est un different
        if (currentFragment == null || !currentFragment.getTag().equalsIgnoreCase("" + item.getItemId())) {
            if (fragmentId == R.id.Accueil) {
                currentFragment = new FragmentAccueil();
            }
            else if (fragmentId == R.id.Stock) {
                currentFragment = new FragmentStock();
            }
            else if (fragmentId == R.id.Bilan) {
                currentFragment = new FragmentBilan();
            }
            else if (fragmentId == R.id.Reglage) {
                currentFragment = new FragmentReglage();
            }
            // Insert the fragment by replacing any existing fragment
            //Je mets en tag l'item id pour pouvoir facilement identifier le fragment courant
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, currentFragment, "" + item.getItemId()).commit();
        }

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        securiseMenu = true;
    }


    /* ---------------------------------
    // Redirection
    // -------------------------------- */

    public void gotoAccueil() {
        if (checkApplicationReadyToUse()) {
            changeFragment(R.id.Accueil);
        }
    }

    public void gotoStock() {
        if (checkApplicationReadyToUse()) {
            changeFragment(R.id.Stock);
        }
    }

    public void gotoBilan() {
        if (checkApplicationReadyToUse()) {
            changeFragment(R.id.Bilan);
        }
    }

    public void gotoReglage(boolean askPassword) {
        changeFragment(R.id.Reglage);
    }

    /**
     * Test si l'application est prette à utiliser c'est à dire s'il y a au moins 1 produit et le mot de passe admin
     */
    private boolean checkApplicationReadyToUse() {
        //On regarde s'il y a le mot de passe réglage
        if (StringUtils.isBlank(SharedPreferenceUtils.getPassword())) {
            //sinon on affiche la demande de mot de passe
            AlertDialogutils.askPassword(this, this);
            return false;
        }
        //On regarde s'il y a au moins un produit dans la base
        else if (!ProduitBddManager.isOneProduct()) {
            Toast.makeText(DrawerActivity.this, "Il faut au moins un produit avant de pouvoir utiliser l'application", Toast.LENGTH_SHORT).show();
            //on redirige sur le réglage
            gotoReglage(true);
            return false;
        }
        return true;
    }

    /* ---------------------------------
    // Callback Ecran login
    // -------------------------------- */

    @Override
    public void loginDialogSuccess() {
        securiseMenu = false;
        //on ouvre le menu
        mDrawer.openDrawer(GravityCompat.START);
    }
}
