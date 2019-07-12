package com.example.mydegree.CourseOverview;

import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.Prereq;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PrereqAdapter extends RecyclerView.Adapter<PrereqAdapter.PrereqViewHolder> {

    public static final String PREREQ_PARCEL = "prereqParcel";

    private ArrayList<Prereq> mDataset;

    public PrereqAdapter(ArrayList<Prereq> myDataset){mDataset=myDataset;}

    public class PrereqViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{

        public TextView prereqCode;
        public CardView prereqCard;

        public PrereqViewHolder(View itemView){
            super(itemView);
            prereqCode = itemView.findViewById(R.id.prereqCode);
            prereqCard = itemView.findViewById(R.id.prereqCard);
            prereqCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            Prereq myPrereq = mDataset.get(position);
            String prereqCode = myPrereq.getPrereq();
            Intent viewIntent = new Intent(v.getContext(), CourseOverview.class);
            viewIntent.putExtra(PREREQ_PARCEL,prereqCode);
            v.getContext().startActivity(viewIntent);
        }
    }

    @Override
    public PrereqAdapter.PrereqViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prereq_item, parent, false);
        PrereqAdapter.PrereqViewHolder vh = new PrereqAdapter.PrereqViewHolder(myView);
        return vh;
    }

    @Override
    public void onBindViewHolder(PrereqAdapter.PrereqViewHolder holder, int position){
        holder.prereqCode.setText(mDataset.get(position).getPrereq());
    }

    @Override
    public int getItemCount(){ return mDataset.size();
    }

}
