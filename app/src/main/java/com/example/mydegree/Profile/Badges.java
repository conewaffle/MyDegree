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

        badge.add(new Badges(R.drawable.badge_arc, "Arc", "You have created an account"));
        badge.add(new Badges(R.drawable.badge_uniwide, "UniWide", "You have saved a plan to the cloud"));
        badge.add(new Badges(R.drawable.badge_coop,"Scholar", "You have created a Co-op plan"));
        badge.add(new Badges(R.drawable.badge_asb,"Business School", "You have created an Information System or Commerce/Information Systems plan"));
        badge.add(new Badges(R.drawable.badge_law, "Law Library", "You have viewed 10 courses, majors or programs"));
        badge.add(new Badges(R.drawable.badge_main, "Main Library", "You have viewed 30 courses, majors or programs"));
        badge.add(new Badges(R.drawable.badge_bookshop,"Bookshop", "You have bookmarked 10 items"));
        badge.add(new Badges(R.drawable.badge_roundhouse,"Roundhouse", "You have deleted a plan"));
        badge.add(new Badges(R.drawable.badge_nucleus,"The Nucleus", "You have created 10 plans"));
        badge.add(new Badges(R.drawable.badge_basser,"Basser Steps", "You have completed a plan"));
        badge.add(new Badges(R.drawable.badge_it,"IT Service Desk", "You have broken a requirement rule"));
        badge.add(new Badges(R.drawable.badge_terraces,"University Terraces", "You have spent 10 hours using TriAngles"));

        return badge;
    }

}
