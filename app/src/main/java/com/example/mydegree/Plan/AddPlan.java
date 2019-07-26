package com.example.mydegree.Plan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mydegree.Program;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddPlan extends AppCompatActivity {

    private Spinner progSpinner, majorSpinner;
    private ArrayList<String> programList, majorList;
    private ArrayAdapter<String> progAdapter, majorAdapter;
    private String programCode, major;
    private Button button;
    public static final String RESULT_PROG = "resultProg";
    public static final String RESULT_MAJOR = "resultMajor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        button = findViewById(R.id.pickProgBtn);
        progSpinner = findViewById(R.id.progSpinner);
        majorSpinner = findViewById(R.id.majorSpinner);
        majorSpinner.setVisibility(View.GONE);

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CourseDb db = Room
                        .databaseBuilder(AddPlan.this, CourseDb.class, "coursedb")
                        .build();
                programList = (ArrayList<String>) db.courseDao().getProgramList();
                majorList = (ArrayList<String>) db.courseDao().getMajors();
                progAdapter = new ArrayAdapter<String>(AddPlan.this, android.R.layout.simple_spinner_item, programList);
                majorAdapter = new ArrayAdapter<String>(AddPlan.this, android.R.layout.simple_spinner_item, majorList);
                progAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                progSpinner.setAdapter(progAdapter);
                majorSpinner.setAdapter(majorAdapter);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(AddPlan.this, Plan.class);
                resultIntent.putExtra(RESULT_PROG, programCode);
                if(major!=null) {
                    resultIntent.putExtra(RESULT_MAJOR, major);
                }
                setResult(RESULT_OK);
                finish();
            }
        });


        //region testing spinnerselectedlisteners
        progSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                programCode = lol.substring(0,4);
                if (programCode.equals("3584")){
                    majorSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // parent.getItemAtPosition(pos)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion



    }

}
