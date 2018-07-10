package com.xhuabu.source.auth;

import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.domain.GroupMenuDomain;
import com.xhuabu.source.domain.MenuDomain;
import com.xhuabu.source.model.po.GroupMenuExample;
import com.xhuabu.source.model.po.Menu;
import com.xhuabu.source.model.po.MenuExample;
import com.xhuabu.source.model.vo.MenuSetVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by vicky on 17/10/9.
 */
@Service
public class JLMenuServiceImpl implements JLMenuService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuDomain menuDomain;

    @Autowired
    private GroupMenuDomain groupMenuDomain;


    // 新增菜单
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer insertMenu(Integer parentId, String name, String uri, String comment, Integer createAdminId) throws AuthException {

        Menu menu = new Menu();
        menu.setParentId(parentId);
        menu.setName(name);
        menu.setUri(uri);
        menu.setComment(comment);
        menu.setCreateAdminId(createAdminId);
        return menuDomain.add(menu);
    }


    // 编辑菜单
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer updateMenu(Integer menuId, Integer parentId, String name, String uri, String comment) throws AuthException {

        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setParentId(parentId);
        menu.setName(name);
        menu.setUri(uri);
        menu.setComment(comment);
        return menuDomain.save(menu);

    }


    // 删除菜单
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteMenu(Integer menuId) throws AuthException {

        List<Integer> menuIds = new ArrayList<>();
        menuIds.add(menuId);

        // 1. 删除菜单 & 子菜单、组关系
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdEqualTo(menuId);
        List<Menu> menus = menuDomain.getAllByExample(menuExample);
        for (Menu menu : menus) {
            menuIds.add(menu.getId());
        }
        GroupMenuExample groupMenuExample = new GroupMenuExample();
        groupMenuExample.or().andMenuIdIn(menuIds);
        groupMenuDomain.deleteByExample(groupMenuExample);

        // 2. 删除所有子菜单
        menuDomain.deleteByExample(menuExample);

        // 3. 删除当前菜单
        Menu menu = new Menu();
        menu.setId(menuId);
        menuDomain.delete(menu);

        return 0;
    }


    // 菜单信息
    @Override
    public Menu getMenu(Integer menuId) {
        return menuDomain.get(menuId, true);
    }


    // 树形菜单列表
    @SuppressWarnings(value = "Duplicates")
    @Override
    public List<MenuSetVO> getMenuTree(Integer parentId, Set<MenuSetVO> menusSet) {

        List<MenuSetVO> result = new ArrayList<>();

        for (MenuSetVO menu : menusSet) {
            if (menu != null && (
                    (menu.getMenu().getParentId() == null && parentId == null)
                            || (menu.getMenu().getParentId() != null && menu.getMenu().getParentId().equals(parentId))
            )) {
                menu.setChildren(getMenuTree(menu.getMenu().getId(), menusSet));
                result.add(menu);
            }
        }

        //排序
        Collections.sort(result, (menuVO1, menuVO2) -> {
            int weight = menuVO1.getMenu().getWeight().compareTo(menuVO2.getMenu().getWeight());
            if (weight == 0) {
                return menuVO1.getMenu().getId().compareTo(menuVO2.getMenu().getId());
            }
            return weight;
        });

        return result;
    }


    // 菜单列表
    @Override
    public List<Menu> getMenus() {

        List<Menu> menus = new ArrayList<>();

        // 父菜单列表
        MenuExample menuExample = new MenuExample();
        menuExample.setOrderByClause("create_time DESC");
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdIsNull();
        List<Menu> parentMenus = menuDomain.getAllByExample(menuExample);

        for (Menu menu : parentMenus) {
            // 每个子菜单列表
            MenuExample subMenuExample = new MenuExample();
            subMenuExample.createCriteria().andParentIdEqualTo(menu.getId());
            List<Menu> subMenus = menuDomain.getAllByExample(subMenuExample);
            // 父菜单在前, 子菜单在后
            menus.add(menu);
            menus.addAll(subMenus);
        }

        return menus;
    }

}
