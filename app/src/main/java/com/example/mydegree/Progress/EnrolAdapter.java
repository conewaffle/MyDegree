package com.example.mydegree.Progress;

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
import com.example.mydegree.Room.EnrolmentItem;
import com.example.mydegree.Room.Plan;

import java.util.ArrayList;

public class EnrolAdapter extends RecyclerView.Adapter<EnrolAdapter.EnrolViewHolder> {

    private ArrayList<EnrolmentItem> mDataset;
    private int position;

    public EnrolAdapter(ArrayList<EnrolmentItem> myDataset) {mDataset = myDataset;}

    public class EnrolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener{

        private TextView courseCode;
        private CardView courseCard;

        public EnrolViewHolder(View itemView){
            super(itemView);
            courseCard = itemView.findViewById(R.id.courseCard);
            courseCode = itemView.findViewById(R.id.courseCode);
            courseCard.setOnClickListener(this);
            courseCard.setOnLongClickListener(this);
            courseCard.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String myCourseCode = mDataset.get(position).getCourseCode();

            Intent courseIntent = new Intent(v.getContext(), CourseOverview.class);
            courseIntent.putExtra(PrereqAdapter.PREREQ_PARCEL,myCourseCode);
            v.getContext().startActivity(courseIntent);
        }


        //change this to inflate the correct
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = ((Activity) v.getContext()).getMenuInflater();

            //if we want to have a swap option use R.menu.planitem_menu. this one just has remove
            inflater.inflate(R.menu.saved_items_longclick, menu);

        /*    switch(((RecyclerView) v.getParent().getParent()).getId()){
                case R.id.streamRV1:
                case R.id.streamRV2:
                case R.id.streamRV3:
                case R.id.streamRV4:
                case R.id.streamRV5:
                case R.id.streamRV6:
                    inflater.inflate(R.menu.saved_items_longclick, menu);
                    break;
                default:
                    inflater.inflate(R.menu.planitem_menu, menu);
            }*/

            //some complex ass way of getting the correct recycler
            //((com.example.mydegree.Progress.Program) v.getContext()).setRecyclerClicked((RecyclerView) v.getParent().getParent());
          /*  ((com.example.mydegree.Progress.Program) v.getContext()).setRecyclerLongClicked(((RecyclerView) v.getParent().getParent()).getId());*/
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
    public EnrolAdapter.EnrolViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View enrolView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
        EnrolAdapter.EnrolViewHolder vh = new EnrolAdapter.EnrolViewHolder(enrolView);
        return vh;
    }

    @Override
    public void onBindViewHolder(EnrolAdapter.EnrolViewHolder holder, int position){
        holder.courseCode.setText(mDataset.get(position).getCourseCode());

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    public void setPlan(ArrayList<EnrolmentItem> enrolmentItems){
        mDataset.clear();
        mDataset.addAll(enrolmentItems);
        this.notifyDataSetChanged();
    }


}
