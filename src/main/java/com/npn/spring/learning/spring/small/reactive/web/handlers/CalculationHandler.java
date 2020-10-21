package com.npn.spring.learning.spring.small.reactive.web.handlers;

import com.npn.spring.learning.spring.small.reactive.web.model.InputData;
import com.npn.spring.learning.spring.small.reactive.web.services.calc.FunctionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Класс Handler
 *
 */

public class CalculationHandler {

    private static final Logger logger = LoggerFactory.getLogger(CalculationHandler.class);


    private final long calculationStartInterval;


    public CalculationHandler(long calculationStartInterval) {
        this.calculationStartInterval = calculationStartInterval;
    }


    public Mono<ServerResponse> getCalculatedResult(ServerRequest request) {
        Flux<String> functionResult = request
                .bodyToMono(InputData.class)
                .map(x->new FunctionBuilder(x,calculationStartInterval))
                .flatMapMany(FunctionBuilder::getResult);



        return ServerResponse
                .ok()
                .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                .body(functionResult,String.class)
                .onErrorResume(e-> {
                    logger.warn("Error: ", e);
                    return ServerResponse
                            .badRequest()
                            .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                            .body(Mono.just(e.getMessage()),String.class);
                });
    }

}
