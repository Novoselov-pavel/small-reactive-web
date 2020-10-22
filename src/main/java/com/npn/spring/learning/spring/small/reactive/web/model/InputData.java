package com.npn.spring.learning.spring.small.reactive.web.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Определяет входные данные для работы API
 */
public class InputData {
    /**
     * Выражение 1
     */
    @ApiModelProperty(example = "count + 'foo'", value = "First JS expression with parameter count")
    private String function1;
    /**
     * Выражение2
     */
    @ApiModelProperty(example = "count + 'bar'", value = "Second JS expression with parameter count")
    private String function2;

    /**
     * Количество требюуемых итераций
     */
    @ApiModelProperty(example = "10", value = "Number of iteration")
    private int iterationCount;

    /**
     * Тип отчета, должен быть или "RAW" или "FORMATTED"
     */

    @ApiModelProperty(allowableValues = "RAW,FORMATTED", value = "Type of report")
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
