package com.example.mydegree.Plan;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.mydegree.Plan.AddPlan.RESULT_MAJOR;
import static com.example.mydegree.Plan.AddPlan.RESULT_PROG;

public class Plan extends BaseActivity {

    private RecyclerView r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12;
    private PlanAdapter p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
    private ImageButton b11, b12, b13, b21, b22, b23, b31, b32, b33, b41, b42, b43;
    private CardView c1, c2, c3, c4;
    private FloatingActionButton fab;
    private static final int PICK_PROGRAM_REQUEST = 1;
    private String programCode, majorName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_plan, frameLayout, true);

        //region setting up fab
        fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Plan.this, AddPlan.class);
                startActivityForResult(pickIntent, PICK_PROGRAM_REQUEST);
            }
        });
        //endregion
        //region  setting up recyclers
        r1 = findViewById(R.id.r1);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r2 = findViewById(R.id.r2);
        r2.setHasFixedSize(true);
        r2.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r3 = findViewById(R.id.r3);
        r3.setHasFixedSize(true);
        r3.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r4 = findViewById(R.id.r4);
        r4.setHasFixedSize(true);
        r4.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r5 = findViewById(R.id.r5);
        r5.setHasFixedSize(true);
        r5.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r6 = findViewById(R.id.r6);
        r6.setHasFixedSize(true);
        r6.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r7 = findViewById(R.id.r7);
        r7.setHasFixedSize(true);
        r7.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r8 = findViewById(R.id.r8);
        r8.setHasFixedSize(true);
        r8.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r9 = findViewById(R.id.r9);
        r9.setHasFixedSize(true);
        r9.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r10 = findViewById(R.id.r10);
        r10.setHasFixedSize(true);
        r10.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r11 = findViewById(R.id.r11);
        r11.setHasFixedSize(true);
        r11.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        r12 = findViewById(R.id.r12);
        r12.setHasFixedSize(true);
        r12.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        //endregion
        //region instantiating adapters
        p1 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p2 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p3 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p4 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p5 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p6 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p7 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p8 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p9 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p10 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p11 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        p12 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        //endregion
        //region bind recycler to adapter
        r1.setAdapter(p1);
        r2.setAdapter(p2);
        r3.setAdapter(p3);
        r4.setAdapter(p4);
        r5.setAdapter(p5);
        r6.setAdapter(p6);
        r7.setAdapter(p7);
        r8.setAdapter(p8);
        r9.setAdapter(p9);
        r10.setAdapter(p10);
        r11.setAdapter(p11);
        r12.setAdapter(p12);
        //endregion
        //region card instantiation
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        //endregion
        //region customise this for each nav menu destination
        navigationView.setCheckedItem(R.id.menuplan);
        setTitle("myPlan");
        //endregion
        //Do the rest as you want for each activity


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PROGRAM_REQUEST){
            if(resultCode==RESULT_OK){
                programCode = data.getStringExtra(RESULT_PROG);
                Toast.makeText(Plan.this, "program is " + programCode,Toast.LENGTH_SHORT).show();
                majorName = data.getStringExtra(RESULT_MAJOR);
                if(majorName!=null){
                    Toast.makeText(Plan.this, "Major is " + majorName + programCode, Toast.LENGTH_SHORT).show();
                }



                //DO SOMETHING HERE WITH THE PROGRAM/MAJOR , maybe create a bin of courses???
                }
            }
        }

    //region extending baseactivity things
    //THIS METHOD MUST BE ADDED TO ALL NAV MENU DESTINATIONS
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //THIS METHOD MUST BE ADDED TO ALL NAV MENU DESTINATIONS, CUSTOMISE FOR IF ID=R.ID.MENU____
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menuplan){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onNavigationItemSelected(item);
        }
        return true;
    }
    //endregion


}
