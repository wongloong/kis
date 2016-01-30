package com.kis.tools.Exception;

/**
 * 查询参数为空异常
 * Created by yks on 15-10-10.
 */
public class ParamsIsNullException extends RuntimeException{
    public ParamsIsNullException(String msg){
        super(msg);
    }
}
