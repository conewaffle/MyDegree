package com.example.mydegree.Progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mydegree.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RoadmapFragment extends Fragment implements Program.ProgramUpdateListener, Program.CourseUpdateListener, Program.RoadmapUpdateListener {

    private View view;
    private ProgressDialog progDialog;
    private ProgressBar roadmapBar;
    private int localPercent;
    private float localFrom, localTo, localNow;
    private FloatingActionButton fab;
    private TextView progressText, progressPercent;
    private ImageView yellow25, red25, yellow50, red50, yellow75, red75, yellow100, red100, person;
    private ConstraintLayout constraintLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_roadmap, container, false);

        progDialog = new ProgressDialog(getActivity());
        roadmapBar = view.findViewById(R.id.pb);
        progressText = view.findViewById(R.id.progressText);
        progressPercent = view.findViewById(R.id.progressPercent);
        fab = getActivity().findViewById(R.id.fab);

        person = view.findViewById(R.id.person);

        constraintLayout = view.findViewById(R.id.roadmapConstraint);

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
        localNow = pbNow;
        localTo = pbNow;
        //roadmapBar.setProgress(pbNow);
        progressText.setText(pbNow + " / " + pbMax + " UOC Completed");

        double percent = (double) pbNow*100/(double)pbMax;
        int percentRounded = (int) Math.round(percent);
        localPercent = percentRounded;
        progressPercent.setText(percentRounded + "%");
        changeIcons();
    }

    private void changeIcons(){
        if(localPercent<17){

        } else if(localPercent<33){

        } else if(localPercent<50){

        } else if(localPercent<67){

        } else if(localPercent<83){

        } else if (localPercent<100){

        } else {

        }
    }



    //trying to implement a moving animation
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            fab.hide();
            ConstraintSet applyConstraint = new ConstraintSet();
            applyConstraint.clone(constraintLayout);
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(1000);
            TransitionManager.beginDelayedTransition(constraintLayout, autoTransition);
            float vBias = (float) ((double) 1 - (double)localPercent / (double) 100);
            applyConstraint.setVerticalBias(R.id.person, vBias);
            applyConstraint.applyTo(constraintLayout);

            ProgressBarAnimation anim = new ProgressBarAnimation(roadmapBar, localFrom, localTo);
            anim.setDuration(800);
            roadmapBar.startAnimation(anim);
            localFrom = (float) localNow;

        } else {

        }
    }

    public class ProgressBarAnimation extends Animation{
        private ProgressBar progressBar;
        private float from;
        private float  to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
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