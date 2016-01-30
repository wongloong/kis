package com.kis.tools;

import com.alibaba.fastjson.JSON;
import com.kis.tools.Exception.ServerErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用辅助类
 * Created by yks on 15-10-9.
 */
public class Post {
    private Logger logger= LoggerFactory.getLogger(Post.class);
    /**
     * 获取返回结果
     * @param url 调用url
     * @param param 组装好的参数 @see com.kis.service.impl.BaseServiceImpl
     * @return Map 通过约定好的key取值。key值一般约定在ResultEnum里 @see com.kis.enums.ResultEnum
     *          返回map后。自己使用JSONOBJECT解析json
     */
    public Map getResultMap(String url,MultiValueMap param){
        String resultText=send(url,param);
        Map map = new HashMap<>();
        if(StringUtils.isNotBlank(resultText)){
            map = JSON.parseObject(resultText);
        }else {
            throw new ServerErrorException(url);
        }
        return map;
    }

    /**
     * 发送请求
     * @param url url
     * @param param 组装好的参数，一般由getResultMap调用
     * @return jsonString
     */
    private String send(String url,MultiValueMap param){
        String result="";
        try {
            result=RestClient.getClient().postForObject(url, param, String.class);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result;
    }
}
