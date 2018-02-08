package com.xhuabu.source.model.vo;

import java.util.Date;

/**
 * Created by vicky on 17/9/28.
 * 管理员列表
 */
public class ListedAdminVO {

    private Integer id;
    private String userName;
    private String phone;
    private String groupName;
    private Integer status;
    private Date lastTime;
    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "ListedAdminVO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", groupName='" + groupName + '\'' +
                ", status=" + status +
                ", lastTime=" + lastTime +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
