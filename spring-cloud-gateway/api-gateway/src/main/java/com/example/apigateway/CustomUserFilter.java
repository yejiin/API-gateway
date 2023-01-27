package com.example.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class CustomUserFilter extends AbstractGatewayFilterFactory<CustomUserFilter.Config> {

    public CustomUserFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // pre filter 동작
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.debug("Custom User Pre Filter : request id = {}", request.getId());

            // post filter 동작
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.debug("Custom User Post Filter : response code -> {}", response.getStatusCode());
            }));
        });
    }

    public static class Config {

    }
}
