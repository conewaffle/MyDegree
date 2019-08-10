package com.example.mydegree.Progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mydegree.R;

public class RoadmapFragment extends Fragment implements Program.ProgramUpdateListener, Program.CourseUpdateListener, Program.RoadmapUpdateListener {

    private View view;
    private ProgressDialog progDialog;
    private ProgressBar roadmapBar;
    private int localPercent;
    private TextView progressText, progressPercent;
    private ImageView yellow25, red25, yellow50, red50, yellow75, red75, yellow100, red100, person;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_roadmap, container, false);

        progDialog = new ProgressDialog(getActivity());
        roadmapBar = view.findViewById(R.id.pb);
        progressText = view.findViewById(R.id.progressText);
        progressPercent = view.findViewById(R.id.progressPercent);

        yellow25 = view.findViewById(R.id.booksyellow);
        red25 = view.findViewById(R.id.booksred);
        yellow50 = view.findViewById(R.id.ayellow);
        red50 = view.findViewById(R.id.ared);
        yellow75 = view.findViewById(R.id.transcriptyellow);
        red75 = view.findViewById(R.id.transcriptred);
        yellow100 = view.findViewById(R.id.hatyellow);
        red100 = view.findViewById(R.id.hatred);
        red25.setAlpha(0.1f);
        red50.setAlpha(0.1f);
        red75.setAlpha(0.1f);
        red100.setAlpha(0.1f);
        person = view.findViewById(R.id.person);

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
        progressText.setText(pbNow + " / " + pbMax + " UOC Completed");

        double percent = (double) pbNow*100/(double)pbMax;
        int percentRounded = (int) Math.round(percent);
        localPercent = percentRounded;
        progressPercent.setText(percentRounded + "%");
        changeIcons();
    }

    private void changeIcons(){
        if(localPercent<25){
            yellow25.setVisibility(View.INVISIBLE);
            yellow50.setVisibility(View.INVISIBLE);
            yellow75.setVisibility(View.INVISIBLE);
            yellow100.setVisibility(View.INVISIBLE);
            red25.setVisibility(View.VISIBLE);
            red50.setVisibility(View.VISIBLE);
            red75.setVisibility(View.VISIBLE);
            red100.setVisibility(View.VISIBLE);
        } else if(localPercent<50){
            yellow25.setVisibility(View.VISIBLE);
            yellow50.setVisibility(View.INVISIBLE);
            yellow75.setVisibility(View.INVISIBLE);
            yellow100.setVisibility(View.INVISIBLE);
            red25.setVisibility(View.INVISIBLE);
            red50.setVisibility(View.VISIBLE);
            red75.setVisibility(View.VISIBLE);
            red100.setVisibility(View.VISIBLE);
        } else if(localPercent<75){
            yellow25.setVisibility(View.VISIBLE);
            yellow50.setVisibility(View.VISIBLE);
            yellow75.setVisibility(View.INVISIBLE);
            yellow100.setVisibility(View.INVISIBLE);
            red25.setVisibility(View.INVISIBLE);
            red50.setVisibility(View.INVISIBLE);
            red75.setVisibility(View.VISIBLE);
            red100.setVisibility(View.VISIBLE);
        } else if(localPercent<100){
            yellow25.setVisibility(View.VISIBLE);
            yellow50.setVisibility(View.VISIBLE);
            yellow75.setVisibility(View.VISIBLE);
            yellow100.setVisibility(View.INVISIBLE);
            red25.setVisibility(View.INVISIBLE);
            red50.setVisibility(View.INVISIBLE);
            red75.setVisibility(View.INVISIBLE);
            red100.setVisibility(View.VISIBLE);
        } else {
            yellow25.setVisibility(View.VISIBLE);
            yellow50.setVisibility(View.VISIBLE);
            yellow75.setVisibility(View.VISIBLE);
            yellow100.setVisibility(View.VISIBLE);
            red25.setVisibility(View.INVISIBLE);
            red50.setVisibility(View.INVISIBLE);
            red75.setVisibility(View.INVISIBLE);
            red100.setVisibility(View.INVISIBLE);
        }

    }

    //trying to implement a moving animation
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            super.onResume();
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    ConstraintLayout.LayoutParams lparams = (ConstraintLayout.LayoutParams) person.getLayoutParams();
                    float vBias = (float) ((double) 1 - (double)localPercent / (double) 100);
                    lparams.verticalBias =  vBias;
                    person.setLayoutParams(lparams);
                }
            };
            a.setDuration(1000); // in ms
            a.setFillAfter(true);
            person.startAnimation(a);
        } else {

        }
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