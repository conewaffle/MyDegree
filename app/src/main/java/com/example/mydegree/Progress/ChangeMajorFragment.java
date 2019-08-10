package com.example.mydegree.Progress;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.example.mydegree.Bookmark;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.Change;

import java.util.ArrayList;

public class ChangeMajorFragment extends DialogFragment {

    private Spinner majorSpinner, programSpinner;
    private ArrayAdapter<String> majorAdapter;
    private String major;
    private Button confirmEnrol;
    private TextView textProgram;
    private Bundle resultBundle;
    public static final String RESULT_MAJOR = "resultMajor";
    private ArrayList<Bookmark> majorNames;


    public ChangeMajorFragment(){    }

    public static ChangeMajorFragment newInstance() {
        ChangeMajorFragment frag = new ChangeMajorFragment();
        return frag;
    }

    public interface ChangeMajorListener{
        void onFinishChangeMajor(String majorFullTitle);
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
        programSpinner.setVisibility(View.GONE);
        majorSpinner = view.findViewById(R.id.majorSpinner2);

        textProgram = view.findViewById(R.id.programText);
        textProgram.setVisibility(View.GONE);

        confirmEnrol = view.findViewById(R.id.confirmEnrol);
        confirmEnrol.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultBundle = new Bundle();
                if(major.equals("No major chosen yet")){
                    major = "Major not declared";
                }
                resultBundle.putString(RESULT_MAJOR, major);

                ChangeMajorFragment.ChangeMajorListener listener = (ChangeMajorListener) getActivity();
                listener.onFinishChangeMajor(major);
                ((Program) getActivity()).onProgramUpdate(((Program) getActivity()).getProgCode(), major);
                dismiss();

            }
        });

        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lol = (String) parent.getItemAtPosition(position);
                //major = lol.substring(0,6);
                major =  lol;
                //i.e. major is the full name including major code and major name to make populating things easier later on
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        new GetSpinnerItemsTask().execute();

    }

    private class GetSpinnerItemsTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
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

            return majors;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result){
            ArrayList<String> myMajors = result;
            myMajors.add(0,"No major chosen yet");
            majorAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, myMajors);
            majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            majorSpinner.setAdapter(majorAdapter);

        }
    }
}