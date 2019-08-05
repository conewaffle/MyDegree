package com.example.mydegree.Profile;

import java.util.ArrayList;

public class Badges {

    private String badgeName, badgeDesc;

    public Badges() {}

    public Badges(String badgeName, String badgeDesc) {
        this.badgeName = badgeName;
        this.badgeDesc = badgeDesc;
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

        badge.add(new Badges("Online", "You've saved a plan to the cloud."));
        badge.add(new Badges("Badge 2", "Badge description 2"));
        badge.add(new Badges("Badge 3", "Badge description 3"));
        badge.add(new Badges("Badge 4", "Badge description 4"));
        badge.add(new Badges("Badge 5", "Badge description 5"));
        badge.add(new Badges("Badge 6", "Badge description 6"));

        return badge;
    }

}
