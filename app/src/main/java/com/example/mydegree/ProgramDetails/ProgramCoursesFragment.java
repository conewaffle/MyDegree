package com.example.mydegree.ProgramDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydegree.R;
import com.example.mydegree.Room.Course;

import java.util.ArrayList;


public class ProgramCoursesFragment extends Fragment {

    private View view;
    private RecyclerView r1, r2, r3, r4, r5,r6, r7, r8, r9, r10;
    private CourseAdapter c1, c2, c3, c4, c5, c6, c7,c8,c9,c10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_program_courses, container, false);

        r1 = view.findViewById(R.id.recycler1);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(getActivity()));
        r2 = view.findViewById(R.id.recycler2);
        r2.setHasFixedSize(true);
        r2.setLayoutManager(new LinearLayoutManager(getActivity()));
        r3 = view.findViewById(R.id.recycler3);
        r3.setHasFixedSize(true);
        r3.setLayoutManager(new LinearLayoutManager(getActivity()));
        r4 = view.findViewById(R.id.recycler4);
        r4.setHasFixedSize(true);
        r4.setLayoutManager(new LinearLayoutManager(getActivity()));
        r5 = view.findViewById(R.id.recycler5);
        r5.setHasFixedSize(true);
        r5.setLayoutManager(new LinearLayoutManager(getActivity()));
        r6 = view.findViewById(R.id.recycler6);
        r6.setHasFixedSize(true);
        r6.setLayoutManager(new LinearLayoutManager(getActivity()));
        r7 = view.findViewById(R.id.recycler7);
        r7.setHasFixedSize(true);
        r7.setLayoutManager(new LinearLayoutManager(getActivity()));
        r8 = view.findViewById(R.id.recycler8);
        r8.setHasFixedSize(true);
        r8.setLayoutManager(new LinearLayoutManager(getActivity()));
        r9 = view.findViewById(R.id.recycler9);
        r9.setHasFixedSize(true);
        r9.setLayoutManager(new LinearLayoutManager(getActivity()));
        r10 = view.findViewById(R.id.recycler10);
        r10.setHasFixedSize(true);
        r10.setLayoutManager(new LinearLayoutManager(getActivity()));

        c1 = new CourseAdapter(new ArrayList<Course>());
        c2 = new CourseAdapter(new ArrayList<Course>());
        c3 = new CourseAdapter(new ArrayList<Course>());
        c4 = new CourseAdapter(new ArrayList<Course>());
        c5 = new CourseAdapter(new ArrayList<Course>());
        c6 = new CourseAdapter(new ArrayList<Course>());
        c7 = new CourseAdapter(new ArrayList<Course>());
        c8 = new CourseAdapter(new ArrayList<Course>());
        c9 = new CourseAdapter(new ArrayList<Course>());
        c10 = new CourseAdapter(new ArrayList<Course>());

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



        return view;
    }

}
