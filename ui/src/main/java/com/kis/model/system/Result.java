package com.kis.model.system;

/**
 * Created by yks on 15-10-13.
 */
public class Result {
    private boolean hasSuccess;
    private String msg="操作成功!";
    private String fileName;

    public Result() {
    }

    public Result(boolean hasSuccess, String msg, String fileName) {
        this.hasSuccess = hasSuccess;
        this.msg = msg;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isHasSuccess() {

        return hasSuccess;
    }

    public void setHasSuccess(boolean hasSuccess) {
        this.hasSuccess = hasSuccess;
    }
}
