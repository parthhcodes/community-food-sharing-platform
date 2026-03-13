package com.project.FoodListing;

import com.project.FoodListing.Model.FoodListing;
import com.project.FoodListing.Repository.FoodListingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * Storage of test data is totally in-memory
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FoodListingRepositoryTest {

    @Autowired
    private FoodListingRepository foodListingRepository;

    /**
     *
     * This method tests all listings at once from mock foodLists
     */
    @Test
    public void testGetAll() {
        // Arrange
        FoodListing foodListing1 = new FoodListing("Available", "Veg", "Burger", "5 pieces", "No allergic ingredients", LocalDate.parse("2026-04-22"), "Normal", "Refrigrator", "Restaurant","user_2", "Burger King", "City Centre, Newcastle Upon Tyne", "BurgerKing@gmail.com");
        FoodListing foodListing2 = new FoodListing("Available", "Non Veg", "Tandoori Chicken Pizza", "10 pieces", "No allergic ingredients", LocalDate.parse("2026-04-22"), "Good", "Refrigrator" ,"Restaurant", "user_3", "Greggs", "City Centre Newcastle upon tyne", "greggs@gmail.com" );
        foodListingRepository.saveAll(List.of(foodListing1,foodListing2));

        // Act
        List<FoodListing> foodLst = foodListingRepository.findAll();

        // Assert
        assertEquals(2, foodLst.size());
        assertTrue(foodLst.contains(foodListing1));
        assertTrue(foodLst.contains(foodListing2));
    }

    /**
     * Test for retrieving food list by specific Id from a mock foodList
     */
    @Test
    public void testGetById() {

        // Arrange
        FoodListing foodListing3 = new FoodListing("Available", "Non Veg", "Tandoori Chicken Pizza", "10 pieces", "No allergic ingredients", LocalDate.parse("2026-04-22"),"Good", "Refrigrator", "Restaurant", "user_3", "Greggs", "City Centre Newcastle upon tyne", "greggs@gmail.com" );
        foodListingRepository.save(foodListing3);

        // Act
        FoodListing foundFoodListing = foodListingRepository.findById(foodListing3.getId()).orElse(null);

        // Assert
        assertEquals(foodListing3, foundFoodListing);
    }



}
