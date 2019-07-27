package com.example.mydegree.ProgramDetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.Stream;
import com.example.mydegree.Room.StreamCourse;
import com.example.mydegree.Search.Search;
import com.example.mydegree.Search.SearchAdapter;

import java.util.ArrayList;


public class ProgramCoursesFragment extends Fragment {

    private View view;
    private RecyclerView r1, r2, r3, r4, r5,r6, r7, r8, r9, r10;
    private CourseAdapter c1, c2, c3, c4, c5, c6, c7,c8,c9,c10;
    private ProgressDialog  progDialog;
    private CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, free7, biz8;
    private ArrayList<String> streamIds;
    private String progString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_program_courses, container, false);

        progDialog = new ProgressDialog(getActivity());

        //region recyclerView setups, if getActivity() doesnt work try view.getContext()
        r1 = view.findViewById(R.id.recycler1);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r2 = view.findViewById(R.id.recycler2);
        r2.setHasFixedSize(true);
        r2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r3 = view.findViewById(R.id.recycler3);
        r3.setHasFixedSize(true);
        r3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r4 = view.findViewById(R.id.recycler4);
        r4.setHasFixedSize(true);
        r4.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r5 = view.findViewById(R.id.recycler5);
        r5.setHasFixedSize(true);
        r5.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r6 = view.findViewById(R.id.recycler6);
        r6.setHasFixedSize(true);
        r6.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r7 = view.findViewById(R.id.recycler7);
        r7.setHasFixedSize(true);
        r7.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r8 = view.findViewById(R.id.recycler8);
        r8.setHasFixedSize(true);
        r8.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r9 = view.findViewById(R.id.recycler9);
        r9.setHasFixedSize(true);
        r9.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r10 = view.findViewById(R.id.recycler10);
        r10.setHasFixedSize(true);
        r10.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //endregion

        //region adapter instantiation
        c1 = new CourseAdapter(new ArrayList<StreamCourse>());
        c2 = new CourseAdapter(new ArrayList<StreamCourse>());
        c3 = new CourseAdapter(new ArrayList<StreamCourse>());
        c4 = new CourseAdapter(new ArrayList<StreamCourse>());
        c5 = new CourseAdapter(new ArrayList<StreamCourse>());
        c6 = new CourseAdapter(new ArrayList<StreamCourse>());
        c7 = new CourseAdapter(new ArrayList<StreamCourse>());
        c8 = new CourseAdapter(new ArrayList<StreamCourse>());
        c9 = new CourseAdapter(new ArrayList<StreamCourse>());
        c10 = new CourseAdapter(new ArrayList<StreamCourse>());
        //endregion

        //region bind adapter to recycler
        r1.setAdapter(c1);
        r2.setAdapter(c2);
        r3.setAdapter(c3);
        r4.setAdapter(c4);
        r5.setAdapter(c5);
        r6.setAdapter(c6);
        r7.setAdapter(c7);
        r8.setAdapter(c8);
        r9.setAdapter(c9);
        r10.setAdapter(c10);
        //endregion

        //region textview setups
        t1 = view.findViewById(R.id.textTopic1);
        t2 = view.findViewById(R.id.textTopic2);
        t3 = view.findViewById(R.id.textTopic3);
        t4 = view.findViewById(R.id.textTopic4);
        t5 = view.findViewById(R.id.textTopic5);
        t6 = view.findViewById(R.id.textTopic6);
        t7 = view.findViewById(R.id.textTopic7);
        t8 = view.findViewById(R.id.textTopic8);
        t9 = view.findViewById(R.id.textTopic9);
        t10 = view.findViewById(R.id.textTopic10);
        //endregion

        //region card setups
        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);
        card6 = view.findViewById(R.id.card6);
        card7 = view.findViewById(R.id.card7);
        card8 = view.findViewById(R.id.card8);
        card9 = view.findViewById(R.id.card9);
        card10 = view.findViewById(R.id.card10);
        //endregion

        //region setting visibility of cards to gone for start
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.GONE);
        card5.setVisibility(View.GONE);
        card6.setVisibility(View.GONE);
        card7.setVisibility(View.GONE);
        card8.setVisibility(View.GONE);
        card9.setVisibility(View.GONE);
        card10.setVisibility(View.GONE);
        //endregion

        free7 = view.findViewById(R.id.freeText);
        biz8 = view.findViewById(R.id.bizElec);
        free7.setVisibility(View.GONE);
        biz8.setVisibility(View.GONE);

        Intent i = getActivity().getIntent();
        progString = i.getStringExtra(SearchAdapter.PROG_CODE);
        if (progString!=null) {
            new GetStreamsTask().execute(progString);
        }

        return view;


    }

    private class GetStreamsTask extends AsyncTask<String, Void, ArrayList<Stream>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Courses...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<Stream> doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            ArrayList<Stream> streamList = (ArrayList<Stream>) db.courseDao().getStreams(query[0]);
            return streamList;
        }

        @Override
        protected void onPostExecute(ArrayList<Stream> result){

            //region setting card appearances based on number of streams
            for(int i=0;i<result.size();i++){
                if (i==0){
                    card1.setVisibility(View.VISIBLE);
                    t1.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==1){
                    card2.setVisibility(View.VISIBLE);
                    t2.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==2){
                    card3.setVisibility(View.VISIBLE);
                    t3.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==3){
                    card4.setVisibility(View.VISIBLE);
                    t4.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==4){
                    card5.setVisibility(View.VISIBLE);
                    t5.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==5){
                    card6.setVisibility(View.VISIBLE);
                    t6.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==6){
                    card7.setVisibility(View.VISIBLE);
                    t7.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==7){
                    card8.setVisibility(View.VISIBLE);
                    t8.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==8){
                    card9.setVisibility(View.VISIBLE);
                    t9.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                } else if (i==9){
                    card10.setVisibility(View.VISIBLE);
                    t10.setText(result.get(i).getStreamName() + " (" + result.get(i).getStreamUoc() + " UOC)");
                }
            }
            //endregion

            streamIds = new ArrayList<>();
            for(int i=0;i<result.size();i++){
                streamIds.add(result.get(i).getId());
            }

            new GetStreamCoursesTask().execute();

        }
    }

    private class GetStreamCoursesTask extends AsyncTask<Void, Void, ArrayList<ArrayList<StreamCourse>>> {

        @Override
        protected void onPreExecute(){
           super.onPreExecute();

        }

        @Override
        protected ArrayList<ArrayList<StreamCourse>> doInBackground(Void... voids) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            ArrayList<ArrayList<StreamCourse>> masterList = new ArrayList<>();
            for(int i=0;i<streamIds.size();i++){
                masterList.add((ArrayList<StreamCourse>) db.courseDao().getStreamCourses(streamIds.get(i)));
            }

            return masterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<StreamCourse>> result){

            for(int i = 0; i<result.size();i++){
                if (i==0){
                    c1.setCourses(result.get(i));
                } else if (i==1){
                    c2.setCourses(result.get(i));
                } else if (i==2){
                    c3.setCourses(result.get(i));
                } else if (i==3){
                    c4.setCourses(result.get(i));
                } else if (i==4){
                    c5.setCourses(result.get(i));
                } else if (i==5){
                    c6.setCourses(result.get(i));
                } else if (i==6){
                    c7.setCourses(result.get(i));
                    if(result.get(i).size()==0){
                        free7.setVisibility(View.VISIBLE);
                        free7.setText("24 UOC of courses from Business School or other faculties");
                    }
                } else if (i==7){
                    c8.setCourses(result.get(i));
                    if(result.get(i).size()==0){
                        biz8.setVisibility(View.VISIBLE);
                        biz8.setText("Up to 30 UOC (depending on major) of Business School courses to ensure minimum program requirements are met.");
                    }
                } else if (i==8){
                    c9.setCourses(result.get(i));
                } else if (i==9){
                    c10.setCourses(result.get(i));
                }
            }

            progDialog.dismiss();
        }
    }

}
