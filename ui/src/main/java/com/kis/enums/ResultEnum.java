package com.kis.enums;

/**
 * Created by yks on 15-9-22.
 * 远程调用相关的enmu值。
 */
public enum ResultEnum {
    CODE("code"),
    ERRORMSG("errMsg"),
    DATA("data"),
    OPERFLAG("operationFlag"),
    CLASSNAME("className"),
    JSONOBJECT("jsonObject"),
    ID("id"),
    ORDERBY("orderBy"),
    ORDERFLAG("orderFlag"),
    CODE_SUCCESS("200");

    ResultEnum(String val) {
        this.val = val;
    }

    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
