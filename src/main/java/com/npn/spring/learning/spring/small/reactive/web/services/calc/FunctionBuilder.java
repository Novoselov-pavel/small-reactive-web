package com.npn.spring.learning.spring.small.reactive.web.services.calc;


import com.npn.spring.learning.spring.small.reactive.web.model.InputData;
import com.npn.spring.learning.spring.small.reactive.web.model.ReportType;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FunctionBuilder {

    private final String jsFunction1;
    private final String jsFunction2;
    private final int iteration;
    private final ReportType reportType;

    private long interval;
    private AtomicInteger function1Counter = new AtomicInteger();
    private AtomicInteger function2Counter = new AtomicInteger();

    public FunctionBuilder(InputData inputData, long interval) {
        this.jsFunction1 = inputData.getFunction1();
        this.jsFunction2 = inputData.getFunction2();
        this.iteration = inputData.getIterationCount();
        this.reportType = ReportType.valueOf(inputData.getReportType());
        Assert.notNull(jsFunction1,"Function can't ne null");
        Assert.notNull(jsFunction2,"Function can't ne null");
        Assert.notNull(reportType,"Report type can't ne null");
    }

    public FunctionBuilder(String jsFunction1, String jsFunction2, int iteration, Long interval, ReportType reportType) {
        this.jsFunction1 = jsFunction1;
        this.jsFunction2 = jsFunction2;
        this.iteration = iteration;
        this.interval = interval;
        this.reportType =reportType;
        Assert.notNull(jsFunction1,"Function can't ne null");
        Assert.notNull(jsFunction2,"Function can't ne null");
        Assert.notNull(reportType,"Report type can't ne null");
    }


    public Flux<String> getResult() {
        Flux<FunctionResult> result1 = Flux.range(0,iteration)
                .delayElements(Duration.ofMillis(interval))
                .map(x->calculate(1,x,function1Counter,jsFunction1));

        Flux<FunctionResult> result2 = Flux.range(0,iteration)
                .delayElements(Duration.ofMillis(interval))
                .map(x->calculate(2,x,function2Counter,jsFunction2));

        return getFormatResult(result1,result2,reportType);
    }

    private Flux<String> getFormatResult(Flux<FunctionResult> result1,
                                        Flux<FunctionResult> result2,
                                        ReportType reportType){
        switch (reportType){
            case RAW:
                return Flux.merge(result1,result2).map(this::getRawResult);
            case FORMATTED:
                return Flux.zip(result1,result2,this::getFormattedResult);
            default:
                return Flux.empty();
        }
    }


    private String getFormattedResult(final FunctionResult result1, final FunctionResult result2) {
        return String.format(reportType.getTemplate(),
                result1.getCurrentIteration(),
                result1.getFunctionResult().toString(),
                result1.getCaclulationTime(),
                function1Counter.get()-result1.getCurrentIteration()-1,
                result2.getFunctionResult().toString(),
                result2.getCaclulationTime(),
                function2Counter.get()-result2.getCurrentIteration()-1);
    }

    private String getRawResult(final FunctionResult result) {
        return String.format(reportType.getTemplate(),
                result.getFunctionNumber(),
                result.getCurrentIteration(),
                result.getFunctionResult().toString(),
                result.getCaclulationTime());
    }


    private FunctionResult calculate(final int functionNumber, final int iteration, final AtomicInteger counter, final String jsFunction) {
        Date startTime = new Date();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Bindings bindings = engine.createBindings();
        bindings.put("count",iteration);
        try {
            Object result = engine.eval(jsFunction,bindings);
            if (functionNumber == 1){
                function1Counter.addAndGet(1);
            } else if (functionNumber == 2) {
                function2Counter.addAndGet(1);
            }
            return new FunctionResult(functionNumber,iteration,result,new Date().getTime()-startTime.getTime());
        } catch (ScriptException e) {
            throw new IllegalStateException(e);
        }
    }



    private class FunctionResult {
        private final int functionNumber;
        private final int currentIteration;
        private final Object functionResult;
        private final long caclulationTime;

        public FunctionResult(int functionNumber,int currentIteration, Object functionResult, long caclulationTime) {
            this.functionNumber = functionNumber;
            this.currentIteration = currentIteration;
            this.functionResult = functionResult;
            this.caclulationTime = caclulationTime;
        }

        public int getCurrentIteration() {
            return currentIteration;
        }

        public Object getFunctionResult() {
            return functionResult;
        }

        public long getCaclulationTime() {
            return caclulationTime;
        }

        public int getFunctionNumber() {
            return functionNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FunctionResult that = (FunctionResult) o;
            return functionNumber == that.functionNumber &&
                    currentIteration == that.currentIteration &&
                    caclulationTime == that.caclulationTime &&
                    Objects.equals(functionResult, that.functionResult);
        }

        @Override
        public int hashCode() {
            return Objects.hash(functionNumber, currentIteration, functionResult, caclulationTime);
        }
    }


}