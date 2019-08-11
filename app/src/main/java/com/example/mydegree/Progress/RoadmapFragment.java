package com.example.mydegree.Progress;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mydegree.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class RoadmapFragment extends Fragment implements Program.ProgramUpdateListener, Program.CourseUpdateListener, Program.RoadmapUpdateListener {

    private View view;
    private ProgressDialog progDialog;
    private ProgressBar roadmapBar;
    private int localPercent;
    private float localFrom, localTo, localNow, localMax;
    private FloatingActionButton fab;
    private TextView progressText, progressPercent;
    private ImageView yellow25, red25, yellow50, red50, yellow75, red75, yellow100, red100;
    private ConstraintLayout constraintLayout;
    private CardView card1, card2, card3, card4, card5, card6, card7, person;
    private LinearLayout floor1, floor2, floor3, floor4, floor5, floor6, floor7;
    private int colorFrom, colorTo;
    private ImageView plane;
    private KonfettiView viewKonfetti;

    private CardView cardRope, shaft, topSpace, topLights, shaftLights;

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

        plane = view.findViewById(R.id.plane);
        plane.setVisibility(View.INVISIBLE);
        topSpace = view.findViewById(R.id.topSpace);
        cardRope = view.findViewById(R.id.cardRope);
        topLights = view.findViewById(R.id.topLights);
        topLights.bringToFront();
        //sendViewToBack(topSpace);

        shaft = view.findViewById(R.id.shaft);
        sendViewToBack(shaft);
        shaftLights = view.findViewById(R.id.shaftLights);
        //shaftLights.bringToFront();
        person = view.findViewById(R.id.person);
        person.bringToFront();


        constraintLayout = view.findViewById(R.id.roadmapConstraint);
        viewKonfetti = view.findViewById(R.id.viewKonfetti);
        viewKonfetti.bringToFront();

        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);
        card6 = view.findViewById(R.id.card6);
        card7 = view.findViewById(R.id.card7);
        floor1 = view.findViewById(R.id.floor1);
        floor2 = view.findViewById(R.id.floor2);
        floor3 = view.findViewById(R.id.floor3);
        floor4 = view.findViewById(R.id.floor4);
        floor5 = view.findViewById(R.id.floor5);
        floor6 = view.findViewById(R.id.floor6);
        floor7 = view.findViewById(R.id.floor7);


        colorFrom = getResources().getColor(R.color.common_google_signin_btn_text_light_pressed, getActivity().getTheme());
        colorTo = Color.TRANSPARENT;

        return view;
    }

    public static void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (parent != null) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }


    @Override
    public void onProgramUpdate(String programCode, String majorName) {

    }

    @Override
    public void onCourseUpdate(String courseCode) {

    }

    @Override
    public void onRoadmapUpdate(int pbNow, int pbMax) {
        plane.setVisibility(View.VISIBLE);
        roadmapBar.setMax(pbMax);
        localMax = pbMax;
        localNow = pbNow;
        localTo = pbNow;
        //roadmapBar.setProgress(pbNow);

        double percent = (double) pbNow*100/(double)pbMax;
        int percentRounded = (int) Math.round(percent);
        localPercent = percentRounded;/*
        progressPercent.setText(percentRounded + "%");*/
        progressText.setText(pbNow + " / " + pbMax + " UOC Completed (" + localPercent + "%)");
        changeIcons();
    }

    //change is now done IN ANIMATION  in the method below this
    private void changeIcons(){
        if(localPercent<17){
            floor1.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor2.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor3.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor4.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor5.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor6.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor7.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
        } else if(localPercent<33){
            floor1.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor2.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor3.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor4.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor5.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor6.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor7.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
        } else if(localPercent<50){
            floor1.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor2.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor3.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor4.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor5.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor6.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor7.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
        } else if(localPercent<67){
            floor1.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor2.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor3.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor4.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor5.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor6.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor7.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
        } else if(localPercent<83){
            floor1.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor2.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor3.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor4.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor5.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor6.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
            floor7.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
        } else if (localPercent<100){
            floor1.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor2.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor3.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor4.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor5.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor6.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            floor7.setBackgroundTintList(ColorStateList.valueOf(colorFrom));
        } else {
            if(!(localPercent>=100)){
                floor1.setBackgroundTintList(ColorStateList.valueOf(colorTo));
                floor2.setBackgroundTintList(ColorStateList.valueOf(colorTo));
                floor3.setBackgroundTintList(ColorStateList.valueOf(colorTo));
                floor4.setBackgroundTintList(ColorStateList.valueOf(colorTo));
                floor5.setBackgroundTintList(ColorStateList.valueOf(colorTo));
                floor6.setBackgroundTintList(ColorStateList.valueOf(colorTo));
                floor7.setBackgroundTintList(ColorStateList.valueOf(colorTo));
            }
        }
    }



    //trying to implement a moving animation
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {

            fab.hide();

            //animating elevator position
            ConstraintSet applyConstraint = new ConstraintSet();
            applyConstraint.clone(constraintLayout);
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(1000);
            TransitionManager.beginDelayedTransition(constraintLayout, autoTransition);
            float vBias = (float) ((double) 1 - (double)localPercent / (double) 100);
            if(vBias<0){
                vBias=0;
            }
            applyConstraint.setVerticalBias(R.id.person, vBias);
            applyConstraint.applyTo(constraintLayout);

            //animating progressbar position
            ProgressBarAnimation anim = new ProgressBarAnimation(roadmapBar, localFrom, localTo);
            anim.setDuration(800);
            roadmapBar.startAnimation(anim);

            if(localFrom!=localNow){
                //animating light switch for each level
                ValueAnimator tintAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                tintAnimation.setDuration(2000);
                tintAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        double percentFrom = (double) localFrom*100/(double)localMax;
                        int fromRounded = (int) Math.round(percentFrom);
                        double percentTo = (double) localTo*100/(double)localMax;
                        int toRounded = (int) Math.round(percentTo);

                    /*floor1.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                    if(toRounded>=17){floor2.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue())); }
                    if(toRounded>=33){floor3.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue())); }
                    if(toRounded>=50){floor4.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));}
                    if(toRounded>=67){floor5.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));}
                    if(toRounded>=83){floor6.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));}
                    if(toRounded>=100){floor7.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));}
*/
                        if(toRounded<17){
                            if(!(localFrom<17)){
                                floor1.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                            }
                        } else if (toRounded<33){
                            if(!(localFrom<33&&localFrom>=17)){
                                floor1.setBackgroundTintList(null);
                                floor2.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                            }
                        } else if (toRounded<50){
                            if(!(localFrom<50&&localFrom>=33)){
                                floor1.setBackgroundTintList(null);
                                floor2.setBackgroundTintList(null);
                                floor3.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                            }
                        } else if (toRounded<67){
                            if(!(localFrom<67&&localFrom>=50)){
                                floor1.setBackgroundTintList(null);
                                floor2.setBackgroundTintList(null);
                                floor3.setBackgroundTintList(null);
                                floor4.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                            }
                        } else if (toRounded<83){
                            if(!(localFrom<83&&localFrom>=67)){
                                floor1.setBackgroundTintList(null);
                                floor2.setBackgroundTintList(null);
                                floor3.setBackgroundTintList(null);
                                floor4.setBackgroundTintList(null);
                                floor5.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                            }
                        } else if (toRounded<100) {
                            if(!(localFrom<100&&localFrom>=83)){
                                floor1.setBackgroundTintList(null);
                                floor2.setBackgroundTintList(null);
                                floor3.setBackgroundTintList(null);
                                floor4.setBackgroundTintList(null);
                                floor5.setBackgroundTintList(null);
                                floor6.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                            }
                        } else {
                                floor1.setBackgroundTintList(null);
                                floor2.setBackgroundTintList(null);
                                floor3.setBackgroundTintList(null);
                                floor4.setBackgroundTintList(null);
                                floor5.setBackgroundTintList(null);
                                floor6.setBackgroundTintList(null);
                                floor7.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue()));
                            viewKonfetti.build()
                                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                                    .setDirection(0.0, 359.0)
                                    .setSpeed(1f, 5f)
                                    .setFadeOutEnabled(true)
                                    .setTimeToLive(2000L)
                                    .addShapes(Shape.RECT, Shape.CIRCLE)
                                    .addSizes(new Size(12, 5))
                                    .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                                    .streamFor(50, 1000L);
                        }

                    }
                });
                tintAnimation.start();
            }


            //MUST BE LAST
            localFrom = (float) localNow;
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