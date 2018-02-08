package com.xhuabu.source.auth;


import com.xhuabu.source.model.vo.MenuVO;

import java.util.List;
import java.util.Set;


public class JLAuthBean {

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 管理员昵称
     */
    private String adminNickName;

    /**
     * 用户有权限的Uri集合
     */
    private Set<String> uriSet;

    /**
     * 用户可见的菜单集合
     */
    private List<MenuVO> menuVOList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAdminNickName() {
        return adminNickName;
    }

    public void setAdminNickName(String adminNickName) {
        this.adminNickName = adminNickName;
    }

    public Set<String> getUriSet() {
        return uriSet;
    }

    public void setUriSet(Set<String> uriSet) {
        this.uriSet = uriSet;
    }

    public List<MenuVO> getMenuVOList() {
        return menuVOList;
    }

    public void setMenuVOList(List<MenuVO> menuVOList) {
        this.menuVOList = menuVOList;
    }
}
