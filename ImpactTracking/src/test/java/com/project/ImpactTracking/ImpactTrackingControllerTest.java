package com.project.ImpactTracking;


import com.project.ImpactTracking.Controller.ImpactTrackingController;
import com.project.ImpactTracking.Model.FoodListingDTO;
import com.project.ImpactTracking.Model.MemberImpact;
import com.project.ImpactTracking.Service.ImpactTrackingService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest
public class ImpactTrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ImpactTrackingController impactTrackingController;

    @MockitoBean
    private ImpactTrackingService impactTrackingService;

    /**
     *
     * Creates mock MemberImpact to perform test impact records with following fields
     * @param memberId - unique id of member
     * @param memberName - member name
     * @param totalPoints - total points
     * @param totalListings - total listings
     * @return
     */
    private MemberImpact createSampleImpact(String memberId, String memberName, int totalPoints, int totalListings){

        MemberImpact impact = new MemberImpact(memberId, memberName);
        impact.setTotalPoints(totalPoints);
        impact.setTotalListings(totalListings);
        impact.setPointsBadge(totalPoints >= 500 ? "Gold" : totalPoints >= 200 ? "Silver" : totalPoints >= 100 ? "Bronze" : "No Badge");
        impact.setListingsBadge(totalListings >= 100 ? "Community Hero" : totalListings >= 50 ? "Gold Saver" : totalListings >= 25 ? "Silver Saver" : totalListings >= 10 ? "Bronze Saver" : "No Badge");
        return impact;
    }


    @Test
    public void testGetMemberImpact() throws Exception {

        // Arrange
        MemberImpact impact = createSampleImpact("User_15", "Raj Patel", 130, 9);
        when(impactTrackingService.calculateImpact("User_15")).
                thenReturn(impact);

        // Act
        ResultActions result = mockMvc.perform(get("/api/impact/member/User_15"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("User_15"))
                .andExpect(jsonPath("$.memberName").value("Raj Patel"))
                .andExpect(jsonPath("$.totalPoints").value(130))
                .andExpect(jsonPath("$.totalListings").value(9))
                .andExpect(jsonPath("$.pointsBadge").value("Bronze"));
    }

    @Test
    public void testGetAllMemberImpact() throws Exception {

        // Arrange
        MemberImpact impact1 =  createSampleImpact("User_1", "Parth Patel", 130, 9);
        MemberImpact impact2 = createSampleImpact("User_2", "Vishu Patel", 230, 15);
        List<MemberImpact> impactList = new ArrayList<>();
        impactList.add(impact1);
        impactList.add(impact2);
        when(impactTrackingService.calculateImpactsForAllMembers()).thenReturn(impactList);

        // Act
        ResultActions result = mockMvc.perform(get("/api/impact/all"));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    public void testMemberListing() throws Exception {

        // Arrange
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
        listing.setMemberName("Jenny Patel");
        listing.setPickupLocation("NE1 7RU");
        listing.setEmail("john@example.com");
        when(impactTrackingService.fetchFoodListingsByMemberId("User_1")).thenReturn(List.of(listing));

        // Act
        ResultActions result = mockMvc.perform(get("/api/impact/listings/User_1"));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    public void testClearImpactData() throws Exception {
        // Arrange
        doNothing().when(impactTrackingService).clearAllMemberImpacts();

        // Act
        ResultActions result = mockMvc.perform(delete("/api/impact/clear"));

        // Assert
        result.andExpect(status().isNoContent());

    }

}
