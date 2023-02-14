package com.example.apigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomGlobalFilter implements GlobalFilter {

    private final CustomGlobalFilter.Config config;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (config.isPreLogger()) {
            log.debug("Global Pre Filter : request id = {}", request.getId());
        }

        // post filter 동작
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            if (config.isPostLogger()) {
                log.debug("Global Post Filter : request id = {}", request.getId());
            }
        }));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
