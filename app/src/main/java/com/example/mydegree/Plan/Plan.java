package com.example.mydegree.Plan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.PlanInfo;
import com.example.mydegree.Room.Prereq;
import com.example.mydegree.Room.Stream;
import com.example.mydegree.Room.StreamCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String programCode, majorCode, planName;
    private String temporaryCourse;
    public int buttonLastClick;
    private int termLastClick;
    private com.example.mydegree.Room.Plan temporaryItem;
    private Spinner planSpinner;
    private ArrayAdapter<String> spinAdapter;
    private Spinner actionSpinner;

    private Spinner toolbarSpinner;
    private ArrayAdapter<String> actionAdapter;
    private int justCreated;
    private ProgressDialog progDialog;

    private RecyclerView recyclerClicked;
    private int recyclerLongClicked;

    private RecyclerView rs1, rs2, rs3, rs4, rs5, rs6;
    private CardView cs1, cs2, cs3, cs4, cs5, cs6;
    private CardView streamsCard;
    private TextView yourCourses;
    private TextView ts1, ts2, ts3, ts4, ts5, ts6, uoc1, uoc2, uoc3, uoc4, uoc5, uoc6;
    private PlanAdapter ps1, ps2, ps3, ps4, ps5, ps6;
    private ArrayList<com.example.mydegree.Room.Plan> ars1, ars2, ars3, ars4, ars5, ars6;
    private ArrayList<StreamCourse> arsc1, arsc2, arsc3, arsc4, arsc5, arsc6;

    private String uid;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button sync, syncDown;
    private TextView text;
    private String course1, course2, course3, course4, course5, course6, course7, course8, course9, course10, course11, course12, course13, course14, course15, course16, course17, course18, course19, course20, course21, course22, course23, course24, course25, course26, course27, course28, course29, course30, course31, course32, course33;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_plan, frameLayout, true);

        //region customise this for each nav menu destination
        navigationView.setCheckedItem(R.id.menuplan);
        setTitle("myPlan");
        //endregion

        justCreated=1;

        //region setting up fab (FAB NO LONGER USED)
        fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setVisibility(View.GONE);
        /*fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Plan.this, AddPlan.class);
                startActivityForResult(pickIntent, PICK_PROGRAM_REQUEST);
            }
        });*/
        //endregion


        //region firebase

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        } else {
            uid = "4PUZCL42tVhL6wP90ZO2gZqOyhC3";
        }

        sync = findViewById(R.id.sync);
        sync.setVisibility(View.GONE);
        syncDown = findViewById(R.id.syncDown);

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

        //region setting up spinner
        planSpinner = findViewById(R.id.planSpinner);
        planSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                int i = lol.indexOf(" ");
                String haha = lol.substring(0,i);

                if (user == null) {
                    new GetOnePlanInfoTask().execute(Integer.valueOf(haha));
                } else {
                    Toast.makeText(Plan.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        toolbarSpinner = findViewById(R.id.my_spinner);
        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                int i = lol.indexOf(" ");
                String haha = lol.substring(0,i);
                if (user == null) {
                    new GetOnePlanInfoTask().execute(Integer.valueOf(haha));
                } else {
                    Toast.makeText(Plan.this, "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        toolbarSpinner.setDropDownVerticalOffset(132);

        //region STREAMS SETUPS

        //set up new stream ArrayList
        ars1 = new ArrayList<>();
        ars2 = new ArrayList<>();
        ars3 = new ArrayList<>();
        ars4 = new ArrayList<>();
        ars5 = new ArrayList<>();
        ars6 = new ArrayList<>();
        arsc1 = new ArrayList<>();
        arsc2 = new ArrayList<>();
        arsc3 = new ArrayList<>();
        arsc4 = new ArrayList<>();
        arsc5 = new ArrayList<>();
        arsc6 = new ArrayList<>();

        //set up textViews
        ts1 = findViewById(R.id.streamText1);
        ts2 = findViewById(R.id.streamText2);
        ts3 = findViewById(R.id.streamText3);
        ts4 = findViewById(R.id.streamText4);
        ts5 = findViewById(R.id.streamText5);
        ts6 = findViewById(R.id.streamText6);
        uoc1 = findViewById(R.id.streamUOC1);
        uoc2 = findViewById(R.id.streamUOC2);
        uoc3 = findViewById(R.id.streamUOC3);
        uoc4 = findViewById(R.id.streamUOC4);
        uoc5 = findViewById(R.id.streamUOC5);
        uoc6 = findViewById(R.id.streamUOC6);

        streamsCard = findViewById(R.id.streamsCard);
        streamsCard.setVisibility(View.GONE);
        yourCourses = findViewById(R.id.textYourCourses);
        yourCourses.setVisibility(View.GONE);


        //set up recyclers
        rs1 = findViewById(R.id.streamRV1);
        rs1.setHasFixedSize(true);
        rs1.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        rs2 = findViewById(R.id.streamRV2);
        rs2.setHasFixedSize(true);
        rs2.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        rs3 = findViewById(R.id.streamRV3);
        rs3.setHasFixedSize(true);
        rs3.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        rs4 = findViewById(R.id.streamRV4);
        rs4.setHasFixedSize(true);
        rs4.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        rs5 = findViewById(R.id.streamRV5);
        rs5.setHasFixedSize(true);
        rs5.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));
        rs6 = findViewById(R.id.streamRV6);
        rs6.setHasFixedSize(true);
        rs6.setLayoutManager(new LinearLayoutManager(Plan.this, RecyclerView.VERTICAL, false));


        //setting adapters
        ps1 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps2 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps3 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps4 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps5 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps6 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());

        //bind recyclers to adapters
        rs1.setAdapter(ps1);
        rs2.setAdapter(ps2);
        rs3.setAdapter(ps3);
        rs4.setAdapter(ps4);
        rs5.setAdapter(ps5);
        rs6.setAdapter(ps6);

        //endregion
        progDialog = new ProgressDialog(Plan.this, ProgressDialog.STYLE_SPINNER);

        //new GetPlanInfosTask().execute();


        if (user != null) {
            uid = user.getUid();
            syncDown.setVisibility(View.VISIBLE);
        } else {
            syncDown.setVisibility(View.GONE);
        }
        //Do the rest as you want for each activity

        syncDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("");/*
                retrievePlans();*/
            }
        });



    }

    private void retrievePlans() {
        DatabaseReference retrieveRef = databaseReference.child("User").child(uid).child("progression");
        retrieveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> planIdList = new ArrayList<>();
                List<String> courseList = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String planId = ds.getKey() + " " + "-" + " " + ds.child("planName").getValue();
                    planIdList.add(planId);

                    String t1course = ds.child("1").child("course1").getValue(String.class);
                    PlanObject planObject = new PlanObject();
                    planObject.setCourse1(t1course);
                    courseList.add(String.valueOf(planObject));

                }

                spinAdapter = new ArrayAdapter<String>(Plan.this, R.layout.simple_spinner_item_v2, planIdList);
                spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                toolbarSpinner.setVisibility(View.VISIBLE);
                toolbarSpinner.setAdapter(spinAdapter);
                toolbarSpinner.setSelection(planIdList.size() - 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void syncPlan(final ArrayList<PlanInfo> result) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            final ProgressDialog progressDialog = ProgressDialog.show(Plan.this, "Please wait", "Syncing...", true);

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (int i = 0; i < result.size(); i++) {
                    String planId = String.valueOf(result.get(i).getPlanId());
                    String progCode = result.get(i).getProgCode();
                    String planName = result.get(i).getPlanName();
                    String majorCode = result.get(i).getMajorId();

                    DatabaseReference syncRef = databaseReference.child("User").child(uid).child("progression").child(planId);
                    Map<String, Object> map = new HashMap<>();
                    map.put("progCode", progCode);
                    map.put("planName", planName);
                    if (progCode.equals("3584")) {
                        map.put("majorCode", majorCode);
                    }

                    //region hell
                        try {
                            if (planId.equals(String.valueOf(ar1.get(0).getPlanId()))) {
                                if (ar1.size() == 0) {
                                    course1 = course2 = course3 = null;
                                } else if (ar1.size() == 1) {
                                    course1 = ar1.get(0).getCourseCode();
                                    course2 = course3 = null;
                                } else if (ar1.size() == 2) {
                                    course1 = ar1.get(0).getCourseCode();
                                    course2 = ar1.get(1).getCourseCode();
                                    course3 = null;
                                } else {
                                    course1 = ar1.get(0).getCourseCode();
                                    course2 = ar1.get(1).getCourseCode();
                                    course3 = ar1.get(2).getCourseCode();
                                }
                                map.put("1", new PlanObject(course1, course2, course3));

                                if (ar2.size() == 0) {
                                    course4 = course5 = course6 = null;
                                } else if (ar2.size() == 1) {
                                    course4 = ar2.get(0).getCourseCode();
                                    course5 = course6 = null;
                                } else if (ar2.size() == 2) {
                                    course4 = ar2.get(0).getCourseCode();
                                    course5 = ar2.get(1).getCourseCode();
                                    course6 = null;
                                } else {
                                    course4 = ar2.get(0).getCourseCode();
                                    course5 = ar2.get(1).getCourseCode();
                                    course6 = ar2.get(2).getCourseCode();
                                }
                                map.put("2", new PlanObject(course4, course5, course6));

                                if (ar3.size() == 0) {
                                    course7 = course8 = course9 = null;
                                } else if (ar3.size() == 1) {
                                    course7 = ar3.get(0).getCourseCode();
                                    course8 = course9 = null;
                                } else if (ar3.size() == 2) {
                                    course7 = ar3.get(0).getCourseCode();
                                    course8 = ar3.get(1).getCourseCode();
                                    course9 = null;
                                } else {
                                    course7 = ar3.get(0).getCourseCode();
                                    course8 = ar3.get(1).getCourseCode();
                                    course9 = ar3.get(2).getCourseCode();
                                }
                                map.put("3", new PlanObject(course7, course8, course9));

                                if (ar4.size() == 0) {
                                    course10 = course11 = course12 = null;
                                } else if (ar4.size() == 1) {
                                    course10 = ar4.get(0).getCourseCode();
                                    course11 = course12 = null;
                                } else if (ar4.size() == 2) {
                                    course10 = ar4.get(0).getCourseCode();
                                    course11 = ar4.get(1).getCourseCode();
                                    course12 = null;
                                } else {
                                    course10 = ar4.get(0).getCourseCode();
                                    course11 = ar4.get(1).getCourseCode();
                                    course12 = ar4.get(2).getCourseCode();
                                }
                                map.put("4", new PlanObject(course10, course11, course12));

                                if (ar5.size() == 0) {
                                    course13 = course14 = course15 = null;
                                } else if (ar5.size() == 1) {
                                    course13 = ar5.get(0).getCourseCode();
                                    course14 = course15 = null;
                                } else if (ar5.size() == 2) {
                                    course13 = ar5.get(0).getCourseCode();
                                    course14 = ar5.get(1).getCourseCode();
                                    course15 = null;
                                } else {
                                    course13 = ar5.get(0).getCourseCode();
                                    course14 = ar5.get(1).getCourseCode();
                                    course15 = ar5.get(2).getCourseCode();
                                }
                                map.put("5", new PlanObject(course13, course14, course15));

                                if (ar6.size() == 0) {
                                    course16 = course17 = course18 = null;
                                } else if (ar6.size() == 1) {
                                    course16 = ar6.get(0).getCourseCode();
                                    course17 = course18 = null;
                                } else if (ar6.size() == 2) {
                                    course16 = ar6.get(0).getCourseCode();
                                    course17 = ar6.get(1).getCourseCode();
                                    course18 = null;
                                } else {
                                    course16 = ar6.get(0).getCourseCode();
                                    course17 = ar6.get(1).getCourseCode();
                                    course18 = ar6.get(2).getCourseCode();
                                }
                                map.put("6", new PlanObject(course16, course17, course18));

                                if (ar7.size() == 0) {
                                    course19 = course20 = course21 = null;
                                } else if (ar7.size() == 1) {
                                    course17 = ar7.get(0).getCourseCode();
                                    course20 = course21 = null;
                                } else if (ar7.size() == 2) {
                                    course19 = ar7.get(0).getCourseCode();
                                    course20 = ar7.get(1).getCourseCode();
                                    course21 = null;
                                } else {
                                    course19 = ar7.get(0).getCourseCode();
                                    course20 = ar7.get(1).getCourseCode();
                                    course21 = ar7.get(2).getCourseCode();
                                }
                                map.put("7", new PlanObject(course19, course20, course21));

                                if (ar8.size() == 0) {
                                    course22 = course23 = course24 = null;
                                } else if (ar8.size() == 1) {
                                    course22 = ar8.get(0).getCourseCode();
                                    course23 = course24 = null;
                                } else if (ar8.size() == 2) {
                                    course22 = ar8.get(0).getCourseCode();
                                    course23 = ar8.get(1).getCourseCode();
                                    course24 = null;
                                } else {
                                    course22 = ar8.get(0).getCourseCode();
                                    course23 = ar8.get(1).getCourseCode();
                                    course24 = ar8.get(2).getCourseCode();
                                }
                                map.put("8", new PlanObject(course22, course23, course24));

                                if (ar9.size() == 0) {
                                    course25 = course26 = course27 = null;
                                } else if (ar9.size() == 1) {
                                    course25 = ar9.get(0).getCourseCode();
                                    course26 = course27 = null;
                                } else if (ar2.size() == 2) {
                                    course25 = ar9.get(0).getCourseCode();
                                    course26 = ar9.get(1).getCourseCode();
                                    course27 = null;
                                } else {
                                    course25 = ar9.get(0).getCourseCode();
                                    course26 = ar9.get(1).getCourseCode();
                                    course27 = ar9.get(2).getCourseCode();
                                }
                                map.put("9", new PlanObject(course25, course26, course27));

                                if (ar10.size() == 0) {
                                    course28 = course29 = course30 = null;
                                } else if (ar10.size() == 1) {
                                    course28 = ar10.get(0).getCourseCode();
                                    course29 = course30 = null;
                                } else if (ar10.size() == 2) {
                                    course28 = ar10.get(0).getCourseCode();
                                    course29 = ar10.get(1).getCourseCode();
                                    course30 = null;
                                } else {
                                    course28 = ar10.get(0).getCourseCode();
                                    course29 = ar10.get(1).getCourseCode();
                                    course30 = ar10.get(2).getCourseCode();
                                }
                                map.put("11", new PlanObject(course28, course29, course30));

                                if (ar11.size() == 0) {
                                    course31 = course32 = course33 = null;
                                } else if (ar11.size() == 1) {
                                    course31 = ar11.get(0).getCourseCode();
                                    course32 = course33 = null;
                                } else if (ar11.size() == 2) {
                                    course31 = ar11.get(0).getCourseCode();
                                    course32 = ar11.get(1).getCourseCode();
                                    course33 = null;
                                } else {
                                    course31 = ar11.get(0).getCourseCode();
                                    course32 = ar11.get(1).getCourseCode();
                                    course33 = ar11.get(2).getCourseCode();
                                }

                                map.put("12", new PlanObject(course31, course32, course33));
                            }
                        } catch (IndexOutOfBoundsException e) {

                    }
                    syncRef.updateChildren(map);
                    //endregion
                }
                progressDialog.dismiss();
                Snackbar.make(c1, "Sync successful.", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setRecyclerClicked(RecyclerView view){
        this.recyclerClicked = view;
    }

    public void setRecyclerLongClicked(int id){
        this.recyclerLongClicked = id;
    }

    private void setupContent(){
        streamsCard.setVisibility(View.VISIBLE);
        yourCourses.setVisibility(View.VISIBLE);
        if(programCode.equals("3979")){
            c4.setVisibility(View.GONE);
        } else {
            c4.setVisibility(View.VISIBLE);
        }
        if(planName!=null){
            if(!planName.isEmpty()){
                setTitle(planName);
            }
        }

        //region setting text for stream headings
        if(programCode.equals("3584")){
            ts1.setText("Commerce Core");
            uoc1.setText("24 UOC");
            ts2.setText("Flexible Core");
            uoc2.setText("18 UOC");
            ts3.setText("INFS Core");
            uoc3.setText("72 UOC");
            ts4.setText("Major");
            uoc4.setText("36-48 UOC");
            ts5.setText("Business Elec.");
            uoc5.setText("18-30 UOC");
            ts6.setText("General Ed.");
            uoc6.setText("12 UOC");
        } else if(programCode.equals("3979")){
            ts1.setText("Stage 1 Core");
            uoc1.setText("42 UOC");
            ts2.setText("Stage 2 Core");
            uoc2.setText("24 UOC");
            ts3.setText("Stage 3 Core");
            uoc3.setText("30 UOC");
            ts4.setText("INFS 2/3 Elec.");
            uoc4.setText("12 UOC");
            ts5.setText("Free Electives");
            uoc5.setText("24 UOC");
            ts6.setText("General Ed.");
            uoc6.setText("12 UOC");
        } else if(programCode.equals("3964")){
            ts1.setText("Stage 1 Core");
            uoc1.setText("42 UOC");
            ts2.setText("Stage 2 Core");
            uoc2.setText("24 UOC");
            ts3.setText("Stage 3");
            uoc3.setText("30 UOC");
            ts4.setText("Placement");
            uoc4.setText("36 UOC");
            ts5.setText("Honours");
            uoc5.setText("48 UOC");
            ts6.setText("General Ed.");
            uoc6.setText("12 UOC");
        }
        //endregion


        //first make buttons visible
        buttons1.setVisibility(View.VISIBLE);
        buttons2.setVisibility(View.VISIBLE);
        buttons3.setVisibility(View.VISIBLE);
        buttons4.setVisibility(View.VISIBLE);

        sync.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PROGRAM_REQUEST){
            if(resultCode==RESULT_OK){
                //receiving data back and making Toasts to ensure its done
                programCode = data.getStringExtra(RESULT_PROG);
                majorCode = data.getStringExtra(RESULT_MAJOR);
                planName = data.getStringExtra(RESULT_NAME);

                PlanInfo toInsert = new PlanInfo(0, planName,programCode, majorCode);

                new InsertPlanInfoTask().execute(toInsert);
                new GetStreamCoursesTask().execute(programCode);
                new GetPlanInfosTask().execute();
                setupContent();
            }
        }
    }

    //LONG-CLICK PLAN TO DELETE THINGS....
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_remove:

                com.example.mydegree.Room.Plan toDelete;
                int position;

                ArrayList<ArrayList<com.example.mydegree.Room.Plan>> masterTermList = new ArrayList<>();
                //region adding to masterlist
                masterTermList.add(ar1);
                masterTermList.add(ar2);
                masterTermList.add(ar3);
                masterTermList.add(ar4);
                masterTermList.add(ar5);
                masterTermList.add(ar6);
                masterTermList.add(ar7);
                masterTermList.add(ar8);
                masterTermList.add(ar9);
                masterTermList.add(ar10);
                masterTermList.add(ar11);
                masterTermList.add(ar12);
                //endregion
                ArrayList<PlanAdapter> masterTermAdapter = new ArrayList<>();
                //region adding to masterlist
                masterTermAdapter.add(p1);
                masterTermAdapter.add(p2);
                masterTermAdapter.add(p3);
                masterTermAdapter.add(p4);
                masterTermAdapter.add(p5);
                masterTermAdapter.add(p6);
                masterTermAdapter.add(p7);
                masterTermAdapter.add(p8);
                masterTermAdapter.add(p9);
                masterTermAdapter.add(p10);
                masterTermAdapter.add(p11);
                masterTermAdapter.add(p12);
                //endregion
                ArrayList<ArrayList<com.example.mydegree.Room.Plan>> masterStreamList = new ArrayList<>();
                //region adding to masterlist
                masterStreamList.add(ars1);
                masterStreamList.add(ars2);
                masterStreamList.add(ars3);
                masterStreamList.add(ars4);
                masterStreamList.add(ars5);
                masterStreamList.add(ars6);
                //endregion
                ArrayList<PlanAdapter> masterStreamAdapter = new ArrayList<>();
                //region adding to masterlist
                masterStreamAdapter.add(ps1);
                masterStreamAdapter.add(ps2);
                masterStreamAdapter.add(ps3);
                masterStreamAdapter.add(ps4);
                masterStreamAdapter.add(ps5);
                masterStreamAdapter.add(ps6);
                //endregion


                switch(recyclerLongClicked){
                    case R.id.r1:
                        position = ((PlanAdapter) r1.getAdapter()).getPosition();
                        toDelete = ar1.get(position);
                        ar1.remove(position);
                        p1.setPlan(ar1);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r2:
                        position = ((PlanAdapter) r2.getAdapter()).getPosition();
                        toDelete = ar2.get(position);
                        ar2.remove(position);
                        p2.setPlan(ar2);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r3:
                        position = ((PlanAdapter) r3.getAdapter()).getPosition();
                        toDelete = ar3.get(position);
                        ar3.remove(position);
                        p3.setPlan(ar3);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r4:
                        position = ((PlanAdapter) r4.getAdapter()).getPosition();
                        toDelete = ar4.get(position);
                        ar4.remove(position);
                        p4.setPlan(ar4);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r5:
                        position = ((PlanAdapter) r5.getAdapter()).getPosition();
                        toDelete = ar5.get(position);
                        ar5.remove(position);
                        p5.setPlan(ar5);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r6:
                        position = ((PlanAdapter) r6.getAdapter()).getPosition();
                        toDelete = ar6.get(position);
                        ar6.remove(position);
                        p6.setPlan(ar6);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r7:
                        position = ((PlanAdapter) r7.getAdapter()).getPosition();
                        toDelete = ar7.get(position);
                        ar7.remove(position);
                        p7.setPlan(ar7);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r8:
                        position = ((PlanAdapter) r8.getAdapter()).getPosition();
                        toDelete = ar8.get(position);
                        ar8.remove(position);
                        p8.setPlan(ar8);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r9:
                        position = ((PlanAdapter) r9.getAdapter()).getPosition();
                        toDelete = ar9.get(position);
                        ar9.remove(position);
                        p9.setPlan(ar9);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r10:
                        position = ((PlanAdapter) r10.getAdapter()).getPosition();
                        toDelete = ar10.get(position);
                        ar10.remove(position);
                        p10.setPlan(ar10);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r11:
                        position = ((PlanAdapter) r11.getAdapter()).getPosition();
                        toDelete = ar11.get(position);
                        ar11.remove(position);
                        p11.setPlan(ar11);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r12:
                        position = ((PlanAdapter) r12.getAdapter()).getPosition();
                        toDelete = ar12.get(position);
                        ar12.remove(position); p12.setPlan(ar12);

                        for(int i=0; i<masterStreamList.size();i++){
                            masterStreamList.get(i).remove(toDelete);
                            masterStreamAdapter.get(i).setPlan(masterStreamList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV1:
                        position = ps1.getPosition();
                        toDelete = ars1.get(position);
                        ars1.remove(toDelete); ps1.setPlan(ars1);

                        for(int i=0; i<masterTermList.size();i++){
                            masterTermList.get(i).remove(toDelete);
                            masterTermAdapter.get(i).setPlan(masterTermList.get(i));
                        }

                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV2:
                        position = ps2.getPosition();
                        toDelete = ars2.get(position);
                        ars2.remove(toDelete); ps2.setPlan(ars2);

                        for(int i=0; i<masterTermList.size();i++){
                            masterTermList.get(i).remove(toDelete);
                            masterTermAdapter.get(i).setPlan(masterTermList.get(i));
                        }
                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV3:
                        position = ps3.getPosition();
                        toDelete = ars3.get(position);
                        ars3.remove(toDelete); ps3.setPlan(ars3);

                        for(int i=0; i<masterTermList.size();i++){
                            masterTermList.get(i).remove(toDelete);
                            masterTermAdapter.get(i).setPlan(masterTermList.get(i));
                        }
                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV4:
                        position = ps4.getPosition();
                        toDelete = ars4.get(position);
                        ars4.remove(toDelete); ps4.setPlan(ars4);

                        for(int i=0; i<masterTermList.size();i++){
                            masterTermList.get(i).remove(toDelete);
                            masterTermAdapter.get(i).setPlan(masterTermList.get(i));
                        }
                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV5:
                        position = ps5.getPosition();
                        toDelete = ars5.get(position);
                        ars5.remove(toDelete); ps5.setPlan(ars5);

                        for(int i=0; i<masterTermList.size();i++){
                            masterTermList.get(i).remove(toDelete);
                            masterTermAdapter.get(i).setPlan(masterTermList.get(i));
                        }
                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV6:
                        position = ps6.getPosition();
                        toDelete = ars6.get(position);
                        ars6.remove(toDelete); ps6.setPlan(ars6);

                        for(int i=0; i<masterTermList.size();i++){
                            masterTermList.get(i).remove(toDelete);
                            masterTermAdapter.get(i).setPlan(masterTermList.get(i));
                        }
                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    default:
                        break;
                }
        }

        return super.onContextItemSelected(item);
    }

    //DELETE WHOLE PLAN MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        MenuItem spinItem = menu.findItem(R.id.action_spinner);
        actionSpinner = (Spinner) spinItem.getActionView();
        actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                int i = lol.indexOf(" ");
                String haha = lol.substring(0,i);
                new GetOnePlanInfoTask().execute(Integer.valueOf(haha));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new GetPlanInfosTask().execute();
        return true;
    }

    //DELETE WHOLE PLAN MENU ONCLICK
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_delete:
                if(programCode!=null && myPlanInfoId!=0){
                    new AlertDialog.Builder(Plan.this)
                            .setTitle("Confirm Delete")
                            .setMessage("Are you sure you want to delete this plan?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new DeleteWholePlanTask().execute(myPlanInfoId);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                } else {
                    Toast.makeText(Plan.this, "There is no plan to delete!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_add:
                Intent pickIntent = new Intent(Plan.this, AddPlan.class);
                startActivityForResult(pickIntent, PICK_PROGRAM_REQUEST);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        temporaryCourse = course;

        temporaryItem = new com.example.mydegree.Room.Plan(myPlanInfoId, year, term, course);

        new CheckPrereqsTask().execute(temporaryCourse);

    }

    private void showToastCourseFull(){
        Toast.makeText(Plan.this, "You cannot place anymore courses in this term!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        buttonLastClick = v.getId();
        switch(v.getId()) {
            case R.id.b11:
                termLastClick = 1;
                if (ar1.size() == 3) {
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 1, 1);
                }
                break;
            case R.id.b12:
                termLastClick = 2;
                if (ar2.size() == 3) {
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 1, 2);
                }
                break;
            case R.id.b13:
                termLastClick = 3;
                if(ar3.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 1,3);
                }
                break;
            case R.id.b21:
                termLastClick = 4;
                if(ar4.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 2,1);
                }
                break;
            case R.id.b22:
                termLastClick = 5;
                if(ar5.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 2,2);
                }
                break;
            case R.id.b23:
                termLastClick = 6;
                if(ar6.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 2, 3);
                }
                break;
            case R.id.b31:
                termLastClick = 7;
                if(ar7.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 3, 1);
                }
                break;
            case R.id.b32:
                termLastClick = 8;
                if(ar8.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 3, 2);
                }
                break;
            case R.id.b33:
                termLastClick = 9;
                if(ar9.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 3, 3);
                }
                break;
            case R.id.b41:
                termLastClick = 10;
                if(ar10.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 4, 1);
                }
                break;
            case R.id.b42:
                termLastClick = 11;
                if(ar11.size()==3){
                    showToastCourseFull();
                } else {
                    showPickDialog(programCode, 4, 2);
                }
                break;
            case R.id.b43:
                termLastClick = 12;
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

    private class DeletePlanItemTask extends AsyncTask<com.example.mydegree.Room.Plan, Void, Void> {
        @Override
        protected Void doInBackground(com.example.mydegree.Room.Plan... plans) {
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            db.courseDao().deletePlanItem(plans[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(Plan.this, "Course deleted from plan.", Toast.LENGTH_SHORT).show();
        }
    }

    private class DeleteWholePlanTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            db.courseDao().deleteWholePlan(integers[0]);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(Plan.this, "Plan deleted.", Toast.LENGTH_SHORT).show();
            justCreated=1;
            new GetPlanInfosTask().execute();

            DatabaseReference clear = databaseReference.child("User").child(uid).child("progression");
            clear.child(String.valueOf(myPlanInfoId)).removeValue();
        }
    }

    private class GetStreamCoursesTask extends AsyncTask<String, Void, ArrayList<StreamCourse>> {
        @Override
        protected ArrayList<StreamCourse> doInBackground(String... strings) {
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

           ArrayList<StreamCourse> masterList = (ArrayList<StreamCourse>) db.courseDao().getProgStreamCourses(strings[0]);

            return masterList;
        }

        @Override
        protected void onPostExecute(ArrayList<StreamCourse> result) {
            ars1 = new ArrayList<>();
            ps1.setPlan(ars1);
            ars2 = new ArrayList<>();
            ps2.setPlan(ars2);
            ars3 = new ArrayList<>();
            ps3.setPlan(ars3);
            ars4 = new ArrayList<>();
            ps4.setPlan(ars4);
            ars5 = new ArrayList<>();
            ps5.setPlan(ars5);
            ars6 = new ArrayList<>();
            ps6.setPlan(ars6);
            arsc1 = new ArrayList<>();
            arsc2 = new ArrayList<>();
            arsc3 = new ArrayList<>();
            arsc4 = new ArrayList<>();
            arsc5 = new ArrayList<>();
            arsc6 = new ArrayList<>();
            if(programCode.contains("3584")){
                for(int i=0; i<result.size(); i++){
                    if (result.get(i).getStreamId2().contains("3584COC")){
                        arsc1.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3584F")){
                        arsc2.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3584I1")||result.get(i).getStreamId2().equals("INFS2C")||result.get(i).getStreamId2().equals("39793C")){
                        arsc3.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals(majorCode)){
                        arsc4.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("GENED")){
                        arsc6.add(result.get(i));
                    } else {
                        //EVERYTHING WOULD BE A BIZ ELEC.
                        arsc5.add(result.get(i));
                    }
                }
            } else {
                for(int i=0; i<result.size(); i++){
                    if (result.get(i).getStreamId2().contains("INFS1C")){
                        arsc1.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("INFS2C")){
                        arsc2.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("39793C")||result.get(i).getStreamId2().contains("3964C")){
                        arsc3.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3979E")||result.get(i).getStreamId2().equals("3964I")){
                        arsc4.add(result.get(i));
                    } else if(result.get(i).getStreamId2().contains("3964H")){
                        arsc5.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("GENED")){
                        arsc6.add(result.get(i));
                    } else {
                        //EVERYTHING ELSE WOULD BE A FREE ELECTIVE
                        arsc5.add(result.get(i));
                    }
                }
            }


        }
    }

    private class GetPlanInfosTask extends AsyncTask<Void, Void, ArrayList<PlanInfo>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog.setMessage("Loading Plan..");
            progDialog.setIndeterminate(false);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<PlanInfo> doInBackground(Void... voids){
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<PlanInfo> myList = (ArrayList<PlanInfo>) db.courseDao().getAllPlanInfos();
            return myList;
        }

        @Override
        protected void onPostExecute(final ArrayList<PlanInfo> result) {
            ArrayList<String> myStrings = new ArrayList<>();
            for(int i = 0; i<result.size(); i++){
                myStrings.add(result.get(i).getPlanId() + " - " + result.get(i).getPlanName());
                if(justCreated==1){
                    if(i==(result.size()-1)){
                        programCode = result.get(i).getProgCode();
                        planName = result.get(i).getPlanName();
                        myPlanInfoId = result.get(i).getPlanId();
                    }
                }
            }
            if(result.size()==0){
                planSpinner.setVisibility(View.GONE);

                toolbarSpinner.setVisibility(View.GONE);

                p1.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p2.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p3.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p4.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p5.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p6.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p7.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p8.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p9.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p10.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p11.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                p12.setPlan(new ArrayList<com.example.mydegree.Room.Plan>());
                setTitle("myPlan");
                buttons1.setVisibility(View.GONE);
                buttons2.setVisibility(View.GONE);
                buttons3.setVisibility(View.GONE);
                buttons4.setVisibility(View.GONE);
                yourCourses.setVisibility(View.GONE);
                streamsCard.setVisibility(View.GONE);
                Snackbar.make(c1, "You have no plans! Make a plan by pressing the + button.", Snackbar.LENGTH_LONG).show();
            } else {
                //planSpinner.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                spinAdapter = new ArrayAdapter<String>(Plan.this, R.layout.simple_spinner_item_v2, myStrings);
                spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                toolbarSpinner.setVisibility(View.VISIBLE);
                toolbarSpinner.setAdapter(spinAdapter);
                toolbarSpinner.setSelection(myStrings.size()-1);
                //actionSpinner.setAdapter(spinAdapter);
                //actionSpinner.setSelection(myStrings.size()-1);
                //planSpinner.setAdapter(spinAdapter);
                //planSpinner.setSelection(myStrings.size() - 1);
            }

            //this could prob be put in the "else" block above tho,.. idk why i put it in separate
            if(result.size()!=0) {
                //String omg = planSpinner.getSelectedItem().toString();
                //String omg = actionSpinner.getSelectedItem().toString();
                String omg = toolbarSpinner.getSelectedItem().toString();
                int fail = omg.indexOf(" ");
                String thePlanId = omg.substring(0, fail);
                if(justCreated==1){
                    new GetPlanItemsTask().execute(Integer.valueOf(thePlanId));
                    justCreated=0;
                    setupContent();
                } else {
                    //new GetStreamCoursesTask().execute(programCode);
                }
            }
            progDialog.dismiss();

            sync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isNetworkAvailable()) {
                        syncPlan(result);
                    } else {
                        Snackbar.make(c1, "Network connection unavailable. Sync unsuccessful.", Snackbar.LENGTH_LONG).show();
                    }

                }
            });

        }
    }

    private class GetOnePlanInfoTask extends AsyncTask<Integer, Void, PlanInfo>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected PlanInfo doInBackground(Integer... integers){
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            PlanInfo myPlan = db.courseDao().getSinglePlanInfo(integers[0]);
            return myPlan;
        }

        @Override
        protected void onPostExecute(PlanInfo result) {
            programCode = result.getProgCode();
            planName = result.getPlanName();
            myPlanInfoId = result.getPlanId();
            majorCode = result.getMajorId();

            new GetStreamCoursesTask().execute(result.getProgCode());
            new GetPlanItemsTask().execute(result.getPlanId());
            setupContent();
        }
    }

    private class GetPlanItemsTask extends AsyncTask<Integer, Void, ArrayList<ArrayList<com.example.mydegree.Room.Plan>>> {

        @Override
        protected ArrayList<ArrayList<com.example.mydegree.Room.Plan>> doInBackground(Integer... integers) {
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<com.example.mydegree.Room.Plan> myList = (ArrayList<com.example.mydegree.Room.Plan>) db.courseDao().getPlanItems(integers[0]);


            ArrayList<com.example.mydegree.Room.Plan> term1 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term2 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term3 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term4 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term5 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term6 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term7 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term8 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term9 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term10 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term11 = new ArrayList<>();
            ArrayList<com.example.mydegree.Room.Plan> term12 = new ArrayList<>();

            for (int i = 0; i < myList.size(); i++) {
                if (myList.get(i).getYear() == 1) {
                    switch (myList.get(i).getTerm()) {
                        case 1:
                            term1.add(myList.get(i));
                            break;
                        case 2:
                            term2.add(myList.get(i));
                            break;
                        case 3:
                            term3.add(myList.get(i));
                            break;
                    }
                } else if (myList.get(i).getYear() == 2) {
                    switch (myList.get(i).getTerm()) {
                        case 1:
                            term4.add(myList.get(i));
                            break;
                        case 2:
                            term5.add(myList.get(i));
                            break;
                        case 3:
                            term6.add(myList.get(i));
                            break;
                    }
                } else if (myList.get(i).getYear() == 3) {
                    switch (myList.get(i).getTerm()) {
                        case 1:
                            term7.add(myList.get(i));
                            break;
                        case 2:
                            term8.add(myList.get(i));
                            break;
                        case 3:
                            term9.add(myList.get(i));
                            break;
                    }
                } else if (myList.get(i).getYear() == 4) {
                    switch (myList.get(i).getTerm()) {
                        case 1:
                            term10.add(myList.get(i));
                            break;
                        case 2:
                            term11.add(myList.get(i));
                            break;
                        case 3:
                            term12.add(myList.get(i));
                            break;
                    }
                }
            }

            ArrayList<ArrayList<com.example.mydegree.Room.Plan>> masterList = new ArrayList<>();
            masterList.add(0, term1);
            masterList.add(1, term2);
            masterList.add(2, term3);
            masterList.add(3, term4);
            masterList.add(4, term5);
            masterList.add(5, term6);
            masterList.add(6, term7);
            masterList.add(7, term8);
            masterList.add(8, term9);
            masterList.add(9, term10);
            masterList.add(10, term11);
            masterList.add(11, term12);
            masterList.add(12, myList);


            return masterList;
        }

        @Override
        protected void onPostExecute(final ArrayList<ArrayList<com.example.mydegree.Room.Plan>> result) {
            int i = 0;
            ar1 = result.get(i);
            p1.setPlan(result.get(i));
            i++;
            ar2 = result.get(i);
            p2.setPlan(result.get(i));
            i++;
            ar3 = result.get(i);
            p3.setPlan(result.get(i));
            i++;
            ar4 = result.get(i);
            p4.setPlan(result.get(i));
            i++;
            ar5 = result.get(i);
            p5.setPlan(result.get(i));
            i++;
            ar6 = result.get(i);
            p6.setPlan(result.get(i));
            i++;
            ar7 = result.get(i);
            p7.setPlan(result.get(i));
            i++;
            ar8 = result.get(i);
            p8.setPlan(result.get(i));
            i++;
            ar9 = result.get(i);
            p9.setPlan(result.get(i));
            i++;
            ar10 = result.get(i);
            p10.setPlan(result.get(i));
            i++;
            ar11 = result.get(i);
            p11.setPlan(result.get(i));
            i++;
            ar12 = result.get(i);
            p12.setPlan(result.get(i));

            for (int k = 0; k < result.get(12).size(); k++) {
                //the method is below
                fillStreams(result.get(12).get(k));
            }


        }
    }


    private void fillStreams(com.example.mydegree.Room.Plan result) {
        for (int j = 0; j < arsc1.size(); j++) {
            if (arsc1.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars1.add(result);
                ps1.setPlan(ars1);
                return;
            }
        }
        for (int j = 0; j < arsc2.size(); j++) {
            if (arsc2.get(j).getStreamCourse().equals(result.getCourseCode())){
                ars2.add(result);
                ps2.setPlan(ars2);
                return;
            }
        }
        for (int j = 0; j < arsc3.size(); j++) {
            if (arsc3.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars3.add(result);
                ps3.setPlan(ars3);
                return;
            }
        }
        for (int j = 0; j < arsc4.size(); j++) {
            if (arsc4.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars4.add(result);
                ps4.setPlan(ars4);
                return;
            }
        }
        for (int j = 0; j < arsc5.size(); j++) {
            if (arsc5.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars5.add(result);
                ps5.setPlan(ars5);
                return;
            }
        }
        for (int j = 0; j < arsc6.size(); j++) {
            if (arsc6.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars6.add(result);
                ps6.setPlan(ars6);
                return;
            }
        }
    }


    //VALIDATION WILL TAKE PLACE HERE.
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
                e.printStackTrace();
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

                fillStreams(temporaryItem);
            }
        }
    }

    private class CheckPrereqsTask extends AsyncTask<String, Void, ArrayList<Prereq>> {

        @Override
        protected ArrayList<Prereq> doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(Plan.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<Prereq> prereqsList = (ArrayList<Prereq>) db.courseDao().getPrereqs(query[0]);
            return prereqsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Prereq> result) {
            ArrayList<ArrayList<com.example.mydegree.Room.Plan>> masterStreams = new ArrayList<>();
            masterStreams.add(ars1);
            masterStreams.add(ars2);
            masterStreams.add(ars3);
            masterStreams.add(ars4);
            masterStreams.add(ars5);
            masterStreams.add(ars6);

            ArrayList<ArrayList<com.example.mydegree.Room.Plan>> masterTerms = new ArrayList<>();
            masterTerms.add(ar1);
            masterTerms.add(ar2);
            masterTerms.add(ar3);
            masterTerms.add(ar4);
            masterTerms.add(ar5);
            masterTerms.add(ar6);
            masterTerms.add(ar7);
            masterTerms.add(ar8);
            masterTerms.add(ar9);
            masterTerms.add(ar10);
            masterTerms.add(ar11);
            masterTerms.add(ar12);


            int uocDone = 0;
            boolean maturityFulfilled = false;
            String level = Character.toString(temporaryCourse.charAt(4));

            String maturityError = "";

            //array containing a boolean (default is FALSE) for each pre-req
            boolean[] myBools = new boolean[result.size()];

            //this boolean will be true if all booleans in the above array are true
            boolean areAllBoolsTrue = true;

            //stores an array of Strings of any pre-requisites not done
            ArrayList<String> toDoList = new ArrayList<>();


            //region maturitychecker
            //first term separate otherwise array index out of bounds (can't check for courses in term 0)
            if(termLastClick==1){
                if(level.equals("2")){
                    maturityFulfilled = false;
                    maturityError = "24 UOC must be completed before enrolling in Level 2 courses.";
                } else if(level.equals("3")){
                    maturityFulfilled = false;
                    maturityError = "48 UOC must be completed before enrolling in Level 3 courses.";
                } else if(level.equals("4")){
                    maturityFulfilled = false;
                    maturityError = "Level 4 courses can only be done in the Honours year.";
                } else if(level.equals("1")){
                    maturityFulfilled = true;
                }
            }

            if(termLastClick>1){
                for (int a = 0; a<(termLastClick-1); a++){
                    for(int b = 0; b<masterTerms.get(a).size(); b++){
                        uocDone = uocDone + 6;
                    }
                }

                if(level.equals("2")){
                    if(uocDone<24){
                        maturityFulfilled = false;
                        maturityError = "24 UOC must be completed before enrolling in Level 2 courses." + "\n" + "\n" +  "You have only done " + uocDone + " UOC in your plan prior to the selected term.";
                    } else {
                        maturityFulfilled = true;
                    }
                } else if(level.equals("3")){
                    if(uocDone<48){
                        maturityFulfilled = false;
                        maturityError = "48 UOC must be completed before enrolling in Level 3 courses."  + "\n" + "\n" +  "You have only done " + uocDone + " UOC in your plan prior to the selected term.";
                    } else {
                        maturityFulfilled = true;
                    }
                } else if(level.equals("4")){
                    if(termLastClick<10){
                        maturityFulfilled = false;
                        maturityError = "Level 4 courses can only be done in the Honours year.";
                    } else {
                        maturityFulfilled = true;
                    }
                } else {
                    maturityFulfilled = true;
                }
            }
            //endregion

            if(result.size()==0){
                if (maturityFulfilled){
                    new InsertPlanItemTask().execute(temporaryItem);
                } else  {
                    new AlertDialog.Builder(Plan.this)
                            .setTitle("Maturity requirements not met!")
                            .setMessage(maturityError + "\n" + "\n" + "Do you still wish to add this course to your plan?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new InsertPlanItemTask().execute(temporaryItem);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            } else {
                //for each prereq, if it exists in previous terms, it will make the boolean in the array for that prereq true
                for (int i = 0; i<(termLastClick-1); i++){
                    for(int j = 0; j<masterTerms.get(i).size();j++){
                        for(int k = 0; k<result.size();k++){
                            if(result.get(k).getPrereq().equals(masterTerms.get(i).get(j).getCourseCode())){
                                myBools[k] = true;
                            }
                        }
                    }
                }
                //if any boolean in the array is false, the whole test fails.
                for(int l = 0; l<result.size();l++){
                    if(!myBools[l]){
                        toDoList.add(result.get(l).getPrereq());
                        areAllBoolsTrue = false;
                    }
                }
                //MAIN LOGIC HERE


                if(areAllBoolsTrue){
                    //TRUE AND TRUE
                    if(maturityFulfilled){
                        new InsertPlanItemTask().execute(temporaryItem);
                    } else {
                        //TRUE AND FALSE
                        new AlertDialog.Builder(Plan.this)
                                .setTitle("Maturity requirements not met!")
                                .setMessage(maturityError + "\n" + "\n" + "Do you still wish to add this course to your plan?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new InsertPlanItemTask().execute(temporaryItem);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                } else {
                    String yetToDo = "";
                    for(int m = 0; m<toDoList.size();m++){
                        yetToDo = yetToDo  +  toDoList.get(m) + "  ";
                    }
                    //FALSE AND TRUE
                    if(maturityFulfilled){
                        new AlertDialog.Builder(Plan.this)
                                .setTitle("Pre-requisites not completed!")
                                .setMessage("Your plan does not have the following pre-requisites prior to the selected term:" + "\n" + "\n" + yetToDo +  "\n" +  "\n" + "Do you still want to add this course to your plan?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new InsertPlanItemTask().execute(temporaryItem);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    } else {
                        //FALSE AND FALSE
                        new AlertDialog.Builder(Plan.this)
                                .setTitle("Requirements not met!")
                                .setMessage(maturityError + "\n" + "\n" + "Additionally, your plan does not have the following pre-requisites prior to the selected term:" + "\n" + "\n" + yetToDo +  "\n" +  "\n" + "Do you still want to add this course to your plan?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new InsertPlanItemTask().execute(temporaryItem);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    }

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}