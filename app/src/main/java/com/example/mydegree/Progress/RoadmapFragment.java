package com.example.mydegree.Progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mydegree.R;

public class RoadmapFragment extends Fragment implements Program.ProgramUpdateListener, Program.CourseUpdateListener, Program.RoadmapUpdateListener {

    private View view;
    private ProgressDialog progDialog;
    private ProgressBar roadmapBar;
    private int localPbNow, localPbMax;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_roadmap, container, false);

        progDialog = new ProgressDialog(getActivity());
        roadmapBar = view.findViewById(R.id.pb);


        return view;
    }

    @Override
    public void onProgramUpdate(String programCode, String majorName) {

    }

    @Override
    public void onCourseUpdate(String courseCode) {

    }

    @Override
    public void onRoadmapUpdate(int pbNow, int pbMax) {
        roadmapBar.setMax(pbMax);
        roadmapBar.setProgress(pbNow);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((Program) context).registerRoadmapUpdateListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((Program) getActivity()).unregisterRoadmapUpdateListener(this);
    }
}