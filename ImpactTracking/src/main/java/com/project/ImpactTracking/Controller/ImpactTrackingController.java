package com.project.ImpactTracking.Controller;


import com.project.ImpactTracking.Model.FoodListingDTO;
import com.project.ImpactTracking.Model.MemberImpact;
import com.project.ImpactTracking.Service.ImpactTrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * REST controller provides endpoints to calculate and retrieve impact data for community members.
 */
@RestController
@RequestMapping("/api/impact")
public class ImpactTrackingController {

    private final ImpactTrackingService impactTrackingService;


    public ImpactTrackingController(ImpactTrackingService impactTrackingService) {
        this.impactTrackingService = impactTrackingService;
    }

    /**
     * Http GET requests /api/impact/member/{memberId}
     * Calculates and returns the impact data of a specific member
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getMemberImpact(@PathVariable String memberId) {
        MemberImpact impact = impactTrackingService.calculateImpact(memberId);
        if (impact == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Member not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(impact, HttpStatus.OK);
    }

    /**
     * GET /api/impact/all
     * Calculates and returns impact data for all members.
     */
    @GetMapping("/all")
    public ResponseEntity<List<MemberImpact>> getAllMembersImpact() {
        List<MemberImpact> allImpacts = impactTrackingService.calculateImpactsForAllMembers();
        if (allImpacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allImpacts, HttpStatus.OK);
    }

    /**
     * HTTP GET Request /api/impact/listings/{memberId}
     * Fetches the raw food listings for a specific member from the food Listing microservice.
     *
     *
     */
    @GetMapping("/listings/{memberId}")
    public ResponseEntity<List<FoodListingDTO>> getMemberListings(@PathVariable String memberId) {
        List<FoodListingDTO> listings = impactTrackingService.fetchFoodListingsByMemberId(memberId);
        if (listings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    /**
     * HTTP DELETE Request /api/impact/clear
     * Clears all in memory impact data.
     *
     */
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearImpactData() {
        impactTrackingService.clearAllMemberImpacts();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
