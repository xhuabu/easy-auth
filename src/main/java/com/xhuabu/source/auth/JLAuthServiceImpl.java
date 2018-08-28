package com.xhuabu.source.auth;

import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.domain.AdminDomain;
import com.xhuabu.source.domain.AuthDomain;
import com.xhuabu.source.domain.GroupAuthDomain;
import com.xhuabu.source.domain.MenuDomain;
import com.xhuabu.source.model.po.*;
import com.xhuabu.source.model.vo.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jdk on 17/9/27.
 * 权限业务
 */
@Component
public class JLAuthServiceImpl implements JLAuthService {


    @Autowired
    private JLGroupService groupService;

    @Autowired
    private AdminDomain adminDomain;

    @Autowired
    private MenuDomain menuDomain;

    @Autowired
    private AuthDomain authDomain;

    @Autowired
    private GroupAuthDomain groupAuthDomain;


    // 权限信息
    @Override
    public Auth getAuth(Integer id) {
        return authDomain.get(id, true);
    }


    // 权限列表
    @Override
    public PageInfo<Auth> getAuths(Integer groupId, String name, String uri, Integer page, Integer size) {
        AuthExample example = new AuthExample();
        AuthExample.Criteria criteria = example.createCriteria();

        List<String> authUris = new ArrayList<>(0);
        if (groupId != null) {
            GroupAuthExample groupAuthExample = new GroupAuthExample();
            GroupAuthExample.Criteria criteria1 = groupAuthExample.createCriteria();
            criteria1.andGroupIdEqualTo(groupId);
            PageInfo<GroupAuth> groupAuthPageInfo = groupAuthDomain.getsByExample(groupAuthExample, page, size);
            for (GroupAuth groupAuth : groupAuthPageInfo.getList()) {
                authUris.add(groupAuth.getUri());
            }
        }

        if (authUris.size() != 0) {
            criteria.andUriIn(authUris);
        }

        if (StringUtils.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (StringUtils.isNotEmpty(uri)) {
            criteria.andUriLike("%" + uri + "%");
        }

        if (StringUtils.isEmpty(example.getOrderByClause())) {
            example.setOrderByClause("create_time desc");
        }
        return authDomain.getsByExample(example, page, size);
    }


    // 增加权限
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer addAuth(String name, String uri, Integer adminId) throws AuthException {
        Auth auth = new Auth();
        auth.setName(name);
        auth.setUri(uri);
        auth.setCreateAdminId(adminId);
        return authDomain.add(auth);
    }


    // 更新权限
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer updateAuth(Integer id, String name, String uri) throws AuthException {
        Auth auth = authDomain.get(id, true);

        // 更新组、权限列表
        GroupAuthExample groupAuthExample = new GroupAuthExample();
        GroupAuthExample.Criteria criteria = groupAuthExample.createCriteria();
        criteria.andUriEqualTo(auth.getUri());
        List<GroupAuth> groupAuths = groupAuthDomain.getAllByExample(groupAuthExample);
        for (GroupAuth groupAuth : groupAuths) {
            groupAuth.setUri(uri);
            groupAuthDomain.save(groupAuth);
        }

        // 更新权限信息
        auth.setName(name);
        auth.setUri(uri);
        authDomain.save(auth);
        return 0;
    }


    // 删除权限
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteAuth(Integer id) throws AuthException {
        Auth auth = authDomain.get(id, true);

        // 删除组、权限列表
        GroupAuthExample groupAuthExample = new GroupAuthExample();
        GroupAuthExample.Criteria criteria = groupAuthExample.createCriteria();
        criteria.andUriEqualTo(auth.getUri());
        groupAuthDomain.deleteByExample(groupAuthExample);

        // 删除权限信息
        authDomain.delete(auth);
        return 0;
    }


    // 获取用户有权限访问的URI的Set
    @Override
    public JLAuthBean getAvailableUriByUserId(Integer userId) {

        JLAuthBean JLAuthBean = new JLAuthBean();

        Admin admin = adminDomain.get(userId, true);

        JLAuthBean.setUserId(userId);
        JLAuthBean.setAdminNickName(admin.getNickname());

        //得到用户组
        List<AdminGroup> adminGroupList = new ArrayList<>();
        AdminGroup adminGroup = groupService.getAdminGroup(userId);
        adminGroupList.add(adminGroup);

        Set<String> uriSet = new HashSet<>();

        Set<Menu> menuSet = new HashSet<>();

        for (AdminGroup ag : adminGroupList) {
            Integer groupId = ag.getGroupId();

            //查对应的 uri
            List<GroupAuth> groupAuthList = groupService.getGroupAuths(groupId);
            for (GroupAuth groupAuth : groupAuthList) {
//                Auth auth = authService.getAuthById(groupAuth.getAuthId());
                uriSet.add(groupAuth.getUri());
            }

            //查对应的 menus
            List<GroupMenu> groupMenuList = groupService.getGroupMenus(groupId);
            for (GroupMenu groupMenu : groupMenuList) {
                Menu menu = menuDomain.get(groupMenu.getMenuId(), true);
                menuSet.add(menu);
            }
        }
        JLAuthBean.setUriSet(uriSet);
        JLAuthBean.setMenuVOList(getMenuTree(null, menuSet));

        return JLAuthBean;
    }


    @SuppressWarnings(value = "Duplicates")
    private List<MenuVO> getMenuTree(Integer parentId, Set<Menu> menusSet) {
        List<MenuVO> result = new ArrayList<>();

        for (Menu menu : menusSet) {
            if (menu != null && (
                    (menu.getParentId() == null && parentId == null)
                            || (menu.getParentId() != null && menu.getParentId().equals(parentId))
            )) {
                MenuVO menuVO = new MenuVO();
                menuVO.setMenu(menu);
                menuVO.setChildren(getMenuTree(menu.getId(), menusSet));
                result.add(menuVO);
            }
        }

        //返回之前..排个序
        Collections.sort(result, (menuVO1, menuVO2) -> {
            int weight = menuVO1.getMenu().getWeight().compareTo(menuVO2.getMenu().getWeight());
            if (weight == 0) {
                return menuVO1.getMenu().getId().compareTo(menuVO2.getMenu().getId());
            }
            return weight;
        });

        return result;
    }

}
