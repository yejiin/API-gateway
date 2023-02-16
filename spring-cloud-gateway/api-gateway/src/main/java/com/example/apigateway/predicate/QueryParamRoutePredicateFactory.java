package com.example.apigateway.predicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Slf4j
@Component
public class QueryParamRoutePredicateFactory extends AbstractRoutePredicateFactory<QueryParamRoutePredicateFactory.Config> {
    public static final String PARAM_KEY = "param";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM_KEY);
    }

    public QueryParamRoutePredicateFactory() {
        super(QueryParamRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (GatewayPredicate) exchange -> {
            if (exchange.getRequest().getQueryParams().containsKey(config.param)) {
                String value = exchange.getRequest().getQueryParams().getFirst(config.param);
                log.debug("Saving {}={}", config.param, value);
                ServerWebExchangeUtils.putUriTemplateVariables(exchange, Map.of(config.param, value));
                return true;
            }
            log.debug("Query parameter {} not found", config.param);
            return false;
        };
    }

    @Validated
    @Data
    @AllArgsConstructor
    public static class Config {

        @NotEmpty
        private String param;
    }
}
