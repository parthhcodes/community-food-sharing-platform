package com.project.ImpactTracking;

import com.project.ImpactTracking.Model.FoodListingDTO;
import com.project.ImpactTracking.Service.ImpactTrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ImpactTrackingServiceTest {

    private ImpactTrackingService impactTrackingService;

    /**
     *
     * It is used to assign the field with the values to use it in each test.
     */
    @BeforeEach
    public void setUp() {
        impactTrackingService = new ImpactTrackingService();

        ReflectionTestUtils.setField(impactTrackingService, "pointsPerSubmission", 10);
        ReflectionTestUtils.setField(impactTrackingService, "pointsCompleteBonus", 10);
        ReflectionTestUtils.setField(impactTrackingService, "badgeBronze", 100);
        ReflectionTestUtils.setField(impactTrackingService, "badgeSilver", 200);
        ReflectionTestUtils.setField(impactTrackingService, "badgeGold", 500);
        ReflectionTestUtils.setField(impactTrackingService, "badgeBronzeSaver", 10);
        ReflectionTestUtils.setField(impactTrackingService, "badgeSilverSaver", 25);
        ReflectionTestUtils.setField(impactTrackingService, "badgeGoldSaver", 50);
        ReflectionTestUtils.setField(impactTrackingService, "badgeCommunityHero", 100);

    }


    /**
     * Creates a mock food list to test each functions
     *
     */
    private FoodListingDTO createCompleteListing() {
        FoodListingDTO listing = new FoodListingDTO();
        listing.setId(1L);
        listing.setAvailability("available");
        listing.setFoodType("veg");
        listing.setFoodName("Baked Beans");
        listing.setQuantity("2 kg");
        listing.setAllergenInfo("Contains gluten");
        listing.setExpiryDate(LocalDate.now().plusDays(7));
        listing.setCondition("fresh");
        listing.setStorage("food cupboard");
        listing.setCommunity("Supermarket");
        listing.setMemberId("User_1");
        listing.setMemberName("Denny Patel");
        listing.setPickupLocation("NE1 7RU");
        listing.setEmail("john@example.com");
        return listing;
    }

    // Tests the listing has all the fields
    @Test
    public void testFoodListingDTO_isComplete(){
        FoodListingDTO listing = createCompleteListing();
        assertTrue(listing.isComplete());
    }

    // Tests the listing does not have all the fields
    @Test
    public void testFoodListingDTO_isNotComplete(){
        FoodListingDTO listing = createCompleteListing();
        listing.setAllergenInfo(null);
        assertFalse(listing.isComplete());
    }

    /**
     * Points Badge Tests
     *
     */

    @Test
    public void testDeterminePointsBadge_NoBadge() {
        String badge = impactTrackingService.determinePointsBadge(50);
        assertEquals("No Badge", badge);
    }

    @Test
    public void testDeterminePointsBadge_Bronze() {
        String badge = impactTrackingService.determinePointsBadge(100);
        assertEquals("Bronze", badge);
    }

    @Test
    public void testDeterminePointsBadge_Silver() {
        String badge = impactTrackingService.determinePointsBadge(200);
        assertEquals("Silver", badge);
    }

    @Test
    public void testDeterminePointsBadge_Gold() {
        String badge = impactTrackingService.determinePointsBadge(500);
        assertEquals("Gold", badge);
    }

    @Test
    public void testDetermineListingsBadge_NoBadge() {
        String badge = impactTrackingService.determineListingsBadge(5);
        assertEquals("No Badge", badge);

    }


    /**
     *
     * Listings Badge Tests
     */
    @Test
    public void testDetermineListingsBadge_Bronze()  {
        String badge = impactTrackingService.determineListingsBadge(10);
        assertEquals("Bronze Saver", badge);
    }

    @Test
    public void testDetermineListingsBadge_Silver()  {
        String badge = impactTrackingService.determineListingsBadge(25);
        assertEquals("Silver Saver", badge);
    }

    @Test
    public void testDetermineListingsBadge_Gold()  {
        String badge = impactTrackingService.determineListingsBadge(50);
        assertEquals("Gold Saver", badge);
    }

    @Test
    public void testDetermineListingsBadge_CommunityHero() {
        String badge = impactTrackingService.determineListingsBadge(100);
        assertEquals("Community Hero", badge);
    }

}
