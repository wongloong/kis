package com.kis.data.enums;

/**
 * Created by wanglong on 16-1-30.
 */
public enum ResultEnum {
    CODE("code"), ERRORMSG("errMsg"), DATA("data"), OPERFLAG("operationFlag"), CLASSNAME("className"), JSONOBJECT("jsonObject");

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
