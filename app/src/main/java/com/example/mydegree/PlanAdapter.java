package com.example.mydegree;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydegree.ProgramDetails.CourseAdapter;
import com.example.mydegree.Room.Plan;
import com.example.mydegree.Room.StreamCourse;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private ArrayList<Plan> mDataset;

    public PlanAdapter(ArrayList<com.example.mydegree.Room.Plan> myDataset) {mDataset = myDataset;}

    public class PlanViewHolder extends RecyclerView.ViewHolder {

        private TextView courseCode;
        private CardView courseCard;

        public PlanViewHolder(View itemView){
            super(itemView);
            courseCard = itemView.findViewById(R.id.courseCard);
            courseCode = itemView.findViewById(R.id.courseCode);
        }
    }

    @Override
    public PlanAdapter.PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View planView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
        PlanAdapter.PlanViewHolder vh = new PlanAdapter.PlanViewHolder(planView);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlanAdapter.PlanViewHolder holder, int position){
        holder.courseCode.setText(mDataset.get(position).getCourseCode());

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    public void setPlan(ArrayList<Plan> plans){
        mDataset.clear();
        mDataset.addAll(plans);
        this.notifyDataSetChanged();
    }


}
