package com.xhuabu.source.model.vo;


import com.xhuabu.source.model.po.Group;

/**
 * Created by jdk on 17/9/29.
 */
public class GroupVO {

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getCreaterPhone() {
        return createrPhone;
    }

    public void setCreaterPhone(String createrPhone) {
        this.createrPhone = createrPhone;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroups(Group groups) {
        this.group = groups;
    }

    /**
     * 组
     */
    Group group;

    /**
     * 创建者名
     */
    String createrName;

    /**
     * 创建者手机号
     */
    String createrPhone;



}
