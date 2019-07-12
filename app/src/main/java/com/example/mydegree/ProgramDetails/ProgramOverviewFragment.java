package com.example.mydegree.ProgramDetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.Program;
import com.example.mydegree.Search.SearchAdapter;

import java.util.ArrayList;

import static com.example.mydegree.ProgramDetails.ProgramDetail.PROG_PARCEL;

public class ProgramOverviewFragment extends Fragment {

    private View view;
    private Program myProgram;
    private TextView duration, uoc, desc, name, code;
    private ProgressDialog progDialog;
    private String progString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_program_overview, container, false);

        progDialog = new ProgressDialog(getActivity());

        Intent i = getActivity().getIntent();
        if (i!=null){
            progString = i.getStringExtra(SearchAdapter.PROG_CODE);
            new GetProgramTask().execute(progString);
        }


        return view;
    }

    private class GetProgramTask extends AsyncTask<String, Void, ArrayList<Program>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Program...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<Program> doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(getActivity() , CourseDb.class, "coursedb")
                    .build();

            ArrayList<Program> programList = (ArrayList<Program>) db.courseDao().getPrograms(query[0]);
            return programList;
        }

        @Override
        protected void onPostExecute(ArrayList<Program> result){
            myProgram = result.get(0);

            duration = view.findViewById(R.id.duration);
            uoc = view.findViewById(R.id.totalUoc);
            desc = view.findViewById(R.id.progDesc);
            name = view.findViewById(R.id.progDetailName);
            code = view.findViewById(R.id.progDetailCode);
            desc.setText(myProgram.getProgDesc());
            uoc.setText(myProgram.getProgUoc() + " UOC");
            duration.setText(myProgram.getYears() + " Years");
            name.setText(myProgram.getProgName());
            code.setText(myProgram.getProgCode());

            progDialog.dismiss();
        }
    }
}
