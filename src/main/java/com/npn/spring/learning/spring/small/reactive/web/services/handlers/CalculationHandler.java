package com.npn.spring.learning.spring.small.reactive.web.services.handlers;

import com.npn.spring.learning.spring.small.reactive.web.model.InputData;
import com.npn.spring.learning.spring.small.reactive.web.routers.MyRouter;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class CalculationHandler {

    public Mono<ServerResponse> getCalculatedResult(ServerRequest request) {


//        return ServerResponse
//                .ok()
//                .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
//                .body(BodyInserters.fromProducer());
//
        return ServerResponse
                .ok()
                .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                .body(BodyInserters.empty());
    }

}
