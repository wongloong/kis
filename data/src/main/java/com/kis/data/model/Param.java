package com.kis.data.model;

import com.kis.data.enums.ParamEnum;

/**
 * 查询参数封装,一般以list形式传递
 * Created by yks on 15-10-10.
 * List params = new LinkedList<>();
 * 三种构造函数：
 * 空构造函数-->new Param();
 * 全构造函数-->new Param("age","<","19");
 * attr/value构造函数-->new Param("username","yks"); oper默认为eq
 */
public class Param {
    /**
     * 实体类属性
     */
    private String attr;
    /**
     * 比较符，默认为=
     * > 大于
     * < 小于
     * in 此时value为逗号分隔：1,2,3,4
     * like
     */
    private ParamEnum oper=ParamEnum.EQ;
    /**
     * 实体类属性值
     */
    private Object value;

    public Param() {
    }

    public Param(String attr, ParamEnum oper, Object value) {
        this.attr = attr;
        this.oper = oper;
        this.value = value;
    }

    public Param(String attr, Object value) {
        this.attr = attr;
        this.value = value;
    }


    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ParamEnum getOper() {
        return oper;
    }

    public void setOper(ParamEnum oper) {
        this.oper = oper;
    }

    @Override
    public String toString() {
        return "Param{" +
                "attr='" + attr + '\'' +
                ", oper=" + oper +
                ", value='" + value + '\'' +
                '}';
    }
}
