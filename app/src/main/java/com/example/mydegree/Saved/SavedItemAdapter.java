package com.example.mydegree.Saved;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public static final String SAVED_PARCEL = "savedParcel";

    private ArrayList<Course> bookmarkList;
    private Context context;
    private int position;

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

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        public TextView code, name;
        public CardView savedCard;

        public ViewHolder(View item) {
            super(item);
            code = item.findViewById(R.id.savedItemCode);
            name = item.findViewById(R.id.savedItemName);
            savedCard = item.findViewById(R.id.savedCard);

            savedCard.setOnClickListener(this);
            savedCard.setOnCreateContextMenuListener(this);
            savedCard.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Course bookmark = bookmarkList.get(position);
            String bookmarkCode = bookmark.getCourseCode();
            Intent i = new Intent(context, CourseOverview.class);
            i.putExtra(SAVED_PARCEL, bookmarkCode);
            context.startActivity(i);
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


}