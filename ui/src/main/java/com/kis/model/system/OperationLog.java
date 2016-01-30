package com.kis.model.system;

import java.util.Date;

/**
 * Created by yks on 15-10-30.
 * 操作日志记录类
 */
public class OperationLog {
    private Long id;
    private String loginName;//登录名
    private Long userId;//操作人id
    private String ip;//ip
    private Date createDate;//创建时间
    private String operation;//具体操作

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }



    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", userId=" + userId +
                ", ip='" + ip + '\'' +
                ", createDate=" + createDate +
                ", operation='" + operation + '\'' +
                '}';
    }
}
