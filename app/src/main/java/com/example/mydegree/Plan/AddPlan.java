package com.example.mydegree.Plan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mydegree.Bookmark;
import com.example.mydegree.Program;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddPlan extends AppCompatActivity {

    private Spinner progSpinner, majorSpinner;
    private ArrayAdapter<String> progAdapter, majorAdapter;
    private String programCode, major;
    private Button button;
    private TextView majSpin;
    public static final String RESULT_PROG = "resultProg";
    public static final String RESULT_MAJOR = "resultMajor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        button = findViewById(R.id.pickProgBtn);
        progSpinner = findViewById(R.id.progSpinner);
        majorSpinner = findViewById(R.id.majorSpinner);
        majSpin = findViewById(R.id.textMajSpin);
        majorSpinner.setVisibility(View.GONE);
        majSpin.setVisibility(View.GONE);

        new GetSpinnerItemsTask().execute();


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
                    majSpin.setVisibility(View.VISIBLE);
                    majorSpinner.setVisibility(View.VISIBLE);
                } else {
                    majorSpinner.setVisibility(View.GONE);
                    majSpin.setVisibility(View.GONE);
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

    private class GetSpinnerItemsTask extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... voids) {
            CourseDb db = Room
                    .databaseBuilder(AddPlan.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<String> majors = (ArrayList<String>) db.courseDao().getMajors();

            ArrayList<Bookmark> dummyList = (ArrayList<Bookmark>) db.courseDao().getProgramList();
            ArrayList<String> programs = new ArrayList<>();
            for(int i =0; i<dummyList.size(); i++){
                String ok = dummyList.get(i).getCourseCode() + " - " + dummyList.get(i).getCourseName();
                programs.add(ok);
            }

            ArrayList<ArrayList<String>> masterList = new ArrayList<>();
            masterList.add(programs);
            masterList.add(majors);

            return masterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> result){
            ArrayList<String> myPrograms = result.get(0);
            ArrayList<String> myMajors = result.get(1);

            progAdapter = new ArrayAdapter<String>(AddPlan.this, android.R.layout.simple_spinner_item, myPrograms);
            majorAdapter = new ArrayAdapter<String>(AddPlan.this, android.R.layout.simple_spinner_item, myMajors);
            progAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            progSpinner.setAdapter(progAdapter);
            majorSpinner.setAdapter(majorAdapter);

        }
    }

}