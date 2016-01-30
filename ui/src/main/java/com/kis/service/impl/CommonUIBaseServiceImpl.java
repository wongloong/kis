package com.kis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kis.enums.ResultEnum;
import com.kis.model.system.Page;
import com.kis.model.system.Param;
import com.kis.service.CommonUIBaseService;
import com.kis.tools.CommonTool;
import com.kis.tools.Post;
import com.kis.tools.ReadTool;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglong on 15-9-12.
 */
@Service("baseService")
public class CommonUIBaseServiceImpl<T> implements CommonUIBaseService<T> {
    private static String ip = getIp();
    private final String BASE_ADD = "/base/add";
    private final String BASE_FIND_BY_ID = "/base/findById";
    private final String BASE_BATCH_ADD = "/base/batchAdd";
    private final String BASE_DELETE_OBJECT = "/base/delObject";
    private final String BASE_DELETE_OBJECT_BY_ID = "/base/delObjectById";
    private final String BASE_DELETE_OBJECT_BY_PARAM = "/base/delObjectByParam";
    private final String BASE_UPDATE = "/base/updateObject";
    private final String BASE_GET_ALL = "/base/getAll";
    private final String BASE_GET_LIST_BY_PARAMS = "/base/getListByParams";
    private final String BASE_GET_ONE_BY_PARAMS = "/base/getOneByParams";
    private final String BASE_QUERY_PAGE = "/base/queryPage";

    @Override
    public boolean add(Object o, Class clazz) {
        return add(o, clazz, BASE_ADD);
    }

    @Override
    public boolean batchAdd(List<T> list, Class clazz) {
        return batchAdd(list, clazz, BASE_BATCH_ADD);
    }

    @Override
    public boolean delObject(T o, Class clazz) {
        return delObject(o, clazz, BASE_DELETE_OBJECT);
    }

    @Override
    public boolean delById(Long id, Class clazz) {
        return delById(id, clazz, BASE_DELETE_OBJECT_BY_ID);
    }

    @Override
    public boolean delByParams(List<Param> params, Class clazz) {
        return delByParams(params, clazz, BASE_DELETE_OBJECT_BY_PARAM);
    }

    @Override
    public boolean updateObject(T t, Class clazz) {
        return updateObject(t, clazz, BASE_UPDATE);
    }

    @Override
    public T findById(Long id, Class clazz) {
        return findById(id, clazz, BASE_FIND_BY_ID);
    }

    @Override
    public List<T> getAll(Class clazz, String orderBy, Integer orderFlag) {
        return getAll(clazz, orderBy, orderFlag, BASE_GET_ALL);
    }

    @Override
    public List<T> getListByParams(List<Param> params, Class clazz, String orderBy, Integer orderFlag) {
        return getListByParams(params, clazz, orderBy, orderFlag, BASE_GET_LIST_BY_PARAMS);
    }

    @Override
    public T getOneByParams(List<Param> params, Class clazz) {
        return getOneByParams(params, clazz, BASE_GET_ONE_BY_PARAMS);
    }

    @Override
    public Page queryPage(List<Param> params, Page page, Class clazz, TypeReference typeReference, String orderBy, Integer orderFlag) {
        return queryPage(params, page, clazz, typeReference, orderBy, orderFlag, BASE_QUERY_PAGE);
    }

