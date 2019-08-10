package com.example.mydegree.ProgramDetails;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydegree.CourseOverview.CourseOverview;
import com.example.mydegree.CourseOverview.PrereqAdapter;
import com.example.mydegree.Major;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.Stream;
import com.example.mydegree.Room.StreamCourse;
import com.example.mydegree.Search.SearchAdapter;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.mydegree.Major.MAJOR_PARCEL;
import static com.example.mydegree.Search.SearchAdapter.COURSE_PARCEL;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    private ArrayList<StreamCourse> mDataset;

    public CourseAdapter(ArrayList<StreamCourse> myDataset){mDataset = myDataset;}

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
            StreamCourse myStreamCourse = mDataset.get(position);
            String myCourse = myStreamCourse.getStreamCourse();

            if(myCourse.length()==8) {
                Intent courseIntent = new Intent(view.getContext(), CourseOverview.class);
                courseIntent.putExtra(PrereqAdapter.PREREQ_PARCEL, myCourse);
                view.getContext().startActivity(courseIntent);
            } else if(myCourse.length()==6){
                Intent viewIntent = new Intent(view.getContext(), Major.class);
                viewIntent.putExtra(MAJOR_PARCEL, myCourse);
                view.getContext().startActivity(viewIntent);
            }
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
        holder.code.setText(mDataset.get(position).getStreamCourse());
        if (mDataset.get(position).isCore()) {
            holder.name.setText("Core");
        } else {
            holder.name.setText("Option " + (position+1));
        }

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    public void setCourses(ArrayList<StreamCourse> streamCourses){
        mDataset.clear();
        mDataset.addAll(streamCourses);
        this.notifyDataSetChanged();
    }

}
