package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

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

            String userId = request.getQueryParams().getFirst("id");
            if (userId != null) {
                ServerWebExchangeUtils.addOriginalRequestUrl(exchange, request.getURI());
                String newUri = "http://" + request.getURI().getHost() + ":" + request.getURI().getPort() + "/user/" + userId;
                ServerHttpRequest newRequest = null;
                try {
                    newRequest = request.mutate().uri(new URI(newUri)).build();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                exchange = exchange.mutate().request(newRequest).build();
            }

            // post filter 동작
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.debug("Custom User Post Filter : response code -> {}", response.getStatusCode());
            }));
        });
    }

    public static class Config {

    }
}
