package com.example.mydegree.Saved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydegree.Bookmark;
import com.example.mydegree.R;

import java.util.List;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.ViewHolder> {

    private List<Bookmark> bookmarkList;
    private Context context;

    public SavedItemAdapter(List<Bookmark> bookmarkList, Context context) {
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
        Bookmark bookmark = bookmarkList.get(position);
        holder.code.setText(bookmark.getCourseCode());
        holder.name.setText(bookmark.getCourseName());

    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView code, name;
        public ViewHolder(View item) {
            super(item);
            code = item.findViewById(R.id.savedItemCode);
            name = item.findViewById(R.id.savedItemName);
        }
    }
}
