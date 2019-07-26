package com.example.mydegree.Search;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.CourseOverview.CourseOverview;
import com.example.mydegree.ProgramDetails.ProgramDetail;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    public static final String COURSE_PARCEL = "courseParcel";
    public static final String PROG_CODE = "progCode";

    private ArrayList<Course> mDataset;

    public SearchAdapter(ArrayList<Course> myDataset){mDataset = myDataset;}

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView code, name;
        public CardView searchCard;

        public SearchViewHolder(View itemView){
            super(itemView);
            code = itemView.findViewById(R.id.searchItemCode);
            name = itemView.findViewById(R.id.searchItemName);
            searchCard = itemView.findViewById(R.id.searchCard);

            searchCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            Course myCourse = mDataset.get(position);
            if(myCourse.getCourseCode().length()==4){
                Intent viewIntent = new Intent(view.getContext(), ProgramDetail.class);
                viewIntent.putExtra(PROG_CODE, myCourse.getCourseCode());
                view.getContext().startActivity(viewIntent);
            } else if (myCourse.getCourseCode().length()==8){
                Intent viewIntent = new Intent(view.getContext(), CourseOverview.class);
                viewIntent.putExtra(COURSE_PARCEL, myCourse);
                view.getContext().startActivity(viewIntent);
            } else if (myCourse.getCourseCode().length()==6){

            }

        }
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        SearchViewHolder vh = new SearchViewHolder(searchView);
        return vh;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position){
        if(mDataset.get(position).getCourseCode().length()==4){
            holder.code.setText(mDataset.get(position).getCourseCode() + " - " + mDataset.get(position).getCourseName());
            holder.name.setText("Program");
            holder.code.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            holder.code.setText(mDataset.get(position).getCourseCode());
            holder.name.setText(mDataset.get(position).getCourseName());
            holder.code.setTypeface(Typeface.DEFAULT);

        }

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }


}
