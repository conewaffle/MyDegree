package com.example.mydegree;

import androidx.annotation.NonNull;
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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mydegree.Plan.Plan;
import com.example.mydegree.Saved.SavedItems;
import com.example.mydegree.Search.Search;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //THIS ACTIVITY IS A TEMPLATE FOR ACTIVITIES THAT NEED THE NAV MENU

    protected DrawerLayout mDrawer;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;
    protected NavigationView navigationView;
    protected static final int NAVDRAWER_LAUNCH_DELAY = 200;
    protected CoordinatorLayout coordinatorLayout;
    protected FrameLayout frameLayout;
    protected TextView welcome;

    protected FirebaseAuth auth;
    protected DatabaseReference databaseReference;
    protected FirebaseDatabase firebaseDatabase;
    protected String userId;

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
        View view = navigationView.getHeaderView(0);
        welcome = view.findViewById(R.id.welcome);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            userId = user.getUid();

            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String message = "Welcome, " + dataSnapshot.child("name").getValue().toString() + "!";
                    welcome.setText(message);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }




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
                    startActivity(new Intent(getApplicationContext(),Program.class));
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
