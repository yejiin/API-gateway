package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomProductFilter extends AbstractGatewayFilterFactory<CustomProductFilter.Config> {

    public CustomProductFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // pre filter 동작
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.debug("Custom Product Pre Filter : request id = {}", request.getId());

            // post filter 동작
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.debug("Custom Product Post Filter : response code -> {}", response.getStatusCode());
            }));
        });
    }

    public static class Config {

    }
}
