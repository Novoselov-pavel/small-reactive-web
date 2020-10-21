package com.npn.spring.learning.spring.small.reactive.web.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlersConfig {

    private final long calculationStartInterval;

    @Autowired
    public HandlersConfig(@Value("${calculation.start.interval}") long calculationStartInterval) {
        this.calculationStartInterval = calculationStartInterval;
    }

    @Bean
    public CalculationHandler getCalculationHandler(){
        return new CalculationHandler(calculationStartInterval);
    }

}
