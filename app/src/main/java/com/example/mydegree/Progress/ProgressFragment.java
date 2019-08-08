package com.example.mydegree.Progress;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mydegree.R;
import com.example.mydegree.Room.StreamCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    private View view;
    private ProgressDialog progDialog;
    private FloatingActionButton fab;

    private TextView progName, progCode, progMajor;

    private RecyclerView rs1, rs2, rs3, rs4, rs5, rs6;
    private CardView cs1, cs2, cs3, cs4, cs5, cs6;
    private CardView streamsCard;
    private TextView ts1, ts2, ts3, ts4, ts5, ts6, uoc1, uoc2, uoc3, uoc4, uoc5, uoc6;
    //private PlanAdapter ps1, ps2, ps3, ps4, ps5, ps6;
    //private ArrayList<com.example.mydegree.Room.Plan> ars1, ars2, ars3, ars4, ars5, ars6;
    private ArrayList<StreamCourse> arsc1, arsc2, arsc3, arsc4, arsc5, arsc6;

    private TextView pt1, pt2, pt3, pt4, pt5, pt6, pt7;
    private ProgressBar pb1, pb2, pb3, pb4, pb5, pb6, pb7;
    private TextView ptuoc1, ptuoc2, ptuoc3, ptuoc4, ptuoc5, ptuoc6, ptuoc7;
    private int pb1max, pb1now, pb2max, pb2now, pb3max, pb3now, pb4max, pb4now, pb5max, pb5now, pb6max, pb6now, pb7max, pb7now;
    private CardView pc5;

    //TODO: Firebase

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_progress, container, false);

        progDialog = new ProgressDialog(getActivity());

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        progName = view.findViewById(R.id.progName);
        progCode = view.findViewById(R.id.progCode);
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

        // need to change entity
/*        ars1 = new ArrayList<>();
        ars2 = new ArrayList<>();
        ars3 = new ArrayList<>();
        ars4 = new ArrayList<>();
        ars5 = new ArrayList<>();
        ars6 = new ArrayList<>();*/
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
        streamsCard.setVisibility(View.GONE);

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


/*        //setting adapters

        ps1 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps2 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps3 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps4 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps5 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());
        ps6 = new PlanAdapter(new ArrayList<com.example.mydegree.Room.Plan>());

        //bind recyclers to adapters
        rs1.setAdapter(ps1);
        rs2.setAdapter(ps2);
        rs3.setAdapter(ps3);
        rs4.setAdapter(ps4);
        rs5.setAdapter(ps5);
        rs6.setAdapter(ps6);*/

        //endregion


        return view;
    }
}