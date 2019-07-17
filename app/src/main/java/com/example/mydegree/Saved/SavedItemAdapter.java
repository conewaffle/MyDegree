package com.example.mydegree.Saved;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydegree.Bookmark;
import com.example.mydegree.CourseOverview.CourseOverview;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;

import java.util.ArrayList;
import java.util.List;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.ViewHolder> {

    public static final String COURSE_PARCEL = "courseParcel";

    private ArrayList<Course> bookmarkList;
    private Context context;

    public SavedItemAdapter(ArrayList<Course> bookmarkList, Context context) {
        this.bookmarkList = bookmarkList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.saved_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.code.setText(bookmarkList.get(position).getCourseCode());
        holder.name.setText(bookmarkList.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView code, name;
        public CardView savedCard;

        public ViewHolder(View item) {
            super(item);
            code = item.findViewById(R.id.savedItemCode);
            name = item.findViewById(R.id.savedItemName);
            savedCard = item.findViewById(R.id.savedCard);

            savedCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();


            Course myCourse = bookmarkList.get(position);
            Intent intent = new Intent(context, CourseOverview.class);
            intent.putExtra(COURSE_PARCEL, myCourse);
            context.startActivity(intent);
        }
    }
}