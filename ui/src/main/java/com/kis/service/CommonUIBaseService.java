package com.kis.service;

import com.alibaba.fastjson.TypeReference;
import com.kis.model.system.Page;
import com.kis.model.system.Param;

import java.util.List;

/**
 * 基础service类，所有service继承此类。
 * Created by yks on 15-10-09.
 *
 * @param <T> the type parameter
 */
public interface CommonUIBaseService<T> {
    /**
     * 添加实体类
     *
     * @param o     要添加的实体类
     * @param clazz 实体类.class
     * @return boolean 是否操作成功 eg:User user = new User(); baseService.add(user,User.class,null);
     */
    boolean add(Object o, Class clazz);
    boolean add(Object o, Class clazz, String url);

    /**
     * 批量添加实体类
     *
     * @param list  实体类集合
     * @param clazz 实体类.class
     * @return boolean 是否操作成功
     */
    boolean batchAdd(List<T> list, Class clazz);
    boolean batchAdd(List<T> list, Class clazz, String url);

    /**
     * 删除实体类
     *
     * @param t     实体类
     * @param clazz 实体类.class
     * @return boolean 是否操作成功
     */
    boolean delObject(T t, Class clazz);
    boolean delObject(T t, Class clazz, String url);

    /**
     * 根据id删除实体类
     *
     * @param id    实体id。Long
     * @param clazz 实体类.class
     * @return boolean 是否操作成功
     */
    boolean delById(Long id, Class clazz);
    boolean delById(Long id, Class clazz, String url);

    /**
     * 根据params条件删除实体
     *
     * @param params List<Param> @see com.kis.model.system.Param
     * @param clazz  实体类.class
     * @return boolean 是否操作成功
     */
    boolean delByParams(List<Param> params, Class clazz);
    boolean delByParams(List<Param> params, Class clazz, String url);

    /**
     * 更新
     *
     * @param t     实体类
     * @param clazz 实体类.class
     * @return boolean 操作是否成功 TODO 只更新部分字段未测试是否为null. eg: User User = (User)findById(1L,User.class,null); User.setUserName("test"); updateObject(user)
     */
    boolean updateObject(T t, Class clazz);
    boolean updateObject(T t, Class clazz, String url);

    /**
     * 根据Id查找实体类
     *
     * @param id    the id
     * @param clazz 实体类.class;
     * @return Object 调用需强制转换 eg:findById(1L,User.class,null)
     */
    T findById(Long id, Class clazz);
    T findById(Long id, Class clazz, String url);

    /**
     * 获取某一实体的全部对象,主要用于select-->option
     *
     * @param clazz     实体类.class
     * @param orderBy   实体类属性 单一排序，不允许多个属性
     * @param orderFlag 排序规则 Integer: <0:desc & >=0:asc
     * @return eg :getAll(User.class,"id",1); eg:getAll(User.class,null,null,null);
     */
    List<T> getAll(Class clazz, String orderBy, Integer orderFlag);
    List<T> getAll(Class clazz, String orderBy, Integer orderFlag, String url);

    /**
     * 根据查询条件获取List
     *
     * @param params    List<Param> @see com.kis.model.system.Param
     * @param clazz     eg:User.class
     * @param orderBy   实体类属性 单一排序，不允许多个属性
     * @param orderFlag 排序标识 Integer: <0:desc & >=0:asc
     * @return List eg: List<Params> params = new LinkedList<>(); params.add(new Param("username","test")); getListByParams(params,User.class,"id",1,null);
     */
    List<T> getListByParams(List<Param> params, Class clazz, String orderBy, Integer orderFlag);
    List<T> getListByParams(List<Param> params, Class clazz, String orderBy, Integer orderFlag, String url);

    /**
     * 根据条件查询一个实体
     *
     * @param params @see com.kis.model.system.Param
     * @param clazz  实体类.class
     * @return Object one by params
     */
    T getOneByParams(List<Param> params, Class clazz);
    T getOneByParams(List<Param> params, Class clazz, String url);

    /**
     * 分页查询
     *
     * @param params        @see com.kis.model.system.Param
     * @param page          @see com.kis.model.system.Page
     * @param clazz         要查询的实体例如 User.class
     * @param typeReference @see com.kis.model.system.Page#List<T>
     * @param orderBy       实体类属性 单一排序，不允许多个属性
     * @param orderFlag     排序规则 Integer: <0:desc & >=0:asc
     * @return Page <p> eg: List<Param> params= new ArrayList<>();<br>
     *     params.add(new Param("username","admin")); <br>Page page = new Page(); <br>page.setCurrentPage(1);<br>
     *     TypeReference typeReference=new TypeReference&ltPage&ltUser&gt&gt(){};<br>
     *     List<User> userList= baseService.queryPage(params,page,User.class,typeReference,null,null,null).getList();
     */
    Page queryPage(List<Param> params, Page page, Class clazz, TypeReference typeReference, String orderBy, Integer orderFlag);
    Page queryPage(List<Param> params, Page page, Class clazz, TypeReference typeReference, String orderBy, Integer orderFlag, String url);
}
