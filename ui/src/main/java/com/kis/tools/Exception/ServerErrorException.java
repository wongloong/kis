package com.kis.tools.Exception;

/**
 * 远程服务器异常
 * Created by yks on 15-10-12.
 */
public class ServerErrorException extends RuntimeException{
    public ServerErrorException(String msg){super(msg+"远程服务器异常");}
}
