package com.kis.data.enums;

/**
 * Created by wanglong on 16-1-30.
 */
public enum ResponseCode {
    SUCCESS("200"), ERROR("500");
    private String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
