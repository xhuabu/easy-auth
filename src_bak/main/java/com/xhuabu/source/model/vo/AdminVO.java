package com.xhuabu.source.model.vo;


import com.xhuabu.source.model.po.Admin;

/**
 * Created by zzhoo8 on 2017/9/27.
 */
public class AdminVO {

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    Admin admin;

    Integer groupId;

    String groupName;

    public Integer getAdminGroupId() {
        return adminGroupId;
    }

    public void setAdminGroupId(Integer adminGroupId) {
        this.adminGroupId = adminGroupId;
    }

    Integer adminGroupId;



    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }



    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
