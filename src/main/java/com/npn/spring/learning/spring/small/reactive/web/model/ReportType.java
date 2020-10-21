package com.npn.spring.learning.spring.small.reactive.web.model;

/**
 * Определяет типы и форматирование отчетов
 */
public enum ReportType {
    RAW("<%d>,<Function-%d>,<%s>,<%d ms.>"),
    FORMATTED("<Iteration - %d>,<%s>,<%d ms.>,<counted function 1 - %d>,<%s>,<%d ms.>,<counted function 2 - %d>");

    private final String template;

    ReportType(String template){
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
