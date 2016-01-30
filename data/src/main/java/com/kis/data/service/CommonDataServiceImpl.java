package com.kis.data.service;

import com.kis.data.dao.CommonBaseDao;
import com.kis.data.model.HibernateTool;
import com.kis.data.model.Page;
import com.kis.data.model.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yks on 15-10-9.
 */
@Service("commonDataService")
public class CommonDataServiceImpl implements CommonDataService {
    private Logger logger = LoggerFactory.getLogger(CommonDataServiceImpl.class);
    @Autowired
    private CommonBaseDao commonDao;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public Object getByHql(String hql,Object...objects) {
        return commonDao.findByHql(hql,objects);
    }

    @Override
    @Transactional()
    public boolean addObject(Object o) {
        return commonDao.add(o);
    }
    @Override
    public boolean batchAddObject(List list){
        return commonDao.batchAdd(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public Object findById(Long id, Class clazz) {
        return commonDao.get(id, clazz);
    }
    @Override
    public boolean delObject(Object o) {
        return commonDao.delObject(o);
    }

    @Override
    public boolean delObjectById(Long id, Class clazz) {
        return commonDao.delObjectById(id, clazz);
    }

    @Override
    public boolean delObjectByParam(List<Param> params, Class clazz) {
        String hql = "delete from "+clazz.getName()+" t where ";
        for (Param param : params) {
            if(param.getOper().getOperVal().equals("in")){
                hql+="t."+param.getAttr()+" "+param.getOper().getOperVal()+" ("+param.getValue()+") and ";
            }else{
                hql+="t."+param.getAttr()+" "+param.getOper().getOperVal()+" '"+param.getValue()+"' and ";
            }
        }
        if(hql.trim().endsWith("and")){
            hql=hql.substring(0,hql.length()-4);
        }
        return commonDao.delByHql(hql);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public Object findOneByOneParam(String columnName, Object value, Class clazz) {
        String hql = "select t from "+clazz.getName()+" t where t."+columnName+"=?";
        return commonDao.findByHql(hql,value);
    }

    @Override
    public boolean updateObject(Object o,Class clazz) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().equals("id")){
                Type attrType = field.getGenericType();
                if(attrType.toString().endsWith("Long")){
                    try {
                        Long id= (Long) o.getClass().getMethod("getId").invoke(o);
                        Object data = findById(id,clazz);
                        o= HibernateTool.mergeObject(data,o);
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        return commonDao.updateObject(o);
    }

    @Override
    public boolean batchUpdateObject(Object[] objs, Class... clazzes) {
        boolean result = false;
        if(objs.length==clazzes.length) {
            for (int i = 0; i < objs.length; i++) {
                result = updateObject(objs[i],clazzes[i]);
                if(!result){
                    logger.error("批量更新错误");
                    break;
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public List getAll(Class clazz, String orderBy, Integer orderFlag) {
        return commonDao.getAll(clazz,orderBy,orderFlag);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public List getList(List<Param> params, Class clazz, String orderBy, Integer orderFlag) {
        return commonDao.getList(params,clazz,orderBy,orderFlag);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public Object findByParams(List<Param> params, Class clazz) {
        Object returnData = null;
        List listData = commonDao.getList(params,clazz,null,null);
        if(listData.size()>0) {
            returnData = listData.get(0);
            if (listData.size() > 1) {
                logger.warn("结果集大于1，数据不准确,"+params.toString()+",class:"+clazz);
                System.out.println("结果集大于1，数据不准确！");
            }
        }
        return returnData;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public Page queryPage(List<Param> params, Class clazz, String orderBy, Integer orderFlag, Page page) {
        return commonDao.queryPage(params,clazz,orderBy,orderFlag,page);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public Page queryPage(String hql,List<Param> params, Page page) {
        return commonDao.queryPage(hql,params,page);
    }

    @Override
    public int executeUpdateHql(String hql,Object...objects) {
        return commonDao.executeHqlByInt(hql,objects);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public List queryListByHql(String hql, Object... objects) {
        return commonDao.queryByHql(hql,objects);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = Throwable.class)
    public List queryListBySql(String sql, Object... objects) {
        return commonDao.queryBySql(sql,objects);
    }
}
