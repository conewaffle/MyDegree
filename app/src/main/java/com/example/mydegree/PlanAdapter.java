package com.example.mydegree;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mydegree.ProgramDetails.CourseAdapter;
import com.example.mydegree.Room.Plan;
import com.example.mydegree.Room.StreamCourse;

import java.util.ArrayList;

public class PlanAdapter {

    private ArrayList<Plan> mDataset;

    public PlanAdapter(ArrayList<com.example.mydegree.Room.Plan> myDataset) {mDataset = myDataset;}

    public class PlanViewHolder extends RecyclerView.ViewHolder {

        public PlanViewHolder(View itemView){
            super(itemView);

        }
    }

   /* @Override
    public PlanAdapter.PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        replace layout
        View searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prog_course_item, parent, false);
        PlanAdapter.PlanViewHolder vh = new PlanAdapter.PlanViewHolder(searchView);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlanAdapter.PlanViewHolder holder, int position){


    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    public void setCourses(ArrayList<StreamCourse> streamCourses){
        mDataset.clear();
        mDataset.addAll();
        this.notifyDataSetChanged();
    }

*/

}
