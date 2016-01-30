package com.kis.data.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yks on 15-11-5.
 */
public class HibernateTool {
    private static Logger logger = LoggerFactory.getLogger(HibernateTool.class);

    public static String createHql(Class a, Class b, String aKey, String bKey, List<Param> paramList, String orderBy, Integer orderFlag) {
        StringBuffer hql = new StringBuffer();
        hql.append("select a,b from ").append(a.getName()).append(" a,").append(b.getName()).append(" b where a.");
        hql.append(aKey).append("=b.").append(bKey);
        for (Param param : paramList) {
            if (param.getAttr().indexOf(".") > -1) {
                hql.append(" and ").append(param.getAttr()).append(" ").append(param.getOper().getOperVal());
                hql.append(":").append(param.getAttr().split("\\.")[1]);
            }
        }
        if (StringUtils.isNotBlank(orderBy)) {
            Field[] fields = a.getDeclaredFields();
            boolean hasField = false;
            for (Field field : fields) {
                if (field.getName().equals(orderBy)) {
                    hql.append(" order by ").append("a.").append(orderBy);
                    hasField = true;
                    break;
                }
            }
            if (!hasField) {
                fields = b.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals(orderBy)) {
                        hql.append(" order by ").append("b.").append(orderBy);
                        break;
                    }
                }
            }
            if (null != orderFlag) {
                if (orderFlag < 0) {
                    hql.append(" desc");
                } else {
                    hql.append(" asc");
                }
            } else {
                hql.append(" asc");
            }
        }
        return hql.toString();
    }

    public static Object mergeObject(Object dataObject, Object formObject) {
        Field[] fields = formObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            String attrName = field.getName();
            Type attrType = field.getGenericType();
            try {
                if (attrType.toString().endsWith("String")) {
                    String formValue = (String) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(formObject);
                    if (null != formValue) {
                        String dataValue = (String) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(dataObject);
                        if (null == dataValue || !formValue.equals(dataValue)) {
                            field.setAccessible(true);
                            field.set(dataObject, formValue);
                        }
                    }
                } else if (attrType.toString().endsWith("Double")) {
                    Double formValue = (Double) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(formObject);
                    if (null != formValue) {
                        Double dataValue = (Double) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(dataObject);
                        if (null == dataValue || !formValue.equals(dataValue)) {
                            field.setAccessible(true);
                            field.set(dataObject, formValue);
                        }
                    }
                } else if (attrType.toString().endsWith("Integer")) {
                    Integer formValue = (Integer) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(formObject);
                    if (null != formValue) {
                        Integer dataValue = (Integer) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(dataObject);
                        if (null == dataValue || !formValue.equals(dataValue)) {
                            field.setAccessible(true);
                            field.set(dataObject, formValue);
                        }
                    }
                } else if (attrType.toString().endsWith("Long")) {
                    Long formValue = (Long) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(formObject);
                    if (null != formValue) {
                        Long dataValue = (Long) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(dataObject);
                        if (null == dataValue || !formValue.equals(dataValue)) {
                            field.setAccessible(true);
                            field.set(dataObject, formValue);
                        }
                    }
                } else if (attrType.toString().endsWith("Date")) {
                    Date formValue = (Date) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(formObject);
                    if (null != formValue) {
                        Date dataValue = (Date) formObject.getClass().getMethod("get" + getMethodName(attrName)).invoke(dataObject);
                        if (null == dataValue || !formValue.equals(dataValue)) {
                            field.setAccessible(true);
                            field.set(dataObject, formValue);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataObject;
    }


    public static String getMethodName(String attrName) {
        return attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    }

    /**
     * 设置关联表数据
     *
     * @param data      根据hql查询分页的list
     * @param fieldName 关联表属性，一般为标注@Transition
     * @return List
     */
    public static List setChildrenData(List data, Class parentClass, String fieldName) {
        List result = new ArrayList<>();
        try {
            Field field = parentClass.getDeclaredField(fieldName);
            for (Object o : data) {
                Object[] dataArray = (Object[]) o;
                Object parentObject = dataArray[0];
                if(null!=field){
                    field.setAccessible(true);
                    field.set(parentObject,dataArray[1]);
                    result.add(parentObject);
                }
            }
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
