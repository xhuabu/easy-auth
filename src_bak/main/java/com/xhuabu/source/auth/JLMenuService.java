package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.model.po.Menu;
import com.xhuabu.source.model.vo.MenuSetVO;

import java.util.List;
import java.util.Set;

/**
 * Created by vicky on 17/10/9.
 */
public interface JLMenuService {



    /**
     * 获取顶级菜单列表
     * @return 顶级菜单列表
     */
    List<Menu> getListedMenu();

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
    int insertMenu(Integer parentId, String name, String uri, String comment, Integer createAdminId);

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
    int updateMenu(Integer menuId, Integer parentId, String name, String uri, String comment);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 1 成功 0 失败
     */
    int deleteMenu(Integer menuId);

    /**
     * 获取菜单
     *
     * @param menuId  菜单ID
     * @return 菜单实例
     */
    Menu getMenuById(Integer menuId);

    /**
     * 获取所有组的菜单列表
     * @return 菜单列表
     */
    List<Menu> getAllMenu();


    /**
     * 判断菜单是否为父菜单
     *
     * @param menuId 菜单ID
     * @return true 是，false 不是
     */
    boolean isParentMenu(Integer menuId);

    /**
     * 获取树形菜单
     * @param parentId 父级ID
     * @param menusSet 菜单集合
     * @return 具有层级关系的菜单集合
     */
    List<MenuSetVO> getMenuTree(Integer parentId, Set<MenuSetVO> menusSet);

    /**
     * 获取菜单列表
     *
     * @return 菜单分页列表
     */
    PageInfo<Menu> getListedMenu(Integer pageNo, Integer pageSize);

}
