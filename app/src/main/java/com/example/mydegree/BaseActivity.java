package com.example.mydegree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //THIS ACTIVITY IS A TEMPLATE FOR ACTIVITIES THAT NEED THE NAV MENU

    protected DrawerLayout mDrawer;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

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
        Intent intent = null;
        if (id == R.id.menusearch) {
            intent = new Intent(this, Search.class);
        } else if (id == R.id.menuplan) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuprofile) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuprogram) {
            intent = new Intent(this, MainActivity.class);
        } else if (id == R.id.menusaved) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menusettings) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        }

        if(intent!=null){
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
