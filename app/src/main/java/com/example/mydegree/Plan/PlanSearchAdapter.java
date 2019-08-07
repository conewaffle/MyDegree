package com.example.mydegree.Plan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydegree.R;

import java.util.ArrayList;

import static com.example.mydegree.Plan.PickCourseFragment.FRAG_TERM;
import static com.example.mydegree.Plan.PickCourseFragment.FRAG_YEAR;
import static com.example.mydegree.Plan.PickCourseFragment.RESULT_COURSE;

public class PlanSearchAdapter extends RecyclerView.Adapter<PlanSearchAdapter.PlanSearchViewHolder>{

    private ArrayList<String> mDataset;
    private int position, year, term;
    private Bundle myBundle;
    private Context context;

    public PlanSearchAdapter(ArrayList<String> myDataset, int year, int term, Context context){mDataset = myDataset; this.year = year; this.term = term; this.context = context;}

    public class PlanSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView plansearchtext;
        public CardView plansearchcard;


        public PlanSearchViewHolder(View itemView){
            super(itemView);
            plansearchtext = itemView.findViewById(R.id.plansearchtext);
            plansearchcard = itemView.findViewById(R.id.plansearchcard);

            plansearchcard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            String progCode = mDataset.get(position).substring(0,8);

            myBundle = new Bundle();
            myBundle.putString(RESULT_COURSE, progCode);
            myBundle.putInt(FRAG_YEAR, year);
            myBundle.putInt(FRAG_TERM, term);
            PickCourseFragment.PickCoursesListener listener = (PickCourseFragment.PickCoursesListener) context;
            listener.onFinishPick(myBundle);
        }
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    @Override
    public PlanSearchAdapter.PlanSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_search_item, parent, false);
        PlanSearchViewHolder vh = new PlanSearchViewHolder(searchView);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlanSearchViewHolder holder, int position){
        holder.plansearchtext.setText(mDataset.get(position));

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    public void setCourses(ArrayList<String> courses){
        mDataset.clear();
        mDataset.addAll(courses);
        this.notifyDataSetChanged();
    }
}
