package com.example.mydegree.Plan;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.PlanInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.mydegree.Plan.AddPlan.RESULT_MAJOR;
import static com.example.mydegree.Plan.AddPlan.RESULT_NAME;
import static com.example.mydegree.Plan.AddPlan.RESULT_PROG;
import static com.example.mydegree.Plan.PickCourseFragment.FRAG_TERM;
import static com.example.mydegree.Plan.PickCourseFragment.FRAG_YEAR;
import static com.example.mydegree.Plan.PickCourseFragment.RESULT_COURSE;

public class Plan extends BaseActivity implements View.OnClickListener, PickCourseFragment.PickCoursesListener {

    private RecyclerView r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12;
    private PlanAdapter p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
    private ImageButton b11, b12, b13, b21, b22, b23, b31, b32, b33, b41, b42, b43;
    private ArrayList<com.example.mydegree.Room.Plan> ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10, ar11, ar12;
    private CardView c1, c2, c3, c4;
    private FloatingActionButton fab;
    private LinearLayout buttons1, buttons2, buttons3, buttons4;
    private int myPlanInfoId;
    public static final int PICK_PROGRAM_REQUEST = 1;
    public static final String PICK_FRAG_TAG = "pickFragTag";
    private String programCode, majorName, planName;
    public int buttonLastClick;
    private com.example.mydegree.Room.Plan temporaryItem;

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

        //region button setup and onclick
        b11 = findViewById(R.id.b11);
        b11.setOnClickListener(this);
        b12 = findViewById(R.id.b12);
        b12.setOnClickListener(this);
        b13 = findViewById(R.id.b13);
        b13.setOnClickListener(this);
        b21 = findViewById(R.id.b21);
        b21.setOnClickListener(this);
        b22 = findViewById(R.id.b22);
        b22.setOnClickListener(this);
        b23 = findViewById(R.id.b23);
        b23.setOnClickListener(this);
        b31 = findViewById(R.id.b31);
        b31.setOnClickListener(this);
        b32 = findViewById(R.id.b32);
        b32.setOnClickListener(this);
        b33 = findViewById(R.id.b33);
        b33.setOnClickListener(this);
        b41 = findViewById(R.id.b41);
        b41.setOnClickListener(this);
        b42 = findViewById(R.id.b42);
        b42.setOnClickListener(this);
        b43 = findViewById(R.id.b43);
        b43.setOnClickListener(this);
        //endregion

        //region setting up button linearlayout
        buttons1 = findViewById(R.id.buttons1);
        buttons2 = findViewById(R.id.buttons2);
        buttons3 = findViewById(R.id.buttons3);
        buttons4 = findViewById(R.id.buttons4);
        buttons1.setVisibility(View.GONE);
        buttons2.setVisibility(View.GONE);
        buttons3.setVisibility(View.GONE);
        buttons4.setVisibility(View.GONE);
        //endregion

        //region setting up arraylists
        ar1 = new ArrayList<>();
        ar2 = new ArrayList<>();
        ar3 = new ArrayList<>();
        ar4 = new ArrayList<>();
        ar5 = new ArrayList<>();
        ar6 = new ArrayList<>();
        ar7 = new ArrayList<>();
        ar8 = new ArrayList<>();
        ar9 = new ArrayList<>();
        ar10 = new ArrayList<>();
        ar11 = new ArrayList<>();
        ar12 = new ArrayList<>();
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
                //receiving data back and making Toasts to ensure its done
                programCode = data.getStringExtra(RESULT_PROG);
                majorName = data.getStringExtra(RESULT_MAJOR);
                if(majorName!=null){
                    Toast.makeText(Plan.this, "Major is " + majorName + programCode, Toast.LENGTH_SHORT).show();
                }
                planName = data.getStringExtra(RESULT_NAME);
                if(planName!=null){
                    if(!planName.isEmpty()){
                        setTitle(planName);
                    }
                }

                //DOING STUFF WITH THE DATA
                //if its 3979, its only 3 years, so make the 4th card disappear.
                if(programCode.equals("3979")){
                    c4.setVisibility(View.GONE);
                }

                PlanInfo toInsert = new PlanInfo(0, planName,programCode);

                new InsertPlanInfoTask().execute(toInsert);


                //DO SOMETHING HERE WITH THE PROGRAM/MAJOR , maybe create a bin of courses???

