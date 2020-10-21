package com.npn.spring.learning.spring.small.reactive.web.routers;

import com.npn.spring.learning.spring.small.reactive.web.handlers.CalculationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import java.nio.charset.StandardCharsets;

/**
 * Конфигурация адресов сервера
 */
@Configuration
public class MyRouter {


    @Bean
    public RouterFunction<ServerResponse> route(CalculationHandler handler) {
        RequestPredicate postFunctions = RequestPredicates.GET("/getData")
                .and(RequestPredicates.accept(new MediaType("application", "json", StandardCharsets.UTF_8)));

        return RouterFunctions
                .route(postFunctions,handler::getCalculatedResult);
    }
}
