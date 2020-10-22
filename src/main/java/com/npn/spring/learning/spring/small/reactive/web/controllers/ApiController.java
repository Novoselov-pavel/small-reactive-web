package com.npn.spring.learning.spring.small.reactive.web.controllers;

import com.npn.spring.learning.spring.small.reactive.web.model.InputData;
import com.npn.spring.learning.spring.small.reactive.web.services.calc.FunctionCalcService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

/**
 * Контроллер для API
 */
@RestController
public class ApiController {

    private final long calculationStartInterval;
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    public ApiController(@Value("${calculation.start.interval}") long calculationStartInterval) {
        this.calculationStartInterval = calculationStartInterval;
    }

    @PostMapping(value = "/getData", consumes = "application/json;charset=UTF-8", produces = "text/plain;charset=UTF-8")
    public Flux<String> calculatePostFunction(@RequestBody InputData inputData){
        return new FunctionCalcService(inputData,calculationStartInterval)
                .getResult()
                .onErrorResume(x->{
                    logger.warn("Calculation error", x);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,x.getMessage());
                });
    }

    @GetMapping(value = "/getData", produces = "text/plain;charset=UTF-8")
    public Flux<String> calculateGetFunction(@ApiParam(example = "count + 'foo'", value = "First JS expression with parameter count")
                                             @RequestParam("func1") String function1,

                                             @ApiParam(example = "count + 'foo'", value = "First JS expression with parameter count")
                                             @RequestParam("func2") String function2,

                                             @ApiParam(example = "10", value = "Number of iteration")
                                             @RequestParam("iter") int iterationCount,

                                             @ApiParam(allowableValues = "RAW,FORMATTED", value = "Type of report")
                                             @RequestParam("report") String reportType){

        InputData inputData = new InputData(function1,function2,iterationCount,reportType);

        return new FunctionCalcService(inputData,calculationStartInterval)
                .getResult()
                .onErrorResume(x->{
                    logger.warn("Calculation error", x);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,x.getMessage());
                });
    }
}
