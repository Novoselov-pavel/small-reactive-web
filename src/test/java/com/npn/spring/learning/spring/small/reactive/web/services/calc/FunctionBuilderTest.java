package com.npn.spring.learning.spring.small.reactive.web.services.calc;

import com.npn.spring.learning.spring.small.reactive.web.model.InputData;
import com.npn.spring.learning.spring.small.reactive.web.model.ReportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class FunctionBuilderTest {

    List<String> rawRowCheckList;

    List<String> formattedRowCheckList;

    @BeforeEach
    public void init(){
        Mono<List<String>> function1 = Flux
                .range(0,10)
                .map(x->"Function 1 - Iteration - "+x)
                .collect(Collectors.toList());

        Mono<List<String>> function2 = Flux
                .range(0,10)
                .map(x->"Function 2 - Iteration - "+x)
                .collect(Collectors.toList());

        List<String> expectedResult = Flux.merge(function1.flatMapMany(Flux::fromIterable),
                function2.flatMapMany(Flux::fromIterable))
                .collectList()
                .block();
        rawRowCheckList = expectedResult;



    }


    /**
     * Тестирует вывод неформатированных строк.
     * @throws InterruptedException
     */
    @Test
    public void functionRawReportBuilderTest() {
        FunctionBuilder builder = new FunctionBuilder(createRawReport(),20L);
        Flux<String> result = builder.getResult();

        StepVerifier.create(result)
                .recordWith(ArrayList::new)
                .expectNextCount(20)
                .consumeRecordedWith(x->{
                    Set<String> set = new HashSet<>(x);
                    assertEquals(20, set.size());
                    assertThat(set).allMatch(s->listContainString(rawRowCheckList,s));
                })
                .verifyComplete();
    }

    /**
     * Тестирует вывод форматированных строк.
     * @throws InterruptedException
     */
    @Test
    public void functionFormattedReportBuilderTest() throws InterruptedException {
        FunctionBuilder builder = new FunctionBuilder(createFormattedReport(),20L);
        builder.getResult().subscribe(System.out::println);
        Thread.sleep(15000);

        //TODO
    }

    /**
     * Проверяет, содержит ли переданная запись хотя-бы одно из значений,
     * @param list
     * @param value
     * @return
     */
    private boolean listContainString(Collection<String> list, String value) {
        return list
                .stream()
                .anyMatch(value::contains);
    }


    /**
     * Создает входные данные для простого тестового отчета.
     *
     * @return {@link InputData} для простого тестового отчета
     *
     */
    public InputData createRawReport() {
        String function1 = "'Function 1 - Iteration - ' + count;";

        String function2 = " for(var i = 0; i<100000; i++) {} " +
                " 'Function 2 - Iteration - ' + count; ";
        int iterationCount = 10;
        String reportType = ReportType.RAW.name();
        return new InputData(function1, function2, iterationCount, reportType);
    }


    /**
     * Создает входные данные для форматиррованного тестового отчета.
     *
     * @return {@link InputData} для форматиррованного тестового отчета
     *
     */
    public InputData createFormattedReport(){
        String function1 = "'Function 1 - Iteration - ' + count;";

        String function2 = " for(var i = 0; i<1000; i++) {} " +
                " 'Function 2 - Iteration - ' + count; ";
        int iterationCount = 10;
        String reportType = ReportType.FORMATTED.name();
        return new InputData(function1, function2, iterationCount, reportType);
    }
}