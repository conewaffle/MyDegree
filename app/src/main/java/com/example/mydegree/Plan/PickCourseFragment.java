package com.example.mydegree.Plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mydegree.R;

public class PickCourseFragment extends DialogFragment {

    private Spinner courseSpinner;
    private Button confirm;
    public static final String FRAG_PROGRAM = "fragProgram";
    public static final String FRAG_T1 = "fragT1";
    public static final String FRAG_T2 = "fragT2";
    public static final String FRAG_T3 = "fragT3";

    public PickCourseFragment(){

    }

    public static PickCourseFragment newInstance(String program, int t1, int t2, int t3) {
        PickCourseFragment frag = new PickCourseFragment();
        Bundle args = new Bundle();
        args.putString(FRAG_PROGRAM, program);
        args.putInt(FRAG_T1, t1);
        args.putInt(FRAG_T2, t2);
        args.putInt(FRAG_T3, t3);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pick_course, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseSpinner = (Spinner) view.findViewById(R.id.courseSpinner);

        // Fetch arguments from bundle and set title
        String program = getArguments().getString(FRAG_PROGRAM);
        int t1 = getArguments().getInt(FRAG_T1, 0);
        int t2 = getArguments().getInt(FRAG_T2, 0);
        int t3 = getArguments().getInt(FRAG_T3, 0);

        getDialog().setTitle("Pick a Course");


    }


}
