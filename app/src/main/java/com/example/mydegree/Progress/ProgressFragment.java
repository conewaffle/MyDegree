package com.example.mydegree.Progress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.EnrolmentInfo;
import com.example.mydegree.Room.EnrolmentItem;
import com.example.mydegree.Room.StreamCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProgressFragment extends Fragment implements Program.ProgramUpdateListener, Program.CourseUpdateListener {

    private View view;
    private ProgressDialog progDialog;
    private FloatingActionButton fab;

    private TextView progName, progCode, progMajor;
    private String programCode, majorCode, majorFullName;

    private RecyclerView rs1, rs2, rs3, rs4, rs5, rs6;
    private CardView cs1, cs2, cs3, cs4, cs5, cs6;
    private CardView streamsCard;
    private TextView ts1, ts2, ts3, ts4, ts5, ts6, uoc1, uoc2, uoc3, uoc4, uoc5, uoc6;
    private EnrolAdapter es1, es2, es3, es4, es5, es6;
    private ArrayList<EnrolmentItem> ars1, ars2, ars3, ars4, ars5, ars6;
    private ArrayList<StreamCourse> arsc1, arsc2, arsc3, arsc4, arsc5, arsc6;

    private TextView yourCourses, yourCheckList;

    private TextView pt1, pt2, pt3, pt4, pt5, pt6, pt7;
    private ProgressBar pb1, pb2, pb3, pb4, pb5, pb6, pb7;
    private TextView ptuoc1, ptuoc2, ptuoc3, ptuoc4, ptuoc5, ptuoc6, ptuoc7;
    private int pb1max, pb1now, pb2max, pb2now, pb3max, pb3now, pb4max, pb4now, pb5max, pb5now, pb6max, pb6now, pb7max, pb7now;
    private CardView pc5;
    private CardView checkListCard;
    public static final String ENROL_FRAG_TAG = "enrolFragTag";
    public static final String PICK_ENROL_ITEM_FRAG_TAG = "pickEnrolItemFragTag";

    private NestedScrollView progressScroll;

    private EnrolmentItem temporaryItem;
    //TODO: Firebase

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_progress, container, false);

        progDialog = new ProgressDialog(getActivity());


        fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //only do if program is null
                if(((Program) getActivity()).getProgCode()==null){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    EnrolProgramFragment enrolProgramFragment = EnrolProgramFragment.newInstance();
                    enrolProgramFragment.show(fm, ENROL_FRAG_TAG);
                } else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    PickEnrolItemFragment pickEnrolItemFragment = PickEnrolItemFragment.newInstance(((Program) getActivity()).getProgCode());
                    pickEnrolItemFragment.show(fm, PICK_ENROL_ITEM_FRAG_TAG);
                }

            }
        });

        progressScroll = view.findViewById(R.id.progressScroll);
        progressScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });


        progName = view.findViewById(R.id.progName);
        progName.setText("Not yet enrolled.");
        progCode = view.findViewById(R.id.progCode);
        progCode.setText("Press the + button to enrol in a program.");
        progMajor = view.findViewById(R.id.progMajor);

        // only for 3584
        progMajor.setVisibility(View.GONE);

        //region setting up progress things
        pb1 = view.findViewById(R.id.pb1);
        pb2 = view.findViewById(R.id.pb2);
        pb3 = view.findViewById(R.id.pb3);
        pb4 = view.findViewById(R.id.pb4);
        pb5 = view.findViewById(R.id.pb5);
        pb6 = view.findViewById(R.id.pb6);
        pb7 = view.findViewById(R.id.pb7);
        pt1 = view.findViewById(R.id.pt1);
        pt2 = view.findViewById(R.id.pt2);
        pt3 = view.findViewById(R.id.pt3);
        pt4 = view.findViewById(R.id.pt4);
        pt5 = view.findViewById(R.id.pt5);
        pt6 = view.findViewById(R.id.pt6);
        pt7 = view.findViewById(R.id.pt7);
        ptuoc1 = view.findViewById(R.id.ptuoc1);
        ptuoc2 = view.findViewById(R.id.ptuoc2);
        ptuoc3 = view.findViewById(R.id.ptuoc3);
        ptuoc4 = view.findViewById(R.id.ptuoc4);
        ptuoc5 = view.findViewById(R.id.ptuoc5);
        ptuoc6 = view.findViewById(R.id.ptuoc6);
        ptuoc7 = view.findViewById(R.id.ptuoc7);
        pc5 = view.findViewById(R.id.pc5);
        //endregion

        //region STREAMS SETUPS

        //set up new stream ArrayList
        ars1 = new ArrayList<>();
        ars2 = new ArrayList<>();
        ars3 = new ArrayList<>();
        ars4 = new ArrayList<>();
        ars5 = new ArrayList<>();
        ars6 = new ArrayList<>();
        arsc1 = new ArrayList<>();
        arsc2 = new ArrayList<>();
        arsc3 = new ArrayList<>();
        arsc4 = new ArrayList<>();
        arsc5 = new ArrayList<>();
        arsc6 = new ArrayList<>();

        //set up textViews
        ts1 = view.findViewById(R.id.streamText1);
        ts2 = view.findViewById(R.id.streamText2);
        ts3 = view.findViewById(R.id.streamText3);
        ts4 = view.findViewById(R.id.streamText4);
        ts5 = view.findViewById(R.id.streamText5);
        ts6 = view.findViewById(R.id.streamText6);
        uoc1 = view.findViewById(R.id.streamUOC1);
        uoc2 = view.findViewById(R.id.streamUOC2);
        uoc3 = view.findViewById(R.id.streamUOC3);
        uoc4 = view.findViewById(R.id.streamUOC4);
        uoc5 = view.findViewById(R.id.streamUOC5);
        uoc6 = view.findViewById(R.id.streamUOC6);

        streamsCard = view.findViewById(R.id.streamsCard);
        yourCourses = view.findViewById(R.id.yourCompleted);
        yourCheckList = view.findViewById(R.id.yourChecklist);
        checkListCard = view.findViewById(R.id.checkListCard);
        streamsCard.setVisibility(View.GONE);
        yourCourses.setVisibility(View.GONE);
        yourCheckList.setVisibility(View.GONE);
        checkListCard.setVisibility(View.GONE);

        //set up recyclers
        rs1 = view.findViewById(R.id.streamRV1);
        rs1.setHasFixedSize(true);
        rs1.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rs2 = view.findViewById(R.id.streamRV2);
        rs2.setHasFixedSize(true);
        rs2.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rs3 = view.findViewById(R.id.streamRV3);
        rs3.setHasFixedSize(true);
        rs3.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rs4 = view.findViewById(R.id.streamRV4);
        rs4.setHasFixedSize(true);
        rs4.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rs5 = view.findViewById(R.id.streamRV5);
        rs5.setHasFixedSize(true);
        rs5.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rs6 = view.findViewById(R.id.streamRV6);
        rs6.setHasFixedSize(true);
        rs6.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        //setting adapters

        es1 = new EnrolAdapter(new ArrayList<EnrolmentItem>());
        es2 = new EnrolAdapter(new ArrayList<EnrolmentItem>());
        es3 = new EnrolAdapter(new ArrayList<EnrolmentItem>());
        es4 = new EnrolAdapter(new ArrayList<EnrolmentItem>());
        es5 = new EnrolAdapter(new ArrayList<EnrolmentItem>());
        es6 = new EnrolAdapter(new ArrayList<EnrolmentItem>());

        //bind recyclers to adapters
        rs1.setAdapter(es1);
        rs2.setAdapter(es2);
        rs3.setAdapter(es3);
        rs4.setAdapter(es4);
        rs5.setAdapter(es5);
        rs6.setAdapter(es6);

        //endregion

        new GetEnrolInfosTask().execute();

        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        EnrolmentItem toDelete;
        int position;
        switch(item.getItemId()) {
            case R.id.menu_remove:
            case R.id.menu_delete:
                switch(((Program) getActivity()).getStreamLastClicked()){
                    case R.id.streamRV1:
                        position = es1.getPosition();
                        toDelete = ars1.get(position);
                        ars1.remove(toDelete);
                        es1.setPlan(ars1);
                        new DeleteEnrolItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV2:
                        position = es2.getPosition();
                        toDelete = ars2.get(position);
                        ars2.remove(toDelete); es2.setPlan(ars2);
                        new DeleteEnrolItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV3:
                        position = es3.getPosition();
                        toDelete = ars3.get(position);
                        ars3.remove(toDelete); es3.setPlan(ars3);
                        new DeleteEnrolItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV4:
                        position = es4.getPosition();
                        toDelete = ars4.get(position);
                        ars4.remove(toDelete); es4.setPlan(ars4);
                        new DeleteEnrolItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV5:
                        position = es5.getPosition();
                        toDelete = ars5.get(position);
                        ars5.remove(toDelete); es5.setPlan(ars5);
                        new DeleteEnrolItemTask().execute(toDelete);
                        break;
                    case R.id.streamRV6:
                        position = es6.getPosition();
                        toDelete = ars6.get(position);
                        ars6.remove(toDelete); es6.setPlan(ars6);
                        new DeleteEnrolItemTask().execute(toDelete);
                        break;
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onProgramUpdate(String programCode2, String majorFullName2) {
        programCode = programCode2;
        majorFullName = majorFullName2;
        String toSet = "";
        switch(programCode){
            case "3584":
                toSet = "Commerce / Information Systems";
                break;
            case "3979":
                toSet = "Information Systems";
                break;
            case "3964":
                toSet = "Information Systems (Co-op) (Hons)";
                break;
        }
        progName.setText(toSet);
        if(programCode.equals("3584")){
            progMajor.setText(majorFullName);
            progMajor.setVisibility(View.VISIBLE);
        }
        progCode.setText(programCode);

        programCode = programCode;
        majorCode = majorFullName.substring(0,6);

        streamsCard.setVisibility(View.VISIBLE);
        if(programCode.equals("3584")){
            ts1.setText("Commerce Core");
            uoc1.setText("24 UOC");
            ts2.setText("Flexible Core");
            uoc2.setText("18 UOC");
            ts3.setText("INFS Core");
            uoc3.setText("72 UOC");
            ts4.setText("Major");
            uoc4.setText("36-48 UOC");
            ts5.setText("Business Elec.");
            uoc5.setText("18-30 UOC");
            ts6.setText("General Ed.");
            uoc6.setText("12 UOC");
        } else if(programCode.equals("3979")){
            ts1.setText("Stage 1 Core");
            uoc1.setText("42 UOC");
            ts2.setText("Stage 2 Core");
            uoc2.setText("24 UOC");
            ts3.setText("Stage 3 Core");
            uoc3.setText("30 UOC");
            ts4.setText("INFS 2/3 Elec.");
            uoc4.setText("12 UOC");
            ts5.setText("Free Electives");
            uoc5.setText("24 UOC");
            ts6.setText("General Ed.");
            uoc6.setText("12 UOC");
        } else if(programCode.equals("3964")){
            ts1.setText("Stage 1 Core");
            uoc1.setText("42 UOC");
            ts2.setText("Stage 2 Core");
            uoc2.setText("24 UOC");
            ts3.setText("Stage 3");
            uoc3.setText("30 UOC");
            ts4.setText("Placement");
            uoc4.setText("36 UOC");
            ts5.setText("Honours");
            uoc5.setText("48 UOC");
            ts6.setText("General Ed.");
            uoc6.setText("12 UOC");
        }

        checkListCard.setVisibility(View.VISIBLE);


        new GetStreamCoursesTask().execute(programCode);

    }


    private class GetStreamCoursesTask extends AsyncTask<String, Void, ArrayList<StreamCourse>> {
        @Override
        protected ArrayList<StreamCourse> doInBackground(String... strings) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            ArrayList<StreamCourse> masterList = (ArrayList<StreamCourse>) db.courseDao().getProgStreamCourses(strings[0]);

            return masterList;
        }

        @Override
        protected void onPostExecute(ArrayList<StreamCourse> result) {
            ars1 = new ArrayList<>();
            es1.setPlan(ars1);
            ars2 = new ArrayList<>();
            es2.setPlan(ars2);
            ars3 = new ArrayList<>();
            es3.setPlan(ars3);
            ars4 = new ArrayList<>();
            es4.setPlan(ars4);
            ars5 = new ArrayList<>();
            es5.setPlan(ars5);
            ars6 = new ArrayList<>();
            es6.setPlan(ars6);
            arsc1 = new ArrayList<>();
            arsc2 = new ArrayList<>();
            arsc3 = new ArrayList<>();
            arsc4 = new ArrayList<>();
            arsc5 = new ArrayList<>();
            arsc6 = new ArrayList<>();
            if(programCode.contains("3584")){
                for(int i=0; i<result.size(); i++){
                    if (result.get(i).getStreamId2().contains("3584COC")){
                        arsc1.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3584F")){
                        arsc2.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3584I1")||result.get(i).getStreamId2().equals("INFS2C")||result.get(i).getStreamId2().equals("39793C")){
                        arsc3.add(result.get(i));
                    } else if(majorCode!=null){
                        if(result.get(i).getStreamId2().equals(majorCode)) {
                            arsc4.add(result.get(i));
                        }
                    } else if(result.get(i).getStreamId2().equals("GENED")){
                        arsc6.add(result.get(i));
                    } else {
                        //EVERYTHING WOULD BE A BIZ ELEC.
                        arsc5.add(result.get(i));
                    }
                }
            } else {
                for(int i=0; i<result.size(); i++){
                    if (result.get(i).getStreamId2().contains("INFS1C")){
                        arsc1.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("INFS2C")){
                        arsc2.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("39793C")||result.get(i).getStreamId2().contains("3964C")){
                        arsc3.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("3979E")||result.get(i).getStreamId2().equals("3964I")){
                        arsc4.add(result.get(i));
                    } else if(result.get(i).getStreamId2().contains("3964H")){
                        arsc5.add(result.get(i));
                    } else if(result.get(i).getStreamId2().equals("GENED")){
                        arsc6.add(result.get(i));
                    } else {
                        //EVERYTHING ELSE WOULD BE A FREE ELECTIVE
                        arsc5.add(result.get(i));
                    }
                }
            }

            new GetEnrolItemsTask().execute(programCode);

        }
    }
    private class GetEnrolInfosTask extends AsyncTask<Void, Void, ArrayList<EnrolmentInfo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<EnrolmentInfo> doInBackground(Void... voids) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            ArrayList<EnrolmentInfo> myList = (ArrayList<EnrolmentInfo>) db.courseDao().getEnrolInfos();
            return myList;
        }

        @Override
        protected void onPostExecute(final ArrayList<EnrolmentInfo> result) {
            if (result.size() == 0) {
                streamsCard.setVisibility(View.GONE);
                checkListCard.setVisibility(View.GONE);
            } else {

                programCode = result.get(0).getProgCode();
                ((Program) getActivity()).setProgCode(programCode);
                majorCode = result.get(0).getMajorId();
                progCode.setText(programCode);
                progMajor.setText(majorCode);
                String toSet = "";
                switch(programCode){
                    case "3584":
                        toSet = "Commerce / Information Systems";
                        break;
                    case "3979":
                        toSet = "Information Systems";
                        break;
                    case "3964":
                        toSet = "Information Systems (Co-op) (Hons)";
                        break;
                }
                progName.setText(toSet);
                if(programCode.equals("3584")){
                    progMajor.setVisibility(View.VISIBLE);
                }
                progCode.setText(programCode);

                streamsCard.setVisibility(View.VISIBLE);
                if(programCode.equals("3584")){
                    ts1.setText("Commerce Core");
                    uoc1.setText("24 UOC");
                    ts2.setText("Flexible Core");
                    uoc2.setText("18 UOC");
                    ts3.setText("INFS Core");
                    uoc3.setText("72 UOC");
                    ts4.setText("Major");
                    uoc4.setText("36-48 UOC");
                    ts5.setText("Business Elec.");
                    uoc5.setText("18-30 UOC");
                    ts6.setText("General Ed.");
                    uoc6.setText("12 UOC");
                } else if(programCode.equals("3979")){
                    ts1.setText("Stage 1 Core");
                    uoc1.setText("42 UOC");
                    ts2.setText("Stage 2 Core");
                    uoc2.setText("24 UOC");
                    ts3.setText("Stage 3 Core");
                    uoc3.setText("30 UOC");
                    ts4.setText("INFS 2/3 Elec.");
                    uoc4.setText("12 UOC");
                    ts5.setText("Free Electives");
                    uoc5.setText("24 UOC");
                    ts6.setText("General Ed.");
                    uoc6.setText("12 UOC");
                } else if(programCode.equals("3964")){
                    ts1.setText("Stage 1 Core");
                    uoc1.setText("42 UOC");
                    ts2.setText("Stage 2 Core");
                    uoc2.setText("24 UOC");
                    ts3.setText("Stage 3");
                    uoc3.setText("30 UOC");
                    ts4.setText("Placement");
                    uoc4.setText("36 UOC");
                    ts5.setText("Honours");
                    uoc5.setText("48 UOC");
                    ts6.setText("General Ed.");
                    uoc6.setText("12 UOC");
                }

                checkListCard.setVisibility(View.VISIBLE);
                yourCourses.setVisibility(View.VISIBLE);
                yourCheckList.setVisibility(View.VISIBLE);

                new GetStreamCoursesTask().execute(programCode);
            }
        }
    }

    private class DeleteEnrolItemTask extends AsyncTask<EnrolmentItem, Void, Void> {
        @Override
        protected Void doInBackground(EnrolmentItem... enrolmentItems) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            db.courseDao().deleteEnrolItem(enrolmentItems[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(), "Course deleted from program.", Toast.LENGTH_SHORT).show();

            fillProgress();
        }
    }

    private class GetEnrolItemsTask extends AsyncTask<String, Void, ArrayList<EnrolmentItem>> {

        @Override
        protected ArrayList<EnrolmentItem> doInBackground(String... strings) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            ArrayList<EnrolmentItem> myList = (ArrayList<EnrolmentItem>) db.courseDao().getEnrolItems(strings[0]);


            return myList;
        }

        @Override
        protected void onPostExecute(final ArrayList<EnrolmentItem> result) {

            for (int i = 0; i < result.size(); i++) {

                fillStreams(result.get(i));
            }

            fillProgress();

        }
    }

    private void fillStreams(EnrolmentItem result) {
        for (int j = 0; j < arsc1.size(); j++) {
            if (arsc1.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars1.add(result);
                es1.setPlan(ars1);
                return;
            }
        }
        for (int j = 0; j < arsc2.size(); j++) {
            if (arsc2.get(j).getStreamCourse().equals(result.getCourseCode())){
                ars2.add(result);
                es2.setPlan(ars2);
                return;
            }
        }
        for (int j = 0; j < arsc3.size(); j++) {
            if (arsc3.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars3.add(result);
                es3.setPlan(ars3);
                return;
            }
        }
        for (int j = 0; j < arsc4.size(); j++) {
            if (arsc4.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars4.add(result);
                es4.setPlan(ars4);
                return;
            }
        }
        for (int j = 0; j < arsc5.size(); j++) {
            if (arsc5.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars5.add(result);
                es5.setPlan(ars5);
                return;
            }
        }
        for (int j = 0; j < arsc6.size(); j++) {
            if (arsc6.get(j).getStreamCourse().equals(result.getCourseCode())) {
                ars6.add(result);
                es6.setPlan(ars6);
                return;
            }
        }
    }

    private void fillProgress(){

        ArrayList<ArrayList<EnrolmentItem>> masterStreams = new ArrayList<>();
        masterStreams.add(ars1);
        masterStreams.add(ars2);
        masterStreams.add(ars3);
        masterStreams.add(ars4);
        masterStreams.add(ars5);
        masterStreams.add(ars6);

        //region progress bar 1 (total uoc for program)

        String progUoc = "";
        switch(programCode){
            case "3584":
            case "3964":
                progUoc = "192";
                break;
            case "3979":
                progUoc = "144";
                break;
        }
        pt1.setText("I have completed minimum " + progUoc + " UOC for the program.");
        pb1max = Integer.valueOf(progUoc);
        pb1.setMax(pb1max);
        pb1now = (ars1.size()+ars2.size()+ars3.size()+ars4.size()+ars5.size()+ars6.size())*6;
        for(int i = 0; i<masterStreams.size();i++){
            for(int j = 0; j<masterStreams.get(i).size();j++){
                if(masterStreams.get(i).get(j).getCourseCode().equals("INFS4802")||masterStreams.get(i).get(j).getCourseCode().equals("2101")){
                    pb1now = pb1now + 6;
                }
            }
        }

        if(pb1now>pb1max){
            pb1.setProgress(pb1max);
        } else {
            pb1.setProgress(pb1now);
        }
        ptuoc1.setText(pb1now+"/"+pb1max);

        ((Program) getActivity()).onRoadmapUpdate(pb1now, pb1max);
        //endregion

        //region progress bar 7 (gen eds)
        pt7.setText("I have completed exactly 12 UOC of Gen Ed courses.");
        pb7max = 12;
        pb7.setMax(pb7max);
        pb7now = ars6.size()*6;
        if(pb7now>pb7max){
            pb7.setProgress(pb7max);
        } else {
            pb7.setProgress(pb7now);
        }
        ptuoc7.setText(pb7now+"/"+pb7max);
        //endregion

        //region progress bar 6 (level 1 course limit)
        int levelOneLimit = 0;
        if(programCode.equals("3979")){
            levelOneLimit = 60;
        } else {
            levelOneLimit = 72;
        }
        pt6.setText("I have completed no more than " + levelOneLimit + " UOC of level 1 courses (excluding Gen Ed).");
        pb6max = levelOneLimit;
        pb6.setMax(pb6max);
        pb6now = 0;
        //streamsSize-1 to not include general ed arraylist at ars6
        for(int i = 0; i<(masterStreams.size()-1);i++){
            for(int j = 0; j<masterStreams.get(i).size();j++){
                if(Character.toString(masterStreams.get(i).get(j).getCourseCode().charAt(4)).equals("1")){
                    pb6now = pb6now + 6;
                }
            }
        }
        if(pb6now>levelOneLimit){
            pb6.setProgress(levelOneLimit);
            ptuoc6.setTextColor(getResources().getColor(R.color.design_default_color_error));
            ptuoc6.setTypeface(Typeface.DEFAULT_BOLD);
            pb6.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFAA00")));
        } else {
            pb6.setProgress(levelOneLimit);
            ptuoc6.setTextColor(Color.GRAY);
            ptuoc6.setTypeface(Typeface.DEFAULT);
            pb6.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        }
        ptuoc6.setText(pb6now+"/"+pb6max);
        //endregion

        //region progressbar 2
        String pb2String = "";
        switch(programCode){
            case "3584":
                pb2String = "I have completed 24 UOC (4 courses) of Commerce Compulsory Core and 72 UOC (12 courses) of INFS Compulsory Core courses";
                pb2max = 96;
                pb2now = (ars1.size()+ars3.size())*6;
                break;
            case "3964":
                pb2String = "I have completed 90 UOC (15 courses) of 1st, 2nd and 3rd year compulsory core courses";
                pb2max = 90;
                pb2now = (ars1.size()+ars2.size()+ars3.size())*6;
                for(int i = 0; i<3;i++){
                    for(int j = 0; j<masterStreams.get(i).size();j++){
                        switch(masterStreams.get(i).get(j).getCourseCode()){
                            case "INFS2631":
                            case "INFS3020":
                            case "INFS3603":
                            case "INFS3632":
                            case "INFS3830":
                            case "INFS3873":
                                pb2now = pb2now - 6;
                                break;
                            default:
                                break;
                        }
                    }
                }
                int econ1203ormath1041 = 0;
                int acct1511orecon1101 = 0;
                for(int i =0; i<ars1.size();i++){
                    switch(ars1.get(i).getCourseCode()){
                        case "ECON1203":
                        case "MATH1041":
                            econ1203ormath1041++;
                            break;
                        case "ACCT1511":
                        case "ECON1101":
                            acct1511orecon1101++;
                            break;
                        default:
                            break;
                    }
                }
                if(econ1203ormath1041==2){
                    pb2now = pb2now - 6;
                }
                if(acct1511orecon1101==2){
                    pb2now = pb2now - 6;
                }
                break;
            case "3979":
                pb2String = "I have completed minimum 84 UOC (14 courses) from the School of ISTM";
                pb2max = 84;
                pb2now = 0;
                for(int i = 0; i<(masterStreams.size()-1);i++){
                    for(int j = 0; j<masterStreams.get(i).size();j++){
                        if(masterStreams.get(i).get(j).getCourseCode().substring(0,4).equals("INFS")){
                            pb2now = pb2now + 6;
                        }
                    }
                }
                break;
        }
        pt2.setText(pb2String);
        pb2.setMax(pb2max);
        if(pb2now>pb2max){
            pb2.setProgress(pb2max);
        } else {
            pb2.setProgress(pb2now);
        }
        ptuoc2.setText(pb2now+"/"+pb2max);
        //endregion

        //region progressbar 3
        String pb3String = "";
        switch(programCode){
            case "3584":
                pb3String = "I have completed 18 UOC of flexible core courses";
                pb3max = 18;
                pb3now = ars2.size()*6;
                break;
            case "3964":
                pb3String = "I have completed 6 UOC of INFS Stage 2/3 elective";
                pb3max = 6;
                pb3now = 0;
                for(int i = 0; i<ars3.size();i++){
                    switch(ars3.get(i).getCourseCode()){
                        case "INFS2631":
                        case "INFS3020":
                        case "INFS3603":
                        case "INFS3632":
                        case "INFS3830":
                        case "INFS3873":
                            pb3now = pb3now + 6;
                            break;
                        default:
                            break;
                    }
                }
                break;
            case "3979":
                pb3String = "I have a minimum of 12 UOC (2 courses) from the INFS Stage 2/3 electives";
                pb3max = 12;
                pb3now = ars3.size()*6;
                break;
        }
        pt3.setText(pb3String);
        pb3.setMax(pb3max);
        if(pb3now>pb3max){
            pb3.setProgress(pb3max);
        } else {
            pb3.setProgress(pb3now);
        }
        ptuoc3.setText(pb3now+"/"+pb3max);
        //endregion

        //region progressbar 4
        String pb4String = "";
        switch(programCode) {
            case "3584":
                pb4String = "I have met the requirements for one Commerce major";
                if (majorCode != null) {
                    if(majorCode.equals("ECONJ1")){
                        pb4max = 60;
                    } else {
                        pb4max = 48;
                    }
                } else {
                    pb4max = 48;
                }

                pb4now = ars4.size() * 6;
                int pb4coremax = 0;
                int pb4corenow = 0;
                int pb4elecmax = pb4max - pb4coremax;
                int pb4elecnow = 0;

                for(int i = 0; i<arsc4.size(); i++){
                    if (arsc4.get(i).isCore()){
                        pb4coremax = pb4coremax + 6;
                    }
                    for (int j = 0; j<ars4.size();j++){
                        if(arsc4.get(i).getStreamCourse().equals(ars4.get(j).getCourseCode())){
                            if(arsc4.get(i).isCore()){
                                pb4corenow = pb4corenow + 6;
                            } else {
                                pb4elecnow = pb4elecnow + 6;
                            }
                        }
                    }
                    for (int j = 0; j<ars1.size();j++){
                        if(arsc4.get(i).getStreamCourse().equals(ars1.get(j).getCourseCode())){
                            if(arsc4.get(i).isCore()){
                                pb4corenow = pb4corenow + 6;
                            }
                        }
                    }
                    for (int j = 0; j<ars2.size();j++){
                        if(arsc4.get(i).getStreamCourse().equals(ars2.get(j).getCourseCode())){
                            if(arsc4.get(i).isCore()){
                                pb4corenow = pb4corenow + 6;
                            }
                        }
                    }
                }
                if(pb4elecnow>pb4elecmax){
                    pb4now = pb4corenow + pb4elecmax;
                } else {
                    pb4now = pb4corenow + pb4elecnow;
                }

                break;
            case "3964":
                pb4String = "I have completed minimum 36 UOC of industrial placement courses";
                pb4max = 36;
                pb4now = ars4.size()*12;
                break;
            case "3979":
                pb4String = "I have completed no more than 24 UOC (4 courses) of free electives";
                pb4max = 24;
                pb4now = ars5.size()*6;
                break;
        }
        pt4.setText(pb4String);
        pb4.setMax(pb4max);
        if(pb4now>pb4max){
            pb4.setProgress(pb4max);
        } else {
            pb4.setProgress(pb4now);
        }
        ptuoc4.setText(pb4now+"/"+pb4max);
        //endregion

        //region progressbar 5
        String pb5String = "";
        switch(programCode){
            case "3584":
                pb5String = "I have completed a minimum of 18 UOC (3 courses) of Business School electives";
                pb5max = 18;
                pb5now = ars5.size()*6;
                break;
            case "3964":
                pb5String = "I have completed 24 UOC of specified level 4 INFS courses plus a 24 UOC thesis";
                pb5max = 48;
                pb5now = ars5.size()*6;
                for(int j = 0; j<ars5.size();j++){
                    if(ars5.get(j).getCourseCode().equals("INFS4802")){
                        pb5now = pb5now + 6;
                    }
                }
                break;
            case "3979":
                pb5max = 100;
                pb5now = 0;
                break;
        }
        pt5.setText(pb5String);
        pb5.setMax(pb5max);
        if(pb5now>pb5max){
            pb5.setProgress(pb5max);
        } else {
            pb5.setProgress(pb5now);
        }
        ptuoc5.setText(pb5now+"/"+pb5max);
        if(programCode.equals("3979")){
            pc5.setVisibility(View.GONE);
        }

    }

    private class InsertEnrolmentItemTask extends AsyncTask<EnrolmentItem, Void, Long>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(EnrolmentItem... enrolmentItem) {
            CourseDb db = Room
                    .databaseBuilder(getActivity(), CourseDb.class, "coursedb")
                    .build();

            try {
                Long myLong = db.courseDao().insertEnrolItem(enrolmentItem[0]);
                return myLong;
            }
            catch (SQLiteConstraintException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            if(aLong==null){
                //if (firebaseLoad != 1) {
                    Toast.makeText(getActivity(),"Error: You have already put this course on your program.", Toast.LENGTH_SHORT).show();
               // }
            } else {
                fillStreams(temporaryItem);
                fillProgress();
                //dismiss fragment dialog
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(PICK_ENROL_ITEM_FRAG_TAG);
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }
            }
        }
    }


    @Override
    public void onCourseUpdate(String courseCode){
        // to do here
        temporaryItem =  new EnrolmentItem(programCode, courseCode);
        new InsertEnrolmentItemTask().execute(temporaryItem);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((Program) context).registerProgramUpdateListener(this);
        ((Program) context).registerCourseUpdateListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((Program) getActivity()).unregisterProgramUpdateListener(this);
        ((Program) getActivity()).unregisterCourseUpdateListener(this);
    }
}