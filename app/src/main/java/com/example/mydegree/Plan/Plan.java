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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.PlanInfo;
import com.example.mydegree.Room.Stream;
import com.example.mydegree.Room.StreamCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
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
    private Spinner planSpinner;
    private ArrayAdapter<String> spinAdapter;
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

        //region setting up spinner
        planSpinner = findViewById(R.id.planSpinner);
        planSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        //endregion


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

        new GetPlanInfosTask().execute();



        //Do the rest as you want for each activity


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
            ts5.setText("Business Electives");
            uoc5.setText("18-30 UOC");
            ts6.setText("General Education");
            uoc6.setText("12 UOC");
        } else if(programCode.equals("3979")){
            ts1.setText("Stage 1 Compulsory");
            uoc1.setText("42 UOC");
            ts2.setText("Stage 2 Compulsory");
            uoc2.setText("24 UOC");
            ts3.setText("Stage 3 Compulsory");
            uoc3.setText("30 UOC");
            ts4.setText("INFS 2/3 Electives");
            uoc4.setText("12 UOC");
            ts5.setText("Free Electives");
            uoc5.setText("24 UOC");
            ts6.setText("General Education");
            uoc6.setText("12 UOC");
        } else if(programCode.equals("3964")){
            ts1.setText("Stage 1 Compulsory");
            uoc1.setText("42 UOC");
            ts2.setText("Stage 2 Compulsory");
            uoc2.setText("24 UOC");
            ts3.setText("Stage 3 Courses");
            uoc3.setText("30 UOC");
            ts4.setText("Industry Placement");
            uoc4.setText("36 UOC");
            ts5.setText("Honours");
            uoc5.setText("48 UOC");
            ts6.setText("General Education");
            uoc6.setText("12 UOC");
        }
        //endregion


        //first make buttons visible
        buttons1.setVisibility(View.VISIBLE);
        buttons2.setVisibility(View.VISIBLE);
        buttons3.setVisibility(View.VISIBLE);
        buttons4.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PROGRAM_REQUEST){
            if(resultCode==RESULT_OK){
                //receiving data back and making Toasts to ensure its done
                programCode = data.getStringExtra(RESULT_PROG);
                majorName = data.getStringExtra(RESULT_MAJOR);
                planName = data.getStringExtra(RESULT_NAME);

                PlanInfo toInsert = new PlanInfo(0, planName,programCode);

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

                switch(recyclerLongClicked){
                    case R.id.r1:
                        int position = ((PlanAdapter) r1.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete = ar1.get(position);
                        ar1.remove(position);
                        p1.setPlan(ar1);
                        new DeletePlanItemTask().execute(toDelete);
                        break;
                    case R.id.r2:
                        final int position2 = ((PlanAdapter) r2.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete2 = ar2.get(position2);
                        ar2.remove(position2);
                        p2.setPlan(ar2);
                        new DeletePlanItemTask().execute(toDelete2);
                        break;
                    case R.id.r3:
                        final int position3 = ((PlanAdapter) r3.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete3 = ar3.get(position3);
                        ar3.remove(position3);
                        p3.setPlan(ar3);
                        new DeletePlanItemTask().execute(toDelete3);
                        break;
                    case R.id.r4:
                        final int position4 = ((PlanAdapter) r4.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete4 = ar4.get(position4);
                        ar4.remove(position4);
                        p4.setPlan(ar4);
                        new DeletePlanItemTask().execute(toDelete4);
                        break;
                    case R.id.r5:
                        final int position5 = ((PlanAdapter) r5.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete5 = ar5.get(position5);
                        ar5.remove(position5);
                        p5.setPlan(ar5);
                        new DeletePlanItemTask().execute(toDelete5);
                        break;
                    case R.id.r6:
                        final int position6 = ((PlanAdapter) r6.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete6 = ar6.get(position6);
                        ar6.remove(position6);
                        p6.setPlan(ar6);
                        new DeletePlanItemTask().execute(toDelete6);
                        break;
                    case R.id.r7:
                        final int position7 = ((PlanAdapter) r7.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete7 = ar7.get(position7);
                        ar7.remove(position7);
                        p7.setPlan(ar7);
                        new DeletePlanItemTask().execute(toDelete7);
                        break;
                    case R.id.r8:
                        final int position8 = ((PlanAdapter) r8.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete8 = ar8.get(position8);
                        ar8.remove(position8);
                        p8.setPlan(ar8);
                        new DeletePlanItemTask().execute(toDelete8);
                        break;
                    case R.id.r9:
                        final int position9 = ((PlanAdapter) r9.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete9 = ar9.get(position9);
                        ar9.remove(position9);
                        p9.setPlan(ar9);
                        new DeletePlanItemTask().execute(toDelete9);
                        break;
                    case R.id.r10:
                        final int position10 = ((PlanAdapter) r10.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete10 = ar10.get(position10);
                        ar10.remove(position10);
                        p10.setPlan(ar10);
                        new DeletePlanItemTask().execute(toDelete10);
                        break;
                    case R.id.r11:
                        final int position11 = ((PlanAdapter) r11.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete11 = ar11.get(position11);
                        ar11.remove(position11);
                        p11.setPlan(ar11);
                        new DeletePlanItemTask().execute(toDelete11);
                        break;
                    case R.id.r12:
                        final int position12 = ((PlanAdapter) r12.getAdapter()).getPosition();
                        final com.example.mydegree.Room.Plan toDelete12 = ar12.get(position12);
                        ar12.remove(position12);
                        p12.setPlan(ar12);
                        new DeletePlanItemTask().execute(toDelete12);
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
        return true;
    }

    //DELETE WHOLE PLAN MENU ONCLICK
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
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

        temporaryItem = new com.example.mydegree.Room.Plan(myPlanInfoId, year, term, course);

        //ADDING THE COURSE TO THE STREAMS
        for(int i = 0; i<arsc1.size();i++){
            if(arsc1.get(i).getStreamCourse().equals(course)){
                ars1.add(temporaryItem);
                ps1.setPlan(ars1);
            }
        }
        for(int i = 0; i<arsc2.size();i++){
            if(arsc2.get(i).getStreamCourse().equals(course)){
                ars2.add(temporaryItem);
                ps2.setPlan(ars2);
            }
        }
        for(int i = 0; i<arsc3.size();i++){
            if(arsc3.get(i).getStreamCourse().equals(course)){
                ars3.add(temporaryItem);
                ps3.setPlan(ars3);
            }
        }
        for(int i = 0; i<arsc4.size();i++){
            if(arsc4.get(i).getStreamCourse().equals(course)){
                ars4.add(temporaryItem);
                ps4.setPlan(ars4);
            }
        }
        for(int i = 0; i<arsc5.size();i++){
            if(arsc5.get(i).getStreamCourse().equals(course)){
                ars5.add(temporaryItem);
                ps5.setPlan(ars5);
            }
        }
        for(int i = 0; i<arsc6.size();i++){
            if(arsc6.get(i).getStreamCourse().equals(course)){
                ars6.add(temporaryItem);
                ps6.setPlan(ars6);
            }
        }

        new InsertPlanItemTask().execute(temporaryItem);


    }

    private void showToastCourseFull(){
        Toast.makeText(Plan.this, "You cannot place anymore courses in this term!", Toast.LENGTH_SHORT).show();
    }

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
            arsc1 = new ArrayList<>();
            arsc2 = new ArrayList<>();
            arsc3 = new ArrayList<>();
            arsc4 = new ArrayList<>();
            arsc5 = new ArrayList<>();
            arsc6 = new ArrayList<>();
            if(programCode.equals("3584")){
                for(int i=0; i<result.size(); i++){
                    if (result.get(i).getStreamId2().contains("3584COC")){
                        arsc1.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3584F")){
                        arsc2.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3584I1")||result.get(i).getStreamId2().equals("INFS2C")||result.get(i).getStreamId2().equals("39793C")){
                        arsc3.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals(majorName)){
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
        protected void onPostExecute(ArrayList<PlanInfo> result) {
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
                Snackbar.make(fab, "You have no plans! Make a plan by pressing the + button.", Snackbar.LENGTH_LONG).show();
            } else {
                planSpinner.setVisibility(View.VISIBLE);
                spinAdapter = new ArrayAdapter<String>(Plan.this, android.R.layout.simple_spinner_item, myStrings);
                spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                planSpinner.setAdapter(spinAdapter);
                planSpinner.setSelection(myStrings.size() - 1);
            }

            //this could prob be put in the "else" block above tho,.. idk why i put it in separate
            if(result.size()!=0) {
                String omg = planSpinner.getSelectedItem().toString();
                int fail = omg.indexOf(" ");
                String thePlanId = omg.substring(0, fail);
                if(justCreated==1){
                    new GetPlanItemsTask().execute(Integer.valueOf(thePlanId));
                    justCreated=0;
                    setupContent();
                }
            }
            progDialog.dismiss();
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

            new GetStreamCoursesTask().execute(programCode);
            new GetPlanItemsTask().execute(result.getPlanId());
            setupContent();
        }
    }

    private class GetPlanItemsTask extends AsyncTask<Integer, Void, ArrayList<ArrayList<com.example.mydegree.Room.Plan>>>{

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

            for (int i = 0; i<myList.size();i++){
                if (myList.get(i).getYear()==1){
                    switch (myList.get(i).getTerm()){
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
                } else if (myList.get(i).getYear()==2){
                    switch (myList.get(i).getTerm()){
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
                } else if (myList.get(i).getYear()==3){
                    switch (myList.get(i).getTerm()){
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
                } else if (myList.get(i).getYear()==4){
                    switch (myList.get(i).getTerm()){
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
            masterList.add(term1);
            masterList.add(term2);
            masterList.add(term3);
            masterList.add(term4);
            masterList.add(term5);
            masterList.add(term6);
            masterList.add(term7);
            masterList.add(term8);
            masterList.add(term9);
            masterList.add(term10);
            masterList.add(term11);
            masterList.add(term12);


            return masterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<com.example.mydegree.Room.Plan>> result) {
            int i = 0;
            ar1 = result.get(i);
            p1.setPlan(result.get(i)); i++;
            ar2 = result.get(i);
            p2.setPlan(result.get(i)); i++;
            ar3 = result.get(i);
            p3.setPlan(result.get(i)); i++;
            ar4 = result.get(i);
            p4.setPlan(result.get(i)); i++;
            ar5 = result.get(i);
            p5.setPlan(result.get(i)); i++;
            ar6 = result.get(i);
            p6.setPlan(result.get(i)); i++;
            ar7 = result.get(i);
            p7.setPlan(result.get(i)); i++;
            ar8 = result.get(i);
            p8.setPlan(result.get(i)); i++;
            ar9 = result.get(i);
            p9.setPlan(result.get(i)); i++;
            ar10 = result.get(i);
            p10.setPlan(result.get(i)); i++;
            ar11 = result.get(i);
            p11.setPlan(result.get(i)); i++;
            ar12 = result.get(i);
            p12.setPlan(result.get(i));


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
