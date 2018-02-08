package com.xhuabu.source.auth;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.GroupMenuMapper;
import com.xhuabu.source.dao.MenuMapper;
import com.xhuabu.source.model.po.GroupMenuExample;
import com.xhuabu.source.model.po.Menu;
import com.xhuabu.source.model.po.MenuExample;
import com.xhuabu.source.model.vo.MenuSetVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private MenuMapper menuMapper;

    @Autowired
    private GroupMenuMapper groupMenuMapper;


    /**
     * 获取顶级菜单列表
     *
     * @return 顶级菜单列表
     */
    @Override
    public List<Menu> getListedMenu() {

        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdIsNull();
        return menuMapper.selectByExample(menuExample);
    }

    /**
     * 新增菜单
     *
     * @param parentId      父级菜单ID
     * @param name          菜单名
     * @param uri           菜单uri
     * @param comment       菜单备注
     * @param createAdminId 创建人ID
     * @return 1 成功 0 失败
     */
    @Override
    public int insertMenu(Integer parentId, String name, String uri, String comment, Integer createAdminId) {


        Menu menu = new Menu();
        menu.setParentId(parentId);
        menu.setName(name);
        menu.setUri(uri);
        menu.setComment(comment);
        menu.setCreateAdminId(createAdminId);
        return menuMapper.insertSelective(menu);

    }

    /**
     * 编辑菜单
     *
     * @param menuId   菜单ID
     * @param parentId 父级菜单ID
     * @param name     菜单名
     * @param uri      菜单uri
     * @param comment  菜单备注
     * @return 1 成功 0 失败
     */
    @Override
    @Transactional
    public int updateMenu(Integer menuId, Integer parentId, String name, String uri, String comment) {


        Menu menu = new Menu();
        menu.setParentId(parentId);
        menu.setName(name);
        menu.setUri(uri);
        menu.setComment(comment);
        menu.setId(menuId);
        return menuMapper.updateByPrimaryKeySelective(menu);

    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 1 成功 0 失败
     */
    @Override
    @Transactional
    public int deleteMenu(Integer menuId) {


        //如果是父菜单，则将其子菜单一起删除
        menuMapper.deleteByPrimaryKey(menuId);
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdEqualTo(menuId);
        //如果id是父菜单id，则获取所有子菜单id
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        //删除所有子菜单
        menuMapper.deleteByExample(menuExample);

        //将"组-菜单"的关联一并删除
        GroupMenuExample groupMenuExample = new GroupMenuExample();
        GroupMenuExample.Criteria criteria1 = groupMenuExample.createCriteria();
        criteria1.andMenuIdEqualTo(menuId);
        groupMenuMapper.deleteByExample(groupMenuExample);

        //如果子菜单存在，删除"组-子菜单"的关联
        List<Integer> ids = new ArrayList<Integer>();
        for (Menu menu : menus) {
            ids.add(menu.getId());
        }

        if (!ids.isEmpty()) {
            GroupMenuExample groupMenuExample1 = new GroupMenuExample();
            GroupMenuExample.Criteria criteria2 = groupMenuExample1.createCriteria();
            criteria2.andMenuIdIn(ids);
            groupMenuMapper.deleteByExample(groupMenuExample1);
        }

        return 1;
    }

    /**
     * 获取菜单
     *
     * @param menuId 菜单ID
     * @return 菜单实例
     */
    @Override
    public Menu getMenuById(Integer menuId) {

        return menuMapper.selectByPrimaryKey(menuId);
    }

    /**
     * 获取所有组的菜单列表
     *
     * @return 菜单列表
     */
    @Override
    public List<Menu> getAllMenu() {

        List<Menu> allMenus = new ArrayList<>();
        MenuExample menuExample = new MenuExample();
        menuExample.setOrderByClause("create_time DESC");
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdIsNull();
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        MenuExample subMenuExample;
        List<Menu> subMenus;
        for (Menu menu : menus) {
            subMenuExample = new MenuExample();
            subMenuExample.createCriteria().andParentIdEqualTo(menu.getId());
            subMenus = menuMapper.selectByExample(subMenuExample);
            allMenus.add(menu);
            allMenus.addAll(subMenus);
        }
        return allMenus;

    }


    /**
     * 判断菜单是否为父菜单
     *
     * @param menuId 菜单ID
     * @return true 是，false 不是
     */
    @Override
    public boolean isParentMenu(Integer menuId) {

        Menu menu = menuMapper.selectByPrimaryKey(menuId);
        Integer parentId = menu.getParentId();
        if (parentId == null) {
            logger.info("该菜单为父菜单~");
            return true;
        }
        return false;
    }

    /**
     * 获取树形菜单
     *
     * @param parentId 父级ID
     * @param menusSet 菜单集合
     * @return 具有层级关系的菜单集合
     */
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

    /**
     * 获取菜单列表
     *
     * @return 菜单分页列表
     */
    @Override
    public PageInfo<Menu> getListedMenu(Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<Menu> allMenus = new ArrayList<>();
        MenuExample menuExample = new MenuExample();
        menuExample.setOrderByClause("create_time DESC");
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdIsNull();
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        MenuExample subMenuExample;
        List<Menu> subMenus;
        for (Menu menu : menus) {
            subMenuExample = new MenuExample();
            subMenuExample.createCriteria().andParentIdEqualTo(menu.getId());
            subMenus = menuMapper.selectByExample(subMenuExample);
            allMenus.add(menu);
            allMenus.addAll(subMenus);
        }

        return new PageInfo<>(allMenus);

    }

}
