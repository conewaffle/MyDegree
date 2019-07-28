package com.example.mydegree.Plan;

import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydegree.CourseOverview.CourseOverview;
import com.example.mydegree.CourseOverview.PrereqAdapter;
import com.example.mydegree.R;
import com.example.mydegree.Room.Plan;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private ArrayList<Plan> mDataset;
    private int position;

    public PlanAdapter(ArrayList<com.example.mydegree.Room.Plan> myDataset) {mDataset = myDataset;}

    public class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener{

        private TextView courseCode;
        private CardView courseCard;

        public PlanViewHolder(View itemView){
            super(itemView);
            courseCard = itemView.findViewById(R.id.courseCard);
            courseCode = itemView.findViewById(R.id.courseCode);
            courseCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String myCourse = mDataset.get(position).getCourseCode();

            Intent courseIntent = new Intent(v.getContext(), CourseOverview.class);
            courseIntent.putExtra(PrereqAdapter.PREREQ_PARCEL,myCourse);
            v.getContext().startActivity(courseIntent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = ((Activity) v.getContext()).getMenuInflater();
            inflater.inflate(R.menu.saved_items_longclick, menu);
        }

        @Override
        public boolean onLongClick(View v) {
            setPosition(getAdapterPosition());
            return false;
        }
    }


    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
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
