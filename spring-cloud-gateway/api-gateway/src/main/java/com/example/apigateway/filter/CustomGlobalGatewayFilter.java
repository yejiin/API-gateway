package com.example.apigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomGlobalGatewayFilter extends AbstractGatewayFilterFactory<CustomGlobalGatewayFilter.Config> {

    public CustomGlobalGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        log.debug("Global Filter baseMessage: {}", config.getBaseMessage());

        // pre filter 동작
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogger()) {
                log.debug("Global Pre Filter : request id = {}", request.getId());
            }

            // post filter 동작
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    log.debug("Global Post Filter : response code -> {}", response.getStatusCode());
                }
            }));
        });
    }

    @Data
    @AllArgsConstructor
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
