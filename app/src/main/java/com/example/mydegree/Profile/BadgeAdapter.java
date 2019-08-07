package com.example.mydegree.Profile;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mydegree.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {

    private ArrayList<Badges> badgeList;
    private Context context;
    private int position;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String userId;


    public BadgeAdapter(Context context, ArrayList<Badges> badgeList) {
        this.badgeList = badgeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.badge_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();

/*        holder.badgeCard.setAlpha(1);*/
        holder.badgeName.setText(badgeList.get(position).getBadgeName());
        holder.badgeDesc.setText(badgeList.get(position).getBadgeDesc());

        if (user != null) {
            userId = user.getUid();
            databaseReference.child("User").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("achievements").child("Online").exists()) {
                        String online = String.valueOf(badgeList.get(position).getBadgeName());
                        if (online.equals("Online"))
                            holder.cardLL.setAlpha(1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return badgeList.size();
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView badgeName, badgeDesc;
        public CardView badgeCard;
        public LinearLayout cardLL;

        public ViewHolder(View item) {
            super(item);
            badgeCard = item.findViewById(R.id.badgeCard);
            badgeName = item.findViewById(R.id.badgeName);
            badgeDesc = item.findViewById(R.id.badgeDesc);
            cardLL = item.findViewById(R.id.cardLL);
        }
    }

}