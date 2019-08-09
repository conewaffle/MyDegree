package com.example.mydegree.Progress;

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

public class EnrolSearchAdapter extends RecyclerView.Adapter<EnrolSearchAdapter.EnrolSearchViewHolder>{
    private ArrayList<String> mDataset;
    private int position, year, term;
    private Bundle myBundle;
    private Context context;

    public EnrolSearchAdapter(ArrayList<String> myDataset, Context context){mDataset = myDataset; this.context = context;}

    public class EnrolSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView plansearchtext;
        public CardView plansearchcard;

        public EnrolSearchViewHolder(View itemView){
            super(itemView);
            plansearchtext = itemView.findViewById(R.id.plansearchtext);
            plansearchcard = itemView.findViewById(R.id.plansearchcard);
            plansearchcard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            String progCode = mDataset.get(position).substring(0,8);

/*            PickEnrolItemFragment.PickEnrolItemListener listener = (PickEnrolItemFragment.PickEnrolItemListener) context;
            listener.onFinishPickEnrolItem(progCode);*/

            ((Program) context).onCourseUpdate(progCode);
            //TO BE DISMISSED IN THE CHECKERS.

        }
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    @Override
    public EnrolSearchAdapter.EnrolSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_search_item, parent, false);
        EnrolSearchViewHolder vh = new EnrolSearchViewHolder(searchView);
        return vh;
    }

    @Override
    public void onBindViewHolder(EnrolSearchViewHolder holder, int position){
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
