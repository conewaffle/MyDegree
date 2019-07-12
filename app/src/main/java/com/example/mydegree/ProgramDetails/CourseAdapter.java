package com.example.mydegree.ProgramDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydegree.CourseOverview;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Search.SearchAdapter;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    private ArrayList<Course> mDataset;

    public CourseAdapter(ArrayList<Course> myDataset){mDataset = myDataset;}

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView code, name;
        public CardView searchCard;

        public CourseViewHolder(View itemView){
            super(itemView);
            code = itemView.findViewById(R.id.courseListCode);
            name = itemView.findViewById(R.id.courseListName);
            searchCard = itemView.findViewById(R.id.progCourseCard);

            searchCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            Course myCourse = mDataset.get(position);

            Intent courseIntent = new Intent(view.getContext(),CourseOverview.class);
            courseIntent.putExtra(SearchAdapter.COURSE_PARCEL,myCourse);
            view.getContext().startActivity(courseIntent);
        }
    }


    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prog_course_item, parent, false);
        CourseAdapter.CourseViewHolder vh = new CourseAdapter.CourseViewHolder(searchView);
        return vh;
    }

    @Override
    public void onBindViewHolder(CourseAdapter.CourseViewHolder holder, int position){
        holder.code.setText(mDataset.get(position).getCourseCode());
        holder.name.setText(mDataset.get(position).getCourseName());
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}
