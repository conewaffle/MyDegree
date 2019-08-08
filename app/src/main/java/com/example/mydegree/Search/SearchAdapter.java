package com.example.mydegree.Search;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.CourseOverview.CourseOverview;
import com.example.mydegree.Major;
import com.example.mydegree.ProgramDetails.ProgramDetail;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    public static final String COURSE_PARCEL = "courseParcel";
    public static final String PROG_CODE = "progCode";
    public static final String MAJOR_CODE = "majorCode";

    private String uid;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static int clickCount = 0;

    private ArrayList<Course> mDataset;

    public SearchAdapter(ArrayList<Course> myDataset){mDataset = myDataset;}

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView code, name, searchType;
        public CardView searchCard;

        public SearchViewHolder(View itemView){
            super(itemView);
            code = itemView.findViewById(R.id.searchItemCode);
            name = itemView.findViewById(R.id.searchItemName);
            searchType = itemView.findViewById(R.id.searchType);
            searchCard = itemView.findViewById(R.id.searchCard);

            searchCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            clickCount ++;
            auth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

            final FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                uid = user.getUid();

                if (clickCount >= 30) {
                    library();
                }
            }

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
                Intent viewIntent = new Intent(view.getContext(), Major.class);
                viewIntent.putExtra(COURSE_PARCEL, myCourse);
                view.getContext().startActivity(viewIntent);
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
        holder.code.setText(mDataset.get(position).getCourseCode());
        holder.name.setText(mDataset.get(position).getCourseName());
        if(mDataset.get(position).getCourseCode().length()==4){
            holder.searchType.setText("Program");
        } else if (mDataset.get(position).getCourseCode().length()==6){
            holder.searchType.setText("Major");
        } else if (mDataset.get(position).getCourseCode().length()==8){
            holder.searchType.setText("Course");
        }

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    private void library() {
        databaseReference.child("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sync = "Basser Steps";
                dataSnapshot.child("achievements").getRef().child(sync).setValue(sync);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
