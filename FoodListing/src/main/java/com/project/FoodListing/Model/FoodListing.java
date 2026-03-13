package com.project.FoodListing.Model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 *
 * This class defines all the fields for the food details and community member details
 */
@Entity
@Table(name = "food")
public class FoodListing {
	
	
	//Food Details

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = true)
	private Long id;

	@NotBlank(message = "Availability is required")
	private String availability ; // available, reserved, cancelled

	@NotBlank(message = "FoodType is required")
	private String foodType; // veg or non-veg

	@NotBlank(message = "FoodName is required")
	private String foodName;

	@NotBlank(message = "Quantity is required")
	private String quantity; // kg, gm, pieces


	private String allergenInfo; // if includes any allergic ingredients

	@NotNull(message = "ExpiryDate is required")
	@Future(message = "Expiry date must be in the future")
	private LocalDate expiryDate;

	private String condition;

	private String storage;
	
	// donor Details


	@NotBlank(message = "Community is required")
	private String community;// Restaurant, Supermarket, Individuals

	@NotBlank(message = "MemberId is required")
	private String memberId;

	@NotBlank(message = "MemberName is required")
	private String memberName;

	@NotBlank(message = "PickupLocation is required")
	private String pickupLocation;

	@NotBlank
	@Email
	private String email;
	
	// constructors 
	
	public FoodListing() {}
	
	public FoodListing(String availability, String foodType, String foodName, String quantity, String allergenInfo, LocalDate expiryDate, String condition, String storage, String community, String memberId, String memberName, String pickupLocation, String email) {
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
	
	
	// Getters 
	
	public Long getId() {
		return id;
	}
	
	public String getAvailability() {
		return availability;
	}
	
	public String getFoodType() {
		return foodType;
	}
	
	public String getFoodName() {
		return foodName;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public String getAllergenInfo() {
		return allergenInfo;
	}
	
	public LocalDate getExpiryDate() { return expiryDate; }

	public String getCondition() { return condition; }

	public String getStorage() { return storage; }
	
	public String getCommunity() {
		return community;
	}

	public String getMemberId() { return memberId; }

	public String getMemberName() { return memberName; }
	
	public String getPickupLocation() {
		return pickupLocation;
	}
	
	public String getEmail() {
		return email;
	}

	
	
	//setters
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAvailability(String availability) {
		this.availability = availability ; 
	}
	
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public void setAllergenInfo(String description) {
		this.allergenInfo = description;
	}
	
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate; 
	}

	public void setCondition(String condition) { this.condition = condition; }

	public void setStorage(String storage) { this.storage = storage; }
	
	public void setCommunity(String community) {
		this.community = community;
	}

	public void setMemberId(String memberId) { this.memberId = memberId; }

	public void setMemberName(String donorName) {
		this.memberName = donorName;
	}
	
	public void setPickupLocation(String location) {
		this.pickupLocation = location;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}



}
