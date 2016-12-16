package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        // A modifier ///////////////////////////////////////////////////////////////////////////////
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = FragmentAccueil.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.Accueil) {
            fragmentClass = FragmentAccueil.class;
        }
        else if (id == R.id.Stock) {
            fragmentClass = FragmentStock.class;
        }
        else if (id == R.id.Bilan) {
            fragmentClass = FragmentBilan.class;
        }
        else if (id == R.id.Reglage) {
            fragmentClass = FragmentReglage.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "fragment").commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();

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
