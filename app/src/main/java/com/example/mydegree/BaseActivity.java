package com.example.mydegree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.mydegree.Search.Search;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //THIS ACTIVITY IS A TEMPLATE FOR ACTIVITIES THAT NEED THE NAV MENU

    protected DrawerLayout mDrawer;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;
    protected NavigationView navigationView;
    protected static final int NAVDRAWER_LAUNCH_DELAY = 200;
    protected CoordinatorLayout coordinatorLayout;
    protected FrameLayout frameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorBase);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        //this removes the activity transition animation and the screen will just appear when loaded
        overridePendingTransition(0,0);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                    startActivity(new Intent(getApplicationContext(),Plan.class));
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
                    startActivity(new Intent(getApplicationContext(),Program.class));
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.menusaved) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),SavedItems.class));
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
