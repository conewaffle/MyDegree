package com.example.mydegree.Plan;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class PickCourseFragment extends DialogFragment {

    private Spinner courseSpinner;
    private Button confirm;
    private ArrayAdapter<String> courseAdapter;
    public static final String FRAG_PROGRAM = "fragProgram";
    public static final String FRAG_MAJOR = "fragMajor";
    public static final String FRAG_TERM = "fragTerm";

    public PickCourseFragment(){

    }

    public static PickCourseFragment newInstance(String program, int term) {
        PickCourseFragment frag = new PickCourseFragment();
        Bundle args = new Bundle();
        args.putString(FRAG_PROGRAM, program);
        args.putInt(FRAG_TERM, term);
        frag.setArguments(args);

        return frag;
    }

    //INCOMPLETE
    public interface PickCoursesListener{
        void onFinishPick(Bundle bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pick_course, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseSpinner = (Spinner) view.findViewById(R.id.courseSpinner);

        String program = getArguments().getString(FRAG_PROGRAM);
        int term = getArguments().getInt(FRAG_TERM, 0);
        getDialog().setTitle("Pick a Course");


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

            ArrayList<StreamCourse> myList = new ArrayList<>();
            if(term==1) {
                myList = (ArrayList<StreamCourse>) db.courseDao().getTermOne(prog);
            } else if(term==2){
                myList = (ArrayList<StreamCourse>) db.courseDao().getTermTwo(prog);
            } else if(term==3){
                myList = (ArrayList<StreamCourse>) db.courseDao().getTermThree(prog);
            }

            ArrayList<String> myStrings = new ArrayList<>();
            for(int i=0; i<myList.size(); i++){
                myStrings.add(myList.get(i).getStreamCourse());
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
