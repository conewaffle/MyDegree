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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private String programCode, major, planName;
    private Button button;
    private TextView majSpin;
    private EditText editName;
    private String fullProgString;
    public static final String RESULT_PROG = "resultProg";
    public static final String RESULT_MAJOR = "resultMajor";
    public static final String RESULT_NAME = "resultName";
    private ArrayList<Bookmark> majorNames;
    private String majorTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        button = findViewById(R.id.pickProgBtn);
        editName = findViewById(R.id.namePlan);
        progSpinner = findViewById(R.id.progSpinner);
        majorSpinner = findViewById(R.id.majorSpinner);
        majSpin = findViewById(R.id.textMajSpin);
        majorSpinner.setVisibility(View.GONE);
        majSpin.setVisibility(View.GONE);
        setTitle("Create a plan");

        new GetSpinnerItemsTask().execute();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent(AddPlan.this, Plan.class);
                resultIntent.putExtra(RESULT_PROG, programCode);
                if(major!=null) {
                    resultIntent.putExtra(RESULT_MAJOR, major);
                }
                planName = editName.getText().toString();
                if(planName.isEmpty()){
                    if(major!=null) {
                        if(returnMajorTitle(major)!=null){
                            resultIntent.putExtra(RESULT_NAME, fullProgString + " - " + returnMajorTitle(major));
                        } else {
                            resultIntent.putExtra(RESULT_NAME, fullProgString);
                        }
                    } else {
                        resultIntent.putExtra(RESULT_NAME, fullProgString);
                    }
                } else {
                    resultIntent.putExtra(RESULT_NAME,planName);
                }
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


        //region testing spinnerselectedlisteners
        progSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                programCode = lol.substring(0,4);
                fullProgString = lol.substring(7);
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
                String lol = (String) parent.getItemAtPosition(position);
                major = lol.substring(0,6);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                major = null;
            }
        });
        //endregion


    }

    private String returnMajorTitle(String majorCode){

        String result = null;
        for(int i = 0; i<majorNames.size(); i++){
            if(majorNames.get(i).getCourseCode().equals(majorCode)){
                result = majorNames.get(i).getCourseName();
            }
        }

        return result;
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

            ArrayList<Bookmark> dummyMajor = (ArrayList<Bookmark>) db.courseDao().getMajors();
            ArrayList<String> majors = new ArrayList<>();
            for(int i =0; i<dummyMajor.size(); i++){
                String lol = dummyMajor.get(i).getCourseCode() + " - " + dummyMajor.get(i).getCourseName();
                majors.add(lol);
            }
            majorNames = dummyMajor;
            ArrayList<Bookmark> dummyProgram = (ArrayList<Bookmark>) db.courseDao().getProgramList();
            ArrayList<String> programs = new ArrayList<>();
            for(int i =0; i<dummyProgram.size(); i++){
                String ok = dummyProgram.get(i).getCourseCode() + " - " + dummyProgram.get(i).getCourseName();
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
            myMajors.add(0,"No major chosen yet");

            progAdapter = new ArrayAdapter<String>(AddPlan.this, android.R.layout.simple_spinner_item, myPrograms);
            majorAdapter = new ArrayAdapter<String>(AddPlan.this, android.R.layout.simple_spinner_item, myMajors);
            progAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            progSpinner.setAdapter(progAdapter);
            majorSpinner.setAdapter(majorAdapter);

        }
    }

}
