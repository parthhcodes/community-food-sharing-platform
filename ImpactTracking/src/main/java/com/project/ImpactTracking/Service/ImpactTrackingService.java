package com.project.ImpactTracking.Service;


import com.project.ImpactTracking.Model.FoodListingDTO;
import com.project.ImpactTracking.Model.MemberImpact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This class is responsible for reading food listings from food listing microservice and
 * calculating the points and assigning the badges to the members.
 *
 */
@Service
public class ImpactTrackingService {


    private final RestTemplate restTemplate;

    private final Map<String, MemberImpact> memberImpactMap = new ConcurrentHashMap<>();

    public ImpactTrackingService() {
        this.restTemplate = new RestTemplate();
    }

    // Constructor for testing
    public ImpactTrackingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${foodlisting.service.url}")
    private String foodListingServiceUrl;

    @Value("${points.per-submission}")
    private int pointsPerSubmission;

    @Value("${points.complete-bonus}")
    private int pointsCompleteBonus;

    @Value("${badge.bronze}")
    private int badgeBronze;

    @Value("${badge.silver}")
    private int badgeSilver;

    @Value("${badge.gold}")
    private int badgeGold;

    @Value("${badge.bronze-saver}")
    private int badgeBronzeSaver;

    @Value("${badge.silver-saver}")
    private int badgeSilverSaver;

    @Value("${badge.gold-saver}")
    private int badgeGoldSaver;

    @Value("${badge.community-hero}")
    private int badgeCommunityHero;

    /**
     *
     * Fetches all food listings from food listing microservice.
     *
     */

    public List<FoodListingDTO> fetchFoodListings() {
        ResponseEntity<List<FoodListingDTO>> response = restTemplate.exchange(
                foodListingServiceUrl,
                HttpMethod.GET ,
                null,
                new ParameterizedTypeReference<List<FoodListingDTO>>() {}
        );
        return response.getBody() != null ? response.getBody() : new ArrayList<>();
    }

    /**
     *
     *
     * Food Listed by any particular member is being fetched using @param memberId.
     *
     *
     */
    public List<FoodListingDTO> fetchFoodListingsByMemberId(String memberId) {
        List<FoodListingDTO> foodListings = fetchFoodListings();
        return foodListings.stream()
                .filter(listing -> memberId.equals(listing.getMemberId()))
                .collect(Collectors.toList());
    }


    /**
     * Calculates and updates the impact for a specific member by reading their
     * food listings from the Food Listings microservices.
     *
     * Rewards:
     * 10 points per valid submission
     * additional 10 points for complete record (data inserted to each field).
     *
     */

    public MemberImpact calculateImpact(String memberId) {


        /**
         *
         * created a object of food listing class of impact tracking service
         */

        List<FoodListingDTO> memberListing = fetchFoodListingsByMemberId(memberId);

        /**
         *
         * if the listing is empty it will not process for rewards calculations.
         */

        if (memberListing.isEmpty()) {
            return null;
        }



        MemberImpact memberImpact = new MemberImpact();
        memberImpact.setMemberId(memberId);

        memberImpact.setMemberName(memberListing.get(0).getMemberName());

        int totalPoints = 0;
        int completeCount = 0 ;
        int incompleteCount = 0;

        for (FoodListingDTO listing : memberListing) {
            totalPoints += pointsPerSubmission;

            if(listing.isComplete()) {
                totalPoints += pointsCompleteBonus;
                completeCount++ ;
            } else {
                incompleteCount++;
            }
        }

        memberImpact.setTotalListings(memberListing.size());
        memberImpact.setTotalPoints(totalPoints);
        memberImpact.setCompletedListings(completeCount);
        memberImpact.setIncompletedListings(incompleteCount);


        memberImpact.setPointsBadge(determinePointsBadge(totalPoints));

        memberImpact.setListingsBadge(determineListingsBadge(memberListing.size()));

        List<String> allBadges = new ArrayList<>();
        if(!"No Badge".equals(memberImpact.getPointsBadge())) {
            allBadges.add(memberImpact.getPointsBadge());
        }
        if (!"No Badge".equals(memberImpact.getListingsBadge())) {
            allBadges.add(memberImpact.getListingsBadge());
        }
        memberImpact.setAllBadges(allBadges);

        memberImpactMap.put(memberId, memberImpact);

        return memberImpact;
    }

