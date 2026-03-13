package com.project.ImpactTracking.Model;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * MemberImpact class is defined for representing the points and reward data for community members.
 */

public class MemberImpact {


    private String memberId;
    private String memberName;
    private int totalPoints;
    private int totalListings;
    private int completedListings;
    private int incompletedListings;
    private String pointsBadge;
    private String listingsBadge;
    private List<String> allBadges;


    /**
     *
     * Default Constructor for rewards
     */

    public MemberImpact() {
        this.totalPoints = 0;
        this.totalListings = 0;
        this.completedListings = 0;
        this.incompletedListings = 0;
        this.pointsBadge = "No Badge";
        this.listingsBadge = "No Badge";
        this.allBadges = new ArrayList<>();
    }

    /**
     *
     *  Parameterized constructor for particular member.
     * @param memberId a unique Id that is assigned to a member.
     * @param memberName their name.
     */

    public MemberImpact(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.totalPoints = 0;
        this.totalListings = 0;
        this.completedListings = 0;
        this.incompletedListings = 0;
        this.pointsBadge = "No Badge";
        this.listingsBadge = "No Badge";
        this.allBadges = new ArrayList<>();
    }

    /**
     *
     * getters and setters for all the fields.
     */
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getTotalListings() {
        return totalListings;
    }

    public void setTotalListings(int totalListings) {
        this.totalListings = totalListings;
    }

    public int getCompletedListings() {
        return completedListings;
    }

    public void setCompletedListings(int completedListings) {
        this.completedListings = completedListings;
    }

    public int getIncompletedListings() {
        return incompletedListings;
    }

    public void setIncompletedListings(int incompletedListings) {
        this.incompletedListings = incompletedListings;
    }

    public String getPointsBadge() {
        return pointsBadge;
    }

    public void setPointsBadge(String pointsBadge) {
        this.pointsBadge = pointsBadge;
    }

    public String getListingsBadge() {
        return listingsBadge;
    }

    public void setListingsBadge(String listingsBadge) {
        this.listingsBadge = listingsBadge;
    }

    public List<String> getAllBadges() {
        return allBadges;
    }

    public void setAllBadges(List<String> allBadges) {
        this.allBadges = allBadges;
    }

    @Override
    public String toString() {
        return "MemberImpact{" + "memberId = '" + memberId + "', memberName = '" + memberName + "', totalPoints = " + totalPoints + ", pointsBadge = '" +  pointsBadge + "', listingsBadge = '" + listingsBadge + "'}";
    }

}
