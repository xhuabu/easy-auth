package com.xhuabu.source.model.vo;


import com.xhuabu.source.model.po.Menu;

import java.util.List;

/**
 * Created by jdk on 17/9/29.
 * 菜单VO
 */
public class MenuVO {

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    Menu menu;
    List<MenuVO> children;

}
