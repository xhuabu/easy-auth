package com.xhuabu.source.auth;


import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.model.po.Menu;
import com.xhuabu.source.model.vo.MenuSetVO;

import java.util.List;
import java.util.Set;

/**
 * Created by vicky on 17/10/9.
 */
public interface JLMenuService {

    /**
     * 新增菜单
     *
     * @param parentId 父级菜单ID
     * @param name 菜单名
     * @param uri 菜单uri
     * @param comment 菜单备注
     * @param createAdminId 创建人ID
     * @return 1 成功 0 失败
     */
    Integer addMenu(Integer parentId, String name, String uri, String comment, Integer createAdminId) throws AuthException;

    /**
     * 编辑菜单
     *
     * @param menuId 菜单ID
     * @param parentId 父级菜单ID
     * @param name 菜单名
     * @param uri 菜单uri
     * @param comment 菜单备注
     * @return 1 成功 0 失败
     */
    Integer updateMenu(Integer menuId, Integer parentId, String name, String uri, String comment) throws AuthException;

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 1 成功 0 失败
     */
    Integer deleteMenu(Integer menuId) throws AuthException;

    /**
     * 获取菜单
     *
     * @param menuId  菜单ID
     * @return 菜单实例
     */
    Menu getMenu(Integer menuId);

    /**
     * 树形菜单
     * @param parentId 父级ID
     * @param menusSet 菜单集合
     * @return 具有层级关系的菜单集合
     */
    List<MenuSetVO> getMenuTree(Integer parentId, Set<MenuSetVO> menusSet);

    /**
     * 菜单列表
     *
     * @return 菜单分页列表
     */
    List<Menu> getMenus(Integer groupId);

}
