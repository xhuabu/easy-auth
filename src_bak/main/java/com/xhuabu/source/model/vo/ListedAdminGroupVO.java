package com.xhuabu.source.model.vo;

import java.util.Date;

/**
 * Created by vicky on 17/10/9.
 * 组的成员列表
 */
public class ListedAdminGroupVO {

    private Integer adminId;
    private Integer adminGroupId;
    private String nickName;
    private String userName;
    private Integer status;
    private Date updateTime;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAdminGroupId() {
        return adminGroupId;
    }

    public void setAdminGroupId(Integer adminGroupId) {
        this.adminGroupId = adminGroupId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ListedAdminGroupVO{" +
                "adminId=" + adminId +
                ", adminGroupId=" + adminGroupId +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", status=" + status +
                ", updateTime=" + updateTime +
                '}';
    }
}
