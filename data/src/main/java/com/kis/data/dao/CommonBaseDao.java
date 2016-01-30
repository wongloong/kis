package com.kis.data.dao;

import com.kis.data.model.Page;
import com.kis.data.model.Param;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by yks on 15-10-9.
 */
@Repository("commonBaseDao")
public class CommonBaseDao {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CommonBaseDao.class);
    @Resource
    private SessionFactory sessionFactory;

    /**
     * 添加实体类
     * @param o object
     * @return true or false
     */
    public boolean add(Object o) {
        boolean result = true;
        try {
            sessionFactory.getCurrentSession().save(o);
        } catch (Exception e) {
            result = false;
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 批量添加 20个提交一次
     * @param list
     * @return boolean
     */
    public boolean batchAdd(List list) {
        boolean result = true;
        Session session = sessionFactory.getCurrentSession();
        try {
            for (int i = 0; i < list.size(); i++) {
                session.save(list.get(i));
                if (i > 0 && i % 20 == 0) {
                    session.flush();
                    session.clear();
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 根据id查找
     * @param id
     * @param clazz
     * @return clazz or null
     */
    public Object load(Long id, Class clazz) {
        Object o = null;
        o = sessionFactory.getCurrentSession().load(clazz, id);
        return o;
    }

    /**
     * 根据id查找
     * @param id
     * @param clazz
     * @return clazz or null
     */
    public Object get(Long id, Class clazz) {
        Object o = null;
        o = sessionFactory.getCurrentSession().get(clazz, id);
        return o;
    }

    /**
     * 删除object
     * @param o
     * @return boolean
     */
    public boolean delObject(Object o) {
        boolean result = true;
        try {
            sessionFactory.getCurrentSession().delete(o);
        } catch (Exception e) {
            result = false;
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 根据id删除实体
     * @param id
     * @param clazz
     * @return boolean
     */
    public boolean delObjectById(Long id, Class clazz) {
        boolean result = false;
        try {
            sessionFactory.getCurrentSession().delete(get(id, clazz));
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 根据hql删除实体
     * @param hql
     * @param objects
     * @return boolean
     */
    public boolean delByHql(String hql, Object... objects) {
        boolean result = true;
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            if (null != objects) {
                for (int i = 0; i < objects.length; i++) {
                    query.setParameter(i, objects[i]);
                }
            }
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            result=false;
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 更新一个实体
     * @param o
     * @return boolean
     */
    public boolean updateObject(Object o) {
        boolean result = true;
        try {
            sessionFactory.getCurrentSession().merge(o);
        } catch (Exception e) {
            result = false;
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }


    /**
     * 查询全部实体
     * @param clazz
     * @param orderBy
     * @param orderFlag
     * @return list
     */
    public List getAll(Class clazz, String orderBy, Integer orderFlag) {
        List data = Collections.emptyList();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz).setCacheable(true);
        if (StringUtils.isNotBlank(orderBy)) {
            if (null != orderFlag && orderFlag < 0) {
                criteria.addOrder(Order.desc(orderBy));
            } else {
                criteria.addOrder(Order.asc(orderBy));
            }
        }
        data = criteria.list();
        return data;
    }

    /**
     * 根据条件查询list
     * @param params
     * @param clazz
     * @param orderBy
     * @param orderFlag
     * @return list
     */
    public List getList(List<Param> params, Class clazz, String orderBy, Integer orderFlag) {
        List data = Collections.emptyList();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz).setCacheable(true);
        data = assemCriteria(params, criteria, orderBy, orderFlag, clazz).list();
        return data;
    }

    /**
     * 根据hql获取list
     * @param hql
     * @param objects
     * @return list
     */
    public List getByHql(String hql, Object... objects) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (null != objects) {
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i, objects[i]);
            }
        }
        return query.setCacheable(true).list();
    }

    /**
     * 根据hql查找一个实体
     * @param hql
     * @param objects
     * @return object or null
     */
    public Object findByHql(String hql, Object... objects) {
        Object o = null;
        List list = getByHql(hql, objects);
        if (null != list && list.size() > 0) {
            o = list.get(0);
            if (list.size() > 1) {
                logger.error("查询结果多余一个，结果不准确，hql:" + hql);
            }
        }
        return o;
    }

    /**
     * 执行hql 返回影响行数
     * @param hql
     * @param objects
     * @return int
     */
    public int executeHqlByInt(String hql, Object... objects) {
        int result = 0;
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
        if (null != objects) {
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i, objects[i]);
            }
        }
        result = query.executeUpdate();
        return result;
    }

    /**
     * 执行hql
     * @param hql
     * @param objects
     * @return boolean
     */
    public boolean executeHql(String hql, Object... objects) {
        boolean result = true;
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
        if (null != objects) {
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i, objects[i]);
            }
        }
        try {
            query.executeUpdate();
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = false;
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 根据参数分页查询
     * @param params
     * @param clazz
     * @param orderBy
     * @param orderFlag
     * @param page
     * @return Page
     */
    public Page queryPage(List<Param> params, Class clazz, String orderBy, Integer orderFlag, Page page) {
        Page pageData = new Page();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz).setCacheable(true);
        criteria = assemCriteria(params, criteria, orderBy, orderFlag, clazz);
        long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        criteria.setFirstResult((page.getCurrentPage() - 1) * page.getSize());
        criteria.setMaxResults(page.getSize());
        List data = criteria.list();
        pageData.setList(data);
        pageData.setCurrentPage(page.getCurrentPage());
        pageData.setTotals(rowCount);
        return pageData;
    }

    /**
     * 根据hql分页查询
     * @param hql
     * @param params
     * @param page
     * @return
     */
    public Page queryPage(String hql, List<Param> params, Page page) {
        Page pageData = new Page();
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        for (Param param : params) {
            if (param.getAttr().indexOf(".") > -1) {
                query.setParameter(param.getAttr().split("\\.")[1], param.getValue());
            }
        }
        query.setFirstResult((page.getCurrentPage() - 1) * page.getSize());
        query.setMaxResults(page.getSize());
        query.setCacheable(true);
        List data = query.list();
        pageData.setList(data);
        pageData.setCurrentPage(page.getCurrentPage());
        pageData.setTotals(getTotals(hql, params));
        return pageData;
    }

    /**
     * 根据hql获取共有多少数据
     * @param hql
     * @param params
     * @return long
     */
    public Long getTotals(String hql, List<Param> params) {
        String rowCountHql = "select count(*) " + hql.substring(hql.indexOf("from"));
        Query query = sessionFactory.getCurrentSession().createQuery(rowCountHql).setCacheable(true);
        for (Param param : params) {
            if (param.getAttr().indexOf(".") > -1) {
                query.setParameter(param.getAttr().split("\\.")[1], param.getValue());
            }
        }
        return (Long) query.uniqueResult();
    }

    /**
     * 组装查询条件
     * @param params
     * @param criteria
     * @param orderBy
     * @param orderFlag
     * @param clazz
     * @return Criteria
     */
    public Criteria assemCriteria(List<Param> params, Criteria criteria, String orderBy, Integer orderFlag, Class clazz) {
        for (Param p : params) {
            switch (p.getOper().getOperVal()) {
                case ">":
                    criteria.add(Restrictions.gt(p.getAttr(), getVal(p, clazz)));
                    break;
                case "<":
                    criteria.add(Restrictions.lt(p.getAttr(), getVal(p, clazz)));
                    break;
                case "like":
                    criteria.add(Restrictions.ilike(p.getAttr(), getVal(p, clazz)));
                    break;
                case "in":
                    String[] values = p.getValue().toString().split(",");
                    criteria.add(Restrictions.in(p.getAttr(), getValForIn(p, values, clazz)));
                    break;
                default:
                    criteria.add(Restrictions.eq(p.getAttr(), getVal(p, clazz)));
            }
        }
        if (StringUtils.isNotBlank(orderBy)) {
            if (null != orderBy && orderFlag < 0) {
                criteria.addOrder(Order.desc(orderBy));
            } else {
                criteria.addOrder(Order.asc(orderBy));
            }
        }
        return criteria;
    }

    public Object getVal(Param p, Class clazz) {
        Object val = null;
        try {
            Field field = clazz.getDeclaredField(p.getAttr());
            Class type = field.getType();
            if (type.getTypeName().endsWith("Long")) {
                val = Long.parseLong(p.getValue().toString());
            } else if (type.getTypeName().toLowerCase().endsWith("integer")) {
                val = Integer.parseInt(p.getValue().toString());

            } else if (type.getTypeName().toLowerCase().endsWith("date")) {
                val = new Date(Long.parseLong(p.getValue().toString()));
            } else {
                val = p.getValue();
            }
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return val;
    }

    public Object[] getValForIn(Param p, String[] ss, Class clazz) {
        Object[] val = null;
        try {
            Field field = clazz.getDeclaredField(p.getAttr());
            Class type = field.getType();
            if (type.getTypeName().endsWith("Long")) {
                List<Long> list = new LinkedList<>();
                Arrays.asList(ss).stream().forEach(s -> list.add(Long.parseLong(s)));
                val = list.toArray();
            } else if (type.getTypeName().endsWith("Integer")) {
                List<Integer> list = new LinkedList<>();
                Arrays.asList(ss).stream().forEach(s -> list.add(Integer.parseInt(s)));
                val = list.toArray();
            } else {
                val = ss;
            }
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return val;
    }
    public List queryByHql(String hql,Object...objects){
        List list = Collections.EMPTY_LIST;
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true);
        if (null != objects) {
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i, objects[i]);
            }
        }
        list = query.list();
        return list;
    }
    public List queryBySql(String sql,Object...objects){
        List list = Collections.EMPTY_LIST;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setCacheable(true);
        if (null != objects) {
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i, objects[i]);
            }
        }
        list = query.list();
        return list;
    }
}
