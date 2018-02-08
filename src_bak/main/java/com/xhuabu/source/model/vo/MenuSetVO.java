package com.xhuabu.source.model.vo;


import com.xhuabu.source.model.po.Menu;

import java.util.List;

/**
 * Created by vicky on 17/10/13.
 */
public class MenuSetVO {


    /**
     *  菜单
     */
    Menu menu;

    /**
     * 是否拥有该权限
     */
    Boolean have;

    /**
     * 子菜单
     */
    List<MenuSetVO> children;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Boolean getHave() {
        return have;
    }

    public void setHave(Boolean have) {
        this.have = have;
    }

    public List<MenuSetVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuSetVO> children) {
        this.children = children;
    }
}
