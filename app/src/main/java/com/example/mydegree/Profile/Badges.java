package com.example.mydegree.Profile;

import android.widget.ImageView;

import com.example.mydegree.R;

import java.util.ArrayList;

public class Badges {

    private int badge;
    private String badgeName, badgeDesc;

    public Badges() {}

    public Badges(int badge, String badgeName, String badgeDesc) {
        this.badge = badge;
        this.badgeName = badgeName;
        this.badgeDesc = badgeDesc;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getBadgeDesc() {
        return badgeDesc;
    }

    public void setBadgeDesc(String badgeDesc) {
        this.badgeDesc = badgeDesc;
    }

    public static ArrayList<Badges> getBadges() {
        ArrayList<Badges> badge = new ArrayList<>();

        badge.add(new Badges(R.drawable.ic_baseline_account_circle_24px, "Arc", "You have created an account"));
        badge.add(new Badges(R.drawable.ic_round_sync_24px, "Post Office", "You have saved a plan to the cloud"));
        badge.add(new Badges(R.drawable.ic_round_school_24px,"Scholar", "You have created a Co-op plan"));
        badge.add(new Badges(R.drawable.ic_round_business_24px,"Business School", "You have created an INFS or Commerce/INFS plan"));
        badge.add(new Badges(R.drawable.ic_baseline_bookmark_border_24px, "Library", "You have viewed 30 courses, majors or programs"));
        badge.add(new Badges(R.drawable.ic_baseline_bookmark_24px,"Bookshop", "You have bookmarked 10 items"));
        badge.add(new Badges(R.drawable.ic_round_business_24px,"Roundhouse", "You have deleted a plan"));
        badge.add(new Badges(R.drawable.ic_baseline_add_circle_outline_24px,"The Nucleus", "You have created 10 plans"));
        badge.add(new Badges(R.drawable.ic_round_playlist_add_check_24px,"Basser Steps", "You have completed a plan"));
        badge.add(new Badges(R.drawable.ic_round_warning_24px,"IT Service Desk", "You have broken a requirement rule"));
        badge.add(new Badges(R.drawable.ic_round_home_24px,"University Terraces", "You have spent 10 hours using TriAngles"));

        return badge;
    }

}
