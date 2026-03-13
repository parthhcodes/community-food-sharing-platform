package com.project.ImpactTracking.Model;


import java.time.LocalDate;

/**
 * This class depicts about the food listing entities that will be tracked by this microservice
 *
 */
public class FoodListingDTO {

    /**
     *
     * Below fields are exactly similar to the food listing entities as we need them in impact tracking to track the food waste of each member.
     */
    private Long id;
    private String availability;
    private String foodType;
    private String foodName;
    private String quantity;
    private String allergenInfo;
    private LocalDate expiryDate;
    private String condition;
    private String storage;
    private String community;
    private String memberId;
    private String memberName;
    private String pickupLocation;
    private String email;

    /**
     * contructor
     */

    public FoodListingDTO() {}

    public FoodListingDTO(Long id, String availability, String foodType, String foodName, String quantity,  String allergenInfo, LocalDate expiryDate, String condition, String storage, String community, String memberId,  String memberName, String pickupLocation, String email) {
        this.id = id;
        this.availability = availability;
        this.foodType = foodType;
        this.foodName = foodName;
        this.quantity = quantity;
        this.allergenInfo = allergenInfo;
        this.expiryDate = expiryDate;
        this.condition = condition;
        this.storage = storage;
        this.community = community;
        this.memberId = memberId;
        this.memberName = memberName;
        this.pickupLocation = pickupLocation;
        this.email = email;
    }

    /**
     *
     * getter and setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAllergenInfo() {
        return allergenInfo;
    }

    public void setAllergenInfo(String allergenInfo) {
        this.allergenInfo = allergenInfo;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

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

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    /**
     *
     *  Checks whether all the fields are inserted or not. as we need to give +10 bonus to the member for providing all the info that is required.
     */

    public boolean isComplete(){
        return availability != null
                && foodType != null
                && foodName != null
                && quantity != null
                && allergenInfo != null
                && expiryDate != null
                && condition != null
                && storage != null
                && community != null
                && memberId != null
                && memberName != null
                && pickupLocation != null
                && email != null;
    }

    /**
     *
     *
     * Returns fields like id, foodName, memberId, memberName along with its value in JSON format.
     */

    @Override
    public String toString() {
        return "FoodListingDTO{" + "id=" + id + ", foodName='" + foodName + "', memberId='" + memberId + "', memberName='" + memberName + "'}" ;
    }
}
