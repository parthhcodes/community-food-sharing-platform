package com.project.FoodListing.Repository;

import java.util.List;

import com.project.FoodListing.Model.FoodListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodListingRepository extends JpaRepository<FoodListing,Long> {

	@Query("SELECT f FROM FoodListing f WHERE " +
			"LOWER(f.foodName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(f.foodType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(f.availability) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(f.allergenInfo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<FoodListing> searchByKeyword(@Param("keyword") String keyword);

}
