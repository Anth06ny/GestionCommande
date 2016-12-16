package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.anthony.gestionstock.R;
import com.example.anthony.gestionstock.controller.fragment.FragmentAccueil;
import com.example.anthony.gestionstock.controller.fragment.FragmentBilan;
import com.example.anthony.gestionstock.controller.fragment.FragmentReglage;
import com.example.anthony.gestionstock.controller.fragment.FragmentStock;
import com.example.anthony.gestionstock.model.sharedPreference.UserSession;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Fragment currentFragment; //fragment en cours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        ////////////////////////////////////////////////////////////////////////////////////////////

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //Changement des couleurs des icones du menu
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().findItem(R.id.Accueil).getIcon().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.Stock).getIcon().setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.Bilan).getIcon().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        navigationView.getMenu().findItem(R.id.Reglage).getIcon().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_IN);

        //On lance la page d'acceuil par defaut en simulant un clic sur le bouton acceuil
        //our qu'il soit selectionné dans le menu au passage
        navigationView.getMenu().performIdentifierAction(R.id.Accueil, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            //On ne sort de l'application que si on est sur l'ecran d'accueil
            if (currentFragment.getTag().equalsIgnoreCase(R.id.Accueil + "")) {
                super.onBackPressed();
            }
            //sinon on revient dessus
            else {
                navigationView.getMenu().performIdentifierAction(R.id.Accueil, 0);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Close the navigation drawer
        mDrawer.closeDrawers();

        //On ne change de fragment que si c'est un different
        if (currentFragment == null || !currentFragment.getTag().equalsIgnoreCase("" + item.getItemId())) {
            if (item.getItemId() == R.id.Accueil) {
                currentFragment = new FragmentAccueil();
            }
            else if (item.getItemId() == R.id.Stock) {
                currentFragment = new FragmentStock();
            }
            else if (item.getItemId() == R.id.Bilan) {
                currentFragment = new FragmentBilan();
            }
            else if (item.getItemId() == R.id.Reglage) {
                currentFragment = new FragmentReglage();
            }
            // Insert the fragment by replacing any existing fragment
            //Je mets en tag l'item id pour pouvoir facilement identifier le fragment courant
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, currentFragment, "" + item.getItemId()).commit();

            // Highlight the selected item has been done by NavigationView
            item.setChecked(true);
            // Set action bar title
            setTitle(item.getTitle());
        }

        return true;
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedpreferences = getSharedPreferences(UserSession.PREFER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        super.onStop();
    }
}
