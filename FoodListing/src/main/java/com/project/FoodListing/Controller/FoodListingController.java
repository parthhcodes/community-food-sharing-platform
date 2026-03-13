package com.project.FoodListing.Controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.FoodListing.Repository.FoodListingRepository;
import java.util.List;
import com.project.FoodListing.Model.FoodListing;

@RestController
@RequestMapping("/api/foodlist")
public class FoodListingController {

	private final FoodListingRepository foodListingRepository;

	public FoodListingController(FoodListingRepository foodListingRepository) {
		this.foodListingRepository = foodListingRepository;
	}

	/**
	 *
	 * Retrieves all Foodlisting
	 * @return FoodListing
	 */
	@GetMapping()
	public List<FoodListing> all() {
		return foodListingRepository.findAll();
	}

	/**
	 *
	 *	retrieves 1 food list through ID.
	 * @param id food listing id
	 * @return FoodListing with specific id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<FoodListing> getListingById(@PathVariable Long id) {
		return foodListingRepository.findById(id)
				.map(foodListing -> new ResponseEntity<>(foodListing, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 *
	 *
	 * @param foodList
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<FoodListing> createFoodList(@Valid @RequestBody FoodListing foodList) {
		try {
			FoodListing newItem = foodListingRepository.save(foodList);
			return new ResponseEntity<>(newItem, HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Void> updateFoodList(@PathVariable Long id,@RequestBody FoodListing updatedFoodList) {
		return foodListingRepository.findById(id)
			.map(foodList -> {
				foodList.setAvailability(updatedFoodList.getAvailability());
				foodList.setFoodType(updatedFoodList.getFoodType());
				foodList.setFoodName(updatedFoodList.getFoodName());
				foodList.setQuantity(updatedFoodList.getQuantity());
				foodList.setAllergenInfo(updatedFoodList.getAllergenInfo());
				foodList.setExpiryDate(updatedFoodList.getExpiryDate());
				foodList.setCondition(updatedFoodList.getCondition());
				foodList.setStorage(updatedFoodList.getStorage());
				foodList.setCommunity(updatedFoodList.getCommunity());
				foodList.setMemberId(updatedFoodList.getMemberId());
				foodList.setMemberName(updatedFoodList.getMemberName());
				foodList.setPickupLocation(updatedFoodList.getPickupLocation());
				foodList.setEmail(updatedFoodList.getEmail());
				foodListingRepository.save(foodList);
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			})
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteFoodList(@PathVariable Long id) {
		return foodListingRepository.findById(id)
				.map(foodList -> {
					foodListingRepository.delete(foodList);
					return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
				})
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/deleteall")
	public ResponseEntity<FoodListing> deleteAllFoodLists() {
		foodListingRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/search")
	public List<FoodListing> searchFoodListings(@RequestParam String keyword) {
		return foodListingRepository.searchByKeyword(keyword);
	}
}
