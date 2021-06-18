package com.example.demo.config.constants;

public enum PredefinedQueries {
    SET_FOREIGN_KEYS_CHECK_0("SET foreign_key_checks = 0"),
    SET_FOREIGN_KEYS_CHECK_1("SET foreign_key_checks = 1");

    private final String query;

    PredefinedQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
