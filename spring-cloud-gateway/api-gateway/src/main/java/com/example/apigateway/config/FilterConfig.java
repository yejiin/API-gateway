package com.example.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig{
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(r -> r
                        .path("/user/**")
                        .filters(f -> f
                                .addRequestHeader("user-request", "user-request-header")
                                .addResponseHeader("user-response", "user-response-header"))
                        .uri("http://localhost:8081"))

                .route(r -> r
                        .path("/product/**")
                        .filters(f -> f
                                .addRequestHeader("product-request", "product-request-header")
                                .addResponseHeader("product-response", "product-response-header"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
