package com.project.APIGateway;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class UriConfiguration {

    private String foodListingApi = "http://loaclhost:8080";
    private String impactTrackingApi = "http://loaclhost:8080";

    public String getFoodListingApi() {
        return foodListingApi;
    }

    public void setFoodListingApi(String foodListingApi) {
        this.foodListingApi = foodListingApi;
    }

    public String getImpactTrackingApi() {
        return impactTrackingApi;
    }

    public void setImpactTrackingApi(String impactTrackingApi) {
        this.impactTrackingApi = impactTrackingApi;
    }
}
