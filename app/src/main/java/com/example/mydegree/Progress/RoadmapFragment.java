package com.example.mydegree.Progress;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydegree.R;

public class RoadmapFragment extends Fragment {

    private View view;
    private ProgressDialog progDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_roadmap, container, false);

        progDialog = new ProgressDialog(getActivity());

        return view;
    }
}