    /**
     *
     * Determines the points based achievement badge
     * 100 points = Bronze
     * 200 points = Silver
     * 500 points = Gold
     */

    public String determinePointsBadge(int totalPoints) {
        if(totalPoints >= badgeGold) {
            return "Gold";
        } else if (totalPoints >= badgeSilver) {
            return "Silver";
        }  else if (totalPoints >= badgeBronze) {
            return "Bronze";
        }
        return "No Badge";
    }

    /**
     * Determines the listings-based achievement badge.
     * 10 listings = Bronze Saver
     * 25 listings = Silver Saver
     * 50 listings = Gold Saver
     * 100 listings = Community Hero Badge
     *
     */

    public String determineListingsBadge(int totalListings) {
        if(totalListings >= badgeCommunityHero) {
            return "Community Hero";
        } else if(totalListings >= badgeGoldSaver) {
            return "Gold Saver";
        } else if (totalListings >= badgeSilverSaver) {
            return "Silver Saver";
        } else if(totalListings >= badgeBronzeSaver) {
            return "Bronze Saver";
        }
        return "No Badge";

    }

    public MemberImpact getMemberImpact(String memberId) {
        return memberImpactMap.get(memberId);
    }

    public Collection<MemberImpact> getAllMemberImpacts() {
        return memberImpactMap.values();
    }

    public List<MemberImpact> calculateImpactsForAllMembers() {
        List<FoodListingDTO> allListings = fetchFoodListings();

        Map<String, List<FoodListingDTO>> listingsByMember = allListings.stream()
                .filter(listing -> listing.getMemberId() != null)
                .collect(Collectors.groupingBy(FoodListingDTO::getMemberId));

        List<MemberImpact> allImpacts = new ArrayList<>();

        for( Map.Entry<String, List<FoodListingDTO>> entry : listingsByMember.entrySet()) {
            String memberId = entry.getKey();
            List<FoodListingDTO> listings = entry.getValue();

            MemberImpact Impact = new MemberImpact();
            Impact.setMemberId(memberId);
            Impact.setMemberName(listings.get(0).getMemberName());

            int totalPoints = 0;
            int completeCount = 0;
            int incompleteCount = 0;

            for (FoodListingDTO listing : listings) {
                totalPoints += pointsPerSubmission;
                if (listing.isComplete()) {
                    totalPoints += pointsCompleteBonus;
                    completeCount++ ;
                } else {
                    incompleteCount++;
                }
            }

            Impact.setTotalListings(listings.size());
            Impact.setTotalPoints(totalPoints);
            Impact.setCompletedListings(completeCount);
            Impact.setIncompletedListings(incompleteCount);
            Impact.setPointsBadge(determinePointsBadge(totalPoints));
            Impact.setListingsBadge(determineListingsBadge(listings.size()));

            List<String> allBadges = new ArrayList<>();
            if(!"No Badge".equals(Impact.getPointsBadge())) {
                allBadges.add(Impact.getPointsBadge());
            }
            if (!"No Badge".equals(Impact.getListingsBadge())) {
                allBadges.add(Impact.getListingsBadge());
            }
            Impact.setAllBadges(allBadges);

            memberImpactMap.put(memberId, Impact);
            allImpacts.add(Impact);

        }

        return allImpacts;
    }
    /**
     *
     * Clears all impact data from memory
     */
    public void clearAllMemberImpacts() {
        memberImpactMap.clear();
    }
}
