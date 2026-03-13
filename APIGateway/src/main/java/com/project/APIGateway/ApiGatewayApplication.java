package com.project.APIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {

		String foodListingApi = uriConfiguration.getFoodListingApi();
		String impactTrackingApi = uriConfiguration.getImpactTrackingApi();

		return builder.routes()

				.route(p -> p
						.path("/api/foodlist/**")
						.filters(f -> f
								.addRequestHeader("Gateway", "SpringCloudGateway"))
						.uri(foodListingApi))

				.route(p -> p
						.path("/api/impact/**")
						.filters(f -> f
								.addRequestHeader("Gateway", "SpringCloudGateway"))
						.uri(impactTrackingApi))

				.build();
	}
}