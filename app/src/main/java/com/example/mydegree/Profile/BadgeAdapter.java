package com.example.mydegree.Profile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private Dialog dialog;
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
        final ViewHolder holder = new ViewHolder(item);


        holder.badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_badge);
                ImageView badge = dialog.findViewById(R.id.badge);
                TextView badgeName = dialog.findViewById(R.id.badgeName);
                TextView badgeDesc = dialog.findViewById(R.id.badgeDesc);
                final ImageButton close = dialog.findViewById(R.id.close);

                badge.setImageResource(badgeList.get(holder.getAdapterPosition()).getBadge());
                badgeName.setText(badgeList.get(holder.getAdapterPosition()).getBadgeName());
                badgeDesc.setText(badgeList.get(holder.getAdapterPosition()).getBadgeDesc());

                dialog.show();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();
/*
        holder.badgeName.setText(badgeList.get(position).getBadgeName());
        holder.badgeDesc.setText(badgeList.get(position).getBadgeDesc());*/
        holder.badge.setImageResource(badgeList.get(position).getBadge());

        if (user != null) {
            if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Arc")) {
                holder.cardLL.setAlpha(1);
            }
            userId = user.getUid();
            databaseReference.child("User").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("achievements").child("UniWide").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("UniWide")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("Scholar").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Scholar")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("Business School").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Business School")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("Law Library").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Law Library")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("Main Library").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Main Library")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("Bookshop").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Bookshop")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("Roundhouse").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Roundhouse")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("The Nucleus").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("The Nucleus")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("Basser Steps").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("Basser Steps")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("IT Service Desk").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("IT Service Desk")) {
                            holder.cardLL.setAlpha(1);
                        }
                    }

                    if (dataSnapshot.child("achievements").child("University Terraces").exists()) {
                        if (String.valueOf(badgeList.get(position).getBadgeName()).equals("University Terraces")) {
                            holder.cardLL.setAlpha(1);
                        }
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
        public ImageView badge;
        public CardView badgeCard;
        public LinearLayout cardLL;

        public ViewHolder(View item) {
            super(item);
            badge = item.findViewById(R.id.badge);
            badgeCard = item.findViewById(R.id.badgeCard);
            cardLL = item.findViewById(R.id.cardLL);
        }
    }

}