    @Override
    public boolean add(Object o, Class clazz, String url) {
        boolean hasSuccess = false;
        if (CommonTool.assertIsNotNull(o, clazz, url)) {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(o));
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (map.get(ResultEnum.CODE.getVal()).toString().equals(ResultEnum.CODE_SUCCESS.getVal())) {
                hasSuccess = (boolean) map.get(ResultEnum.OPERFLAG.getVal());
            }
        }
        return hasSuccess;
    }

    @Override
    public boolean batchAdd(List<T> list, Class clazz, String url) {
        boolean hasSuccess = false;
        if (CommonTool.assertIsNotNull(list, clazz, url)) {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(list));
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (map.get(ResultEnum.CODE.getVal()).toString().equals(ResultEnum.CODE_SUCCESS.getVal())) {
                hasSuccess = (boolean) map.get(ResultEnum.OPERFLAG.getVal());
            }
        }
        return hasSuccess;
    }

    @Override
    public boolean delObject(T o, Class clazz, String url) {
        boolean hasSuccess = false;
        if (CommonTool.assertIsNotNull(o, clazz, url)) {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(o));
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (map.get(ResultEnum.CODE.getVal()).toString().equals(ResultEnum.CODE_SUCCESS.getVal())) {
                hasSuccess = (boolean) map.get(ResultEnum.OPERFLAG.getVal());
            }
        }
        return hasSuccess;
    }

    @Override
    public boolean delById(Long id, Class clazz, String url) {
        boolean hasSuccess = false;
        if (CommonTool.assertIsNotNull(id, clazz, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.ID.getVal(), id.toString());
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (map.get(ResultEnum.CODE.getVal()).toString().equals(ResultEnum.CODE_SUCCESS.getVal())) {
                hasSuccess = (boolean) map.get(ResultEnum.OPERFLAG.getVal());
            }
        }
        return hasSuccess;
    }

    @Override
    public boolean delByParams(List<Param> params, Class clazz, String url) {
        boolean hasSuccess = false;
        if (CommonTool.assertIsNotNull(params, clazz, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(params));
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (map.get(ResultEnum.CODE.getVal()).equals(ResultEnum.CODE_SUCCESS.getVal())) {
                hasSuccess = (boolean) map.get(ResultEnum.OPERFLAG.getVal());
            }
        }
        return hasSuccess;
    }

    @Override
    public boolean updateObject(T t, Class clazz, String url) {
        boolean hasSuccess = false;
        if (CommonTool.assertIsNotNull(t, clazz, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(t));
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (map.get(ResultEnum.CODE.getVal()).toString().equals(ResultEnum.CODE_SUCCESS.getVal())) {
                hasSuccess = (boolean) map.get(ResultEnum.OPERFLAG.getVal());
            }
        }
        return hasSuccess;
    }

    @Override
    public T findById(Long id, Class clazz, String url) {
        T t = null;
        if (CommonTool.assertIsNotNull(id, clazz, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.ID.getVal(), id + "");
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (checkResult(map)) {
                t = (T) JSON.parseObject(map.get(ResultEnum.DATA.getVal()).toString(), clazz);
            }
        }
        return t;
    }

    @Override
    public List<T> getAll(Class clazz, String orderBy, Integer orderFlag, String url) {
        List list = new ArrayList<>();
        if (CommonTool.assertIsNotNull(clazz, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            requestEntity.add(ResultEnum.ORDERBY.getVal(), orderBy);
            requestEntity.add(ResultEnum.ORDERFLAG.getVal(), null == orderFlag ? orderFlag : orderFlag + "");
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (checkResult(map)) {
                list = JSON.parseArray(map.get(ResultEnum.DATA.getVal()).toString(), clazz);
            }
        }
        return list;
    }

    @Override
    public List<T> getListByParams(List<Param> params, Class clazz, String orderBy, Integer orderFlag, String url) {
        List<T> list = Collections.EMPTY_LIST;
        if (CommonTool.assertIsNotNull(params, clazz, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(params));
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            requestEntity.add(ResultEnum.ORDERBY.getVal(), orderBy);
            requestEntity.add(ResultEnum.ORDERFLAG.getVal(), null == orderFlag ? orderFlag : orderFlag.toString());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (checkResult(map)) {
                list = JSON.parseArray(map.get(ResultEnum.DATA.getVal()).toString(), clazz);
            }
        }
        return list;
    }

    @Override
    public T getOneByParams(List<Param> params, Class clazz, String url) {
        T t = null;
        if (CommonTool.assertIsNotNull(params, clazz, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(params));
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (checkResult(map)) {
                t = (T) JSON.parseObject(map.get(ResultEnum.DATA.getVal()).toString(), clazz);
            }
        }
        return t;
    }

    @Override
    public Page queryPage(List<Param> params, Page page, Class clazz, TypeReference typeReference, String orderBy, Integer orderFlag, String url) {
        Page pageData = new Page();
        if (CommonTool.assertIsNotNull(clazz, page, url)) {
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<>();
            if (null != params) {
                requestEntity.add(ResultEnum.JSONOBJECT.getVal(), JSON.toJSONString(params));
            }
            requestEntity.add(ResultEnum.CLASSNAME.getVal(), clazz.getName());
            requestEntity.add(ResultEnum.ORDERBY.getVal(), orderBy);
            requestEntity.add(ResultEnum.ORDERFLAG.getVal(), null == orderFlag ? orderFlag : orderFlag + "");
            requestEntity.add("page", JSON.toJSONString(page));
            Map map = new Post().getResultMap(ip + url, requestEntity);
            if (checkResult(map)) {
                pageData = (Page) JSON.parseObject(map.get(ResultEnum.DATA.getVal()).toString(), typeReference);
            }
        }
        return pageData;
    }


    public boolean checkResult(Map resultMap) {
        boolean result = false;
        if (resultMap.get(ResultEnum.CODE.getVal()).toString().equals(ResultEnum.CODE_SUCCESS.getVal())) {
            if (null != resultMap.get(ResultEnum.DATA.getVal())) {
                result = true;
            }
        }
        return result;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static String getIp() {
        return ReadTool.getInstance().getProperties("BaseServerIp");
    }

}