                //first make buttons visible
                buttons1.setVisibility(View.VISIBLE);
                buttons2.setVisibility(View.VISIBLE);
                buttons3.setVisibility(View.VISIBLE);
                buttons4.setVisibility(View.VISIBLE);
            }
        }
    }

    //LONG-CLICK PLAN TO DELETE THINGS....
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_remove:
                final int position = ((PlanAdapter)  r1.getAdapter()).getPosition();
                //((PlanAdapter)((RecyclerView) item.getActionView().getParent()).getAdapter()).getPosition();


                //remove plan from db

                Toast.makeText(Plan.this, "Course removed (not really though, but it  will be once we figure it out)", Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);
    }

    private void showPickDialog(String programCode, int year, int term){
        FragmentManager fm = getSupportFragmentManager();
        PickCourseFragment pickCourseFragment = PickCourseFragment.newInstance(programCode, year, term);
        pickCourseFragment.show(fm, PICK_FRAG_TAG);
    }

    @Override
    public void onFinishPick(Bundle bundle){
        String course = bundle.getString(RESULT_COURSE);
        int term = bundle.getInt(FRAG_TERM);
        int year = bundle.getInt(FRAG_YEAR);

        temporaryItem = new com.example.mydegree.Room.Plan(myPlanInfoId, year, term, course);

        new InsertPlanItemTask().execute(temporaryItem);

        //region populating recyclers based on the term/year of selection - NOT USED ANYMORE BUT KEEP CODE IN CASE
/*        if(year==1){
            switch(term){
                case 1:
                    ar1.add(temporaryItem);
                    p1.setPlan(ar1);
                    break;
                case 2:
                    ar2.add(temporaryItem);
                    p2.setPlan(ar2);
                    break;
                case 3:
                    ar3.add(temporaryItem);
                    p3.setPlan(ar3);
                    break;
            }
        } else if(year==2){
            switch(term) {
                case 1:
                    ar4.add(temporaryItem);
                    p4.setPlan(ar4);
                    break;
                case 2:
                    ar5.add(temporaryItem);
                    p5.setPlan(ar5);
                    break;
                case 3:
                    ar6.add(temporaryItem);
                    p6.setPlan(ar6);
                    break;
            }
        } else if(year==3){
            switch(term) {
                case 1:
                    ar7.add(temporaryItem);
                    p7.setPlan(ar7);
                    break;
                case 2:
                    ar8.add(temporaryItem);
                    p8.setPlan(ar8);
                    break;
                case 3:
                    ar9.add(temporaryItem);
                    p9.setPlan(ar9);
                    break;
            }
        } else if(year==4){
            switch(term) {
                case 1:
                    ar10.add(temporaryItem);
                    p10.setPlan(ar10);
                    break;
                case 2:
                    ar11.add(temporaryItem);
                    p11.setPlan(ar11);
                    break;
                case 3:
                    ar12.add(temporaryItem);
                    p12.setPlan(ar12);
                    break;
            }
        }*/
        //endregion
    }

    private void showToastCourseFull(){
        Toast.makeText(Plan.this, "You cannot place anymore courses in this term!", Toast.LENGTH_SHORT).show();
    }

    //handling + button clicks
    @Override
    public void onClick(View v) {
        buttonLastClick = v.getId();
        switch(v.getId()) {
            case R.id.b11:
                if (ar1.size() == 3) {
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 1, 1);
                }
                break;
            case R.id.b12:
                if (ar2.size() == 3) {
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 1, 2);
                }
                break;
            case R.id.b13:
                if(ar3.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 1,3);
                }
                break;
            case R.id.b21:
                if(ar4.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 2,1);
                }
                break;
            case R.id.b22:
                if(ar5.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 2,2);
                }
                break;
            case R.id.b23:
                if(ar6.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 2, 3);
                }
                break;
            case R.id.b31:
                if(ar7.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 3, 1);
                }
                break;
            case R.id.b32:
                if(ar8.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 3, 2);
                }
                break;
            case R.id.b33:
                if(ar9.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 3, 3);
                }
                break;
            case R.id.b41:
                if(ar10.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 4, 1);
                }
                break;
            case R.id.b42:
                if(ar11.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 4, 2);
                }
                break;
            case R.id.b43:
                if(ar12.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 4, 3);
                }
                break;
        }
    }

    private class InsertPlanInfoTask extends AsyncTask<PlanInfo, Void, Long> {
        @Override
        protected Long doInBackground(PlanInfo... planInfos) {
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            Long myLong = db.courseDao().insertPlanInfo(planInfos[0]);

            return myLong;
        }

        @Override
        protected void onPostExecute(Long result) {
            myPlanInfoId = result.intValue();
        }
    }

    private class GetPlanInfosTask extends AsyncTask<Void, Void, ArrayList<PlanInfo>>{
        @Override
        protected ArrayList<PlanInfo> doInBackground(Void... voids){
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<PlanInfo> myList = (ArrayList<PlanInfo>) db.courseDao().getAllPlanInfos();
            return myList;
        }

        @Override
        protected void onPostExecute(ArrayList<PlanInfo> result) {

        }
    }

    private class InsertPlanItemTask extends AsyncTask<com.example.mydegree.Room.Plan, Void, Long>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(com.example.mydegree.Room.Plan... plans) {
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            try {
                Long myLong = db.courseDao().insertPlan(plans[0]);

                //dismiss fragment dialog
                Fragment prev = getSupportFragmentManager().findFragmentByTag(PICK_FRAG_TAG);
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }
                return myLong;

            }
            catch (SQLiteConstraintException e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(Long aLong) {
            if(aLong==null){
                Toast.makeText(Plan.this,"Error: You have already put this course on your plan.", Toast.LENGTH_SHORT).show();
            } else {
                switch (buttonLastClick) {
                    case R.id.b11:
                        ar1.add(temporaryItem);
                        p1.setPlan(ar1);
                        break;
                    case R.id.b12:
                        ar2.add(temporaryItem);
                        p2.setPlan(ar2);
                        break;
                    case R.id.b13:
                        ar3.add(temporaryItem);
                        p3.setPlan(ar3);
                        break;
                    case R.id.b21:
                        ar4.add(temporaryItem);
                        p4.setPlan(ar4);
                        break;
                    case R.id.b22:
                        ar5.add(temporaryItem);
                        p5.setPlan(ar5);
                        break;
                    case R.id.b23:
                        ar6.add(temporaryItem);
                        p6.setPlan(ar6);
                        break;
                    case R.id.b31:
                        ar7.add(temporaryItem);
                        p7.setPlan(ar7);
                        break;
                    case R.id.b32:
                        ar8.add(temporaryItem);
                        p8.setPlan(ar8);
                        break;
                    case R.id.b33:
                        ar9.add(temporaryItem);
                        p9.setPlan(ar9);
                        break;
                    case R.id.b41:
                        ar10.add(temporaryItem);
                        p10.setPlan(ar10);
                        break;
                    case R.id.b42:
                        ar11.add(temporaryItem);
                        p11.setPlan(ar11);
                        break;
                    case R.id.b43:
                        ar12.add(temporaryItem);
                        p12.setPlan(ar12);
                        break;
                }
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
