package com.kis.tools.Exception;

/**
 * Created by yks on 15-10-26.
 */
public class IdsCommaIsNotFoundException extends RuntimeException{
    public IdsCommaIsNotFoundException(String msg) {
        super("请检查参数是否含有逗号分隔符-->"+msg);
    }
}
