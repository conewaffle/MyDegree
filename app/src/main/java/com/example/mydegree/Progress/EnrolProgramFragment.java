package com.example.mydegree.Progress;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.example.mydegree.Bookmark;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;

import java.util.ArrayList;

public class EnrolProgramFragment extends DialogFragment {

    private Spinner programSpinner, majorSpinner;
    private ArrayAdapter<String> progAdapter, majorAdapter;
    private String programCode, major;
    private Button confirmEnrol;
    private TextView textMajor;
    private Bundle resultBundle;
    public static final String RESULT_PROG = "resultProg";
    public static final String RESULT_MAJOR = "resultMajor";
    private ArrayList<Bookmark> majorNames;

    public EnrolProgramFragment(){    }

    public static EnrolProgramFragment newInstance() {
        EnrolProgramFragment frag = new EnrolProgramFragment();
        return frag;
    }

    public interface EnrolProgramListener{
        void onFinishEnrol(Bundle bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.y = 100;
        getDialog().getWindow().setAttributes(p);

        return inflater.inflate(R.layout.fragment_enrol_prog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        programSpinner = view.findViewById(R.id.programSpinner);
        majorSpinner = view.findViewById(R.id.majorSpinner2);


        textMajor = view.findViewById(R.id.textMajor);

        confirmEnrol = view.findViewById(R.id.confirmEnrol);
        confirmEnrol.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultBundle = new Bundle();
                resultBundle.putString(RESULT_PROG, major);
                resultBundle.putString(RESULT_PROG, programCode);
                EnrolProgramFragment.EnrolProgramListener listener = (EnrolProgramListener) getActivity();
                listener.onFinishEnrol(resultBundle);
                dismiss();
            }
        });

        programSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                programCode = lol.substring(0,4);
                if (programCode.equals("3584")){
                    textMajor.setVisibility(View.VISIBLE);
                    majorSpinner.setVisibility(View.VISIBLE);
                } else {
                    majorSpinner.setVisibility(View.GONE);
                    textMajor.setVisibility(View.GONE);
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
            }
        });

        new GetSpinnerItemsTask().execute();

    }

    private class GetSpinnerItemsTask extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... voids) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
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

            progAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, myPrograms);
            majorAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, myMajors);
            progAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            programSpinner.setAdapter(progAdapter);
            majorSpinner.setAdapter(majorAdapter);

        }
    }
}
