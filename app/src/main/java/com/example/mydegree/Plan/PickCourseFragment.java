package com.example.mydegree.Plan;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.example.mydegree.Bookmark;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.StreamCourse;
import com.example.mydegree.Room.StreamCoursePlan;

import java.util.ArrayList;

public class PickCourseFragment extends DialogFragment {

    private Spinner courseSpinner;
    private Button confirm;
    private Bundle myBundle;
    private String pickedCourse;
    private int term, year;
    private ArrayAdapter<String> courseAdapter;
    public static final String FRAG_PROGRAM = "fragProgram";
    public static final String FRAG_MAJOR = "fragMajor";
    public static final String FRAG_TERM = "fragTerm";
    public static final String FRAG_YEAR = "fragYear";
    public static final String RESULT_COURSE = "resultCourse";

    public PickCourseFragment(){    }

    public static PickCourseFragment newInstance(String program, int year, int term) {
        PickCourseFragment frag = new PickCourseFragment();
        Bundle args = new Bundle();
        args.putString(FRAG_PROGRAM, program);
        args.putInt(FRAG_TERM, term);
        args.putInt(FRAG_YEAR, year);
        frag.setArguments(args);

        return frag;
    }

    public interface PickCoursesListener{
        void onFinishPick(Bundle bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.y = 100;
        getDialog().getWindow().setAttributes(p);

        return inflater.inflate(R.layout.fragment_pick_course, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region setting up spinner onClick
        courseSpinner = (Spinner) view.findViewById(R.id.courseSpinner);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                pickedCourse = lol.substring(0,8);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        //region button set up onClick
        confirm = view.findViewById(R.id.btnConfirmCourse);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBundle = new Bundle();
                myBundle.putString(RESULT_COURSE, pickedCourse);
                myBundle.putInt(FRAG_YEAR, year);
                myBundle.putInt(FRAG_TERM, term);
                PickCoursesListener listener = (PickCoursesListener) getActivity();
                listener.onFinishPick(myBundle);
                //dismiss now moved into onfragmentdialog
                //dismiss();
            }
        }) ;
        //endregion

        term = getArguments().getInt(FRAG_TERM, 0);
        year = getArguments().getInt(FRAG_YEAR,0);

        new GetSpinnerCourses().execute(getArguments());

    }

    private class GetSpinnerCourses extends AsyncTask<Bundle, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Bundle... bundles) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            Bundle bundle = bundles[0];
            String prog = bundle.getString(FRAG_PROGRAM);
            int term = bundle.getInt(FRAG_TERM);

            ArrayList<StreamCoursePlan> myList = new ArrayList<>();
            if(term==1) {
                myList = (ArrayList<StreamCoursePlan>) db.courseDao().getTermOneX(prog);
            } else if(term==2){
                myList = (ArrayList<StreamCoursePlan>) db.courseDao().getTermTwoX(prog);
            } else if(term==3){
                myList = (ArrayList<StreamCoursePlan>) db.courseDao().getTermThreeX(prog);
            }

            ArrayList<String> myStrings = new ArrayList<>();
            for(int i=0; i<myList.size(); i++){
                myStrings.add(myList.get(i).getStreamCourse() + " - " + myList.get(i).getStreamName());
            }

            return myStrings;

        }

        @Override
        protected void onPostExecute(ArrayList<String> result){
            courseAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, result);
            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseSpinner.setAdapter(courseAdapter);

        }
    }



}
