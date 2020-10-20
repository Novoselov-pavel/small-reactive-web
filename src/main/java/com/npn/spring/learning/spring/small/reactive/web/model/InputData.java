package com.npn.spring.learning.spring.small.reactive.web.model;

public class InputData {
    private String function1;
    private String function2;
    private int iterationCount;

    private String reportType;

    public InputData() {
    }

    public InputData(String function1, String function2, int iterationCount, String reportType) {
        this.function1 = function1;
        this.function2 = function2;
        this.iterationCount = iterationCount;
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFunction1() {
        return function1;
    }

    public void setFunction1(String function1) {
        this.function1 = function1;
    }

    public String getFunction2() {
        return function2;
    }

    public void setFunction2(String function2) {
        this.function2 = function2;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }


}
