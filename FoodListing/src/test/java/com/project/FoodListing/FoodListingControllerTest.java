package com.project.FoodListing;

import com.project.FoodListing.Model.FoodListing;

import com.project.FoodListing.Repository.FoodListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class FoodListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FoodListingRepository foodListingRepository;

    @BeforeEach
    public void setup() {
        foodListingRepository.deleteAll(); // clean slate

        FoodListing listing = new FoodListing(
                "Available", "Veg", "Pizza", "5 kg",
                "No allergic ingredients", LocalDate.parse("2026-03-12"),
                 "Good", "Refrigrator","Supermarket", "user_1" ,"Morrisons",
                "Cowgate Morrisons, Newcastle upon Tyne",
                "morrisons@gmail.com"
        );
        foodListingRepository.save(listing); // this gets auto-assigned id=1
    }
    @Test
    public void testGetFoodListing() throws Exception {
        // Arrange
        Long foodId = foodListingRepository.findAll().get(0).getId(); // get actual ID


        // Act
        ResultActions result = mockMvc.perform(get("/api/foodlist/{Id}", foodId));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(foodId))
                .andExpect(jsonPath("$.availability").value("Available"))
                .andExpect(jsonPath("$.foodType").value("Veg"))
                .andExpect(jsonPath("$.foodName").value("Pizza"))
                .andExpect(jsonPath("$.quantity").value("5 kg"))
                .andExpect(jsonPath("$.allergenInfo").value("No allergic ingredients"))
                .andExpect(jsonPath("$.expiryDate").value("2026-03-12"))
                .andExpect(jsonPath("$.condition").value("Good"))
                .andExpect(jsonPath("$.storage").value("Refrigrator"))
                .andExpect(jsonPath("$.community").value("Supermarket"))
                .andExpect(jsonPath("$.memberId").value("user_1"))
                .andExpect(jsonPath("$.memberName").value("Morrisons"))
                .andExpect(jsonPath("$.pickupLocation").value("Cowgate Morrisons, Newcastle upon Tyne"))
                .andExpect(jsonPath("$.email").value("morrisons@gmail.com"));
    }

    @Test
    public void testCreateFoodListing() throws Exception {
        // Arrange
        FoodListing mockFoodListing = new FoodListing("Available", "Veg", "Pizza", "5 kg", "No allergic ingredients", LocalDate.parse("2026-03-12"), "Good", "Refrigrator", "Supermarket", "user_1","Morrisons", "Cowgate Morrisons, Necastlke upon Tyne", "morrisons@gmail.com");
        String requestBody = objectMapper.writeValueAsString(mockFoodListing);

        // Act
        ResultActions result =  mockMvc.perform(post("/api/foodlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // Assert
        result.andExpect(status().isCreated());
    }

    @Test
    public void testUpdateFoodListing() throws Exception {
        // Arrange
        Long foodId = foodListingRepository.findAll().get(0).getId(); // get actual ID


        String updateFoodJson = "{\"availability\":\"Reserved\",\"foodType\":\"veg\",\"foodName\":\"Sandwich\",\"quantity\":\"7 pieces\",\"allergenInfo\":\"Gluten Free Bread\",\"expiryDate\":\"2026-03-11\",\"condition\":\"Good\",\"storage\":\"Refrigrator\",\"community\":\"Restaurant\",\"memberId\":\"user_5\",\"memberName\":\"Subway\",\"pickupLocation\":\"Cowgate Newcastle upon tyne\",\"email\":\"subway.cgt@gmail.com\" }";

        // Act
        ResultActions result = mockMvc.perform(put("/api/foodlist/update/{id}", foodId)
        .contentType(MediaType.APPLICATION_JSON)
                .content(updateFoodJson));

        // Assert
        result.andExpect(status().is(204));
    }

    @Test
    public void testDeleteFoodListing() throws Exception {
        // Arrange
        Long foodId = foodListingRepository.findAll().get(0).getId();

        // Act
        ResultActions result = mockMvc.perform(delete("/api/foodlist/delete/{id}", foodId));

        // Assert
        result.andExpect(status().is(204));
    }
}
