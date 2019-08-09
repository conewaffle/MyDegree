package com.example.mydegree.Progress;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mydegree.Bookmark;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.StreamCourse;
import com.example.mydegree.Room.StreamCoursePlan;

import java.util.ArrayList;
import java.util.Collections;

public class PickEnrolItemFragment extends DialogFragment {


    public static final String FRAG_PROGRAM = "fragProgram";
    public static final String RESULT_COURSE = "resultCourse";
    private RecyclerView planCycler;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private EnrolSearchAdapter mAdapter;
    private ArrayList<String> dbResult;
    private ArrayList<String> searchFilteredResult;

    public PickEnrolItemFragment(){    }

    public static PickEnrolItemFragment newInstance(String program) {
        PickEnrolItemFragment frag = new PickEnrolItemFragment();
        Bundle args = new Bundle();
        args.putString(FRAG_PROGRAM, program);
        frag.setArguments(args);
        return frag;
    }

    public interface PickEnrolItemListener{
        void onFinishPickEnrolItem(String string);
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

        planCycler = view.findViewById(R.id.plansearchcycler);
        planCycler.setHasFixedSize(true);
        planCycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView = view.findViewById(R.id.plansearch);
        searchView.setIconifiedByDefault(false);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        queryTextListener = new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText){
                Log.i("onQueryTextChange", newText);
                doSearch(newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query){
                Log.i("onQueryTextSubmit", query);
                doSearch(query);
                searchView.clearFocus();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        new GetSpinnerCourses().execute(((Program) getActivity()).getProgCode());

    }

    private void doSearch(String query){
        if(query.isEmpty()){
            mAdapter = new EnrolSearchAdapter(dbResult, getActivity());
            planCycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            searchFilteredResult = new ArrayList<>();
            for(int i = 0; i<dbResult.size();i++){
                if(dbResult.get(i).toUpperCase().contains(query.toUpperCase())){
                    searchFilteredResult.add(dbResult.get(i));
                }
            }
            mAdapter = new EnrolSearchAdapter(searchFilteredResult, getActivity());
            planCycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class GetSpinnerCourses extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            ArrayList<StreamCoursePlan> myList = new ArrayList<>();
            myList = (ArrayList<StreamCoursePlan>) db.courseDao().getAllTerms(strings[0]);

            ArrayList<String> myStrings = new ArrayList<>();
            for(int i=0; i<myList.size(); i++){
                myStrings.add(myList.get(i).getStreamCourse() + " - " + myList.get(i).getStreamName());
            }

            Collections.sort(myStrings);

            return myStrings;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result){
            dbResult = result;
            mAdapter = new EnrolSearchAdapter(result, getActivity());
            planCycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
      /*  final Plan activity = (Plan) getActivity();
        activity.setIfSwapping(0);*/
    }
}
