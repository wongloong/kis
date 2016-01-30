package com.kis.data.service;


import com.kis.data.model.Page;
import com.kis.data.model.Param;

import java.util.List;

/**
 * Created by yks on 15-10-9.
 */
public interface CommonDataService {
    /**
     * 添加
     * @param o ObjectType
     * @return boolean
     */
    boolean addObject(Object o);

    /**
     * 批量添加
     * @param o List<Object>
     * @return boolean
     */
    boolean batchAddObject(List o);

    /**
     * 根据id查找
     * @param id  Long
     * @param clazz User.class
     * @return  null or object
     */
    Object findById(Long id, Class clazz);

    /**
     * 根据一个条件查找出一个对象
     * @param columnName attr like: "loginNamej"
     * @param value    value  like: "admin" 类型一定要与columnName类型相同
     * @param clazz           like:  User.class
     * @return  null or object
     */
    Object findOneByOneParam(String columnName, Object value, Class clazz);

    /**
     * 删除实体
     * @param o
     * @return boolean;
     */
    boolean delObject(Object o);

    /**
     * 根据id删除
     * @param id LongType
     * @param clazz User.class
     * @return
     */
    boolean delObjectById(Long id, Class clazz);

    /**
     * 根据查询条件删除实体，可用做批量删除
     * @param parsms List<param>
     * @param clazz
     * @return
     */
    boolean delObjectByParam(List<Param> parsms, Class clazz);

    /**
     * 更新实体,只更新更改属性，不需更新属性请设置为null
     * @param o
     * @param clazz
     * @return
     */
    boolean updateObject(Object o, Class clazz);

    /**
     * 批量更新 同updateObject,批量操作,保证objs的长度与clazzes的长度相等。
     * @param objs new Object[]{user,userInfo}
     * @param clazzes like: User.class,UserInfo.class
     * @return boolean
     */
    boolean batchUpdateObject(Object[] objs, Class... clazzes);

    /**
     * 获取所有实体 排序。无查询条件
     * @param clazz
     * @param orderBy
     * @param orderFlag
     * @return
     */
    List getAll(Class clazz, String orderBy, Integer orderFlag);

    /**
     * 根据查询条件查询数据并排序
     * @param params List<Param>
     * @param clazz
     * @param orderBy 排序字段
     * @param orderFlag 排序方式 Integer
     * @return
     */
    List getList(List<Param> params, Class clazz, String orderBy, Integer orderFlag);

    /**
     * 根据查询条件查找一个实体
     * @param params List<Param>
     * @param clazz
     * @return
     */
    Object findByParams(List<Param> params, Class clazz);

    /**
     * 分页查找
     * @param params  查询条件
     * @param clazz
     * @param orderBy 排序
     * @param orderFlag 排序方式
     * @param page 分页信息
     * @return Page
     */
    Page queryPage(List<Param> params, Class clazz, String orderBy, Integer orderFlag, Page page);

    /**
     * 根据条件用hql排序
     * @param hql
     * @param params
     * @param page
     * @return
     */
    Page queryPage(String hql, List<Param> params, Page page);

    /**
     * 根据hql获得一个实体
     * @param hql
     * @param values
     * @return
     */
    Object getByHql(String hql, Object... values);

    /**
     * 执行hql，返回影响行数
     * @param hql
     * @param objects
     * @return int or 0
     */
    int executeUpdateHql(String hql, Object... objects);

    /**
     * 根据hql查询list
     * @param hql
     * @param objects
     * @return list
     */
    List queryListByHql(String hql, Object... objects);

    /**
     * 根据sql查询获得list
     * @param sql
     * @param objects
     * @return list
     */
    List queryListBySql(String sql, Object... objects);
}
