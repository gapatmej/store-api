package com.aperalta.store.repository.enumeration;

public enum QueryOperationEnum {
    GREATER_THAN(">"),
    GREATER_OR_EQUAL(">="),
    LESS_THAN("<"),
    LESS_OR_EQUAL("<="),
    EQUAL("="),
    NOT_EQUAL("!="),
    LIKE(":"),
    IN("[]"),
    NOT_IN("![]"),
    NOT_DATA("<>"),
    IN_SEPARATOR(";"),
    KEY_SEPARATOR("\\."),
    OR("*");

    private String value;

    QueryOperationEnum(String value){
        this.value=value;
    }

    public String value() {
        return value;
    }
}
