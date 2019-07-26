package com.example.mydegree;

import android.content.Intent;
import android.os.Bundle;

import com.example.mydegree.Plan.Plan;
import com.example.mydegree.Saved.SavedItems;
import com.example.mydegree.Search.Search;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.View;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.MenuItem;

//IGNORE THIS ACTIVITY ITS HERE just in case

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    protected int menuSelected = 0;

    protected static final int NAVDRAWER_LAUNCH_DELAY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FLOATING ACTION BUTTON STUFFS
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This will eventually lead to add plan?", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        //NAV DRAWER STUFFS
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //customise
        navigationView.setCheckedItem(R.id.menuprogram);

        //this removes the activity transition animation and the screen will just appear when loaded
        overridePendingTransition(0,0);



    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Handler mHandler = new Handler();
        if (id == R.id.menusearch) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), Search.class));
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.menuplan) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), Plan.class));
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.menuprofile) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),Profile.class));
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.menuprogram) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.menusaved) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), SavedItems.class));
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.menusettings) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),Settings.class));
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

