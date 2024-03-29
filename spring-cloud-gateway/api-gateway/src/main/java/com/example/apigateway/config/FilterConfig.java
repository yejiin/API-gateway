package com.example.apigateway.config;

import com.example.apigateway.filter.CustomGlobalFilter;
import com.example.apigateway.filter.CustomProductFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FilterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder, CustomProductFilter customProductFilter) {
        return routeLocatorBuilder
                .routes()
//                .route(r -> r
//                        .path("/user/**")
//                        .filters(f -> f
//                                .addRequestHeader("user-request", "user-request-header")
//                                .addResponseHeader("user-response", "user-response-header")
//                                )
//                        .uri("http://localhost:8081"))

                .route(r -> r
                        .path("/product/**")
                        .filters(f -> f
                                .addRequestHeader("product-request", "product-request-header")
                                .addResponseHeader("product-response", "product-response-header")
                                .filter(customProductFilter.apply(new CustomProductFilter.Config())))
                        .uri("http://localhost:8082"))
                .build();
    }

    @Bean
    public CustomGlobalFilter.Config config() {
        return new CustomGlobalFilter.Config("Spring Cloud Gateway Global Filter(Java Code)", true, true);
    }

    @Bean
    public GlobalFilter globalFilter() {
        log.debug("Global Filter baseMessage: {}", config().getBaseMessage());

        return ((exchange, chain) -> {
            new CustomGlobalFilter(config());
            return chain.filter(exchange);
        });
    }
}
