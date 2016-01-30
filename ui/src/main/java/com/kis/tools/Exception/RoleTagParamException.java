package com.kis.tools.Exception;

/**
 * Created by yks on 15-10-27.
 */
public class RoleTagParamException extends RuntimeException{
    public RoleTagParamException() {
        super("请输入且仅输入1个权限字段");
    }
}
