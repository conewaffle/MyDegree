package com.example.mydegree.ProgramDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydegree.R;


public class ProgramCoursesFragment extends Fragment {

    private View view;
    private RecyclerView r1, r2, r3, r4, r5,r6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_program_courses, container, false);
        return view;




    }

}
