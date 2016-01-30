package com.kis.enums;

/**
 * Created by yks on 15-10-26.
 */
public enum ParamEnum {
    LT("<"),
    GT(">"),
    IN("in"),
    LIKE("like"),
    EQ("=");
    private String operVal;

    ParamEnum(String operVal) {
        this.operVal = operVal;
    }

    public String getOperVal() {
        return operVal;
    }
}
