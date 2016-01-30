package com.kis.data.controller;

import com.alibaba.fastjson.JSON;
import com.kis.data.enums.ResponseCode;
import com.kis.data.enums.ResultEnum;
import com.kis.data.model.Page;
import com.kis.data.model.Param;
import com.kis.data.service.CommonDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglong on 16-1-30.
 */
@Controller
@RequestMapping("/base")
public class BaseController {
    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private CommonDataService commonDataService;

    @RequestMapping("add")
    @ResponseBody
    public Map<String, Object> add(String jsonObject, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        boolean result = false;
        try {
            Class c = Class.forName(className);
            Object o = JSON.parseObject(jsonObject, c);
            result = commonDataService.addObject(o);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.OPERFLAG.getVal(), result);
        return mapResult;
    }

    @RequestMapping("batchAdd")
    @ResponseBody
    public Map<String, Object> batchAdd(String jsonObject, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        boolean result = false;
        try {
            Class c = Class.forName(className);
            List list = JSON.parseArray(jsonObject, c);
            result = commonDataService.batchAddObject(list);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.OPERFLAG.getVal(), result);
        return mapResult;
    }

    @RequestMapping("findById")
    @ResponseBody
    public Map<String, Object> findById(Long id, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        try {
            Class c = Class.forName(className);
            Object o = c.newInstance();
            o = commonDataService.findById(id, c);
            mapResult.put(ResultEnum.DATA.getVal(), o);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return mapResult;
    }

    @RequestMapping("delObject")
    @ResponseBody
    public Map<String, Object> delObject(String jsonObject, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        boolean result = false;
        try {
            Class c = Class.forName(className);
            Object o = JSON.parseObject(jsonObject, c);
            result = commonDataService.delObject(o);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.OPERFLAG.getVal(), result);
        return mapResult;
    }

    @RequestMapping("delObjectById")
    @ResponseBody
    public Map<String, Object> delObjectById(Long id, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        boolean result = false;
        try {
            Class clazz = Class.forName(className);
            result = commonDataService.delObjectById(id, clazz);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.OPERFLAG.getVal(), result);
        return mapResult;
    }

    @RequestMapping("delObjectByParam")
    @ResponseBody
    public Map<String, Object> delObjectById(String jsonObject, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        boolean result = false;
        try {
            List<Param> params = JSON.parseArray(jsonObject, Param.class);
            Class clazz = Class.forName(className);
            result = commonDataService.delObjectByParam(params, clazz);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.OPERFLAG.getVal(), result);
        return mapResult;
    }

    @RequestMapping("updateObject")
    @ResponseBody
    public Map<String, Object> updateObject(String jsonObject, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        boolean result = false;
        try {
            Class clazz = Class.forName(className);
            Object o = JSON.parseObject(jsonObject, clazz);
            result = commonDataService.updateObject(o, clazz);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.OPERFLAG.getVal(), result);
        return mapResult;
    }

    @RequestMapping("getAll")
    @ResponseBody
    public Map<String, Object> getAll(String orderBy, Integer orderFlag, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        List data = new ArrayList<>();
        try {
            Class clazz = Class.forName(className);
            data = commonDataService.getAll(clazz, orderBy, orderFlag);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.DATA.getVal(), data);
        return mapResult;
    }

    @RequestMapping("getListByParams")
    @ResponseBody
    public Map<String, Object> getListByParams(@RequestHeader HttpHeaders httpHeaders, String jsonObject, String orderBy, Integer orderFlag, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        List data = new ArrayList<>();
        try {
            List<Param> params = JSON.parseArray(jsonObject, Param.class);
            Class clazz = Class.forName(className);
            data = commonDataService.getList(params, clazz, orderBy, orderFlag);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.DATA.getVal(), data);
        return mapResult;
    }

    @RequestMapping("getOneByParams")
    @ResponseBody
    public Map<String, Object> getOneByParams(String jsonObject, String className) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        Object o = null;
        try {
            List<Param> params = JSON.parseArray(jsonObject, Param.class);
            Class clazz = Class.forName(className);
            o = commonDataService.findByParams(params, clazz);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.DATA.getVal(), o);
        return mapResult;
    }

    @RequestMapping("queryPage")
    @ResponseBody
    public Map<String, Object> queryPage(String jsonObject, String className, String orderBy, Integer orderFlag, String page) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.SUCCESS.getCode());
        Page returnData = new Page();
        List<Param> params = new ArrayList<Param>();
        try {
            if (null != jsonObject) {
                params = JSON.parseArray(jsonObject, Param.class);
            }
            Page pageObj = JSON.parseObject(page, Page.class);
            Class clazz = Class.forName(className);
            returnData = commonDataService.queryPage(params, clazz, orderBy, orderFlag, pageObj);
        } catch (ClassNotFoundException e) {
            mapResult.put(ResultEnum.CODE.getVal(), ResponseCode.ERROR.getCode());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        mapResult.put(ResultEnum.DATA.getVal(), returnData);
        return mapResult;
    }
}
