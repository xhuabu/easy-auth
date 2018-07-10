package com.xhuabu.source.auth;

import com.xhuabu.source.domain.AdminDomain;
import com.xhuabu.source.domain.MenuDomain;
import com.xhuabu.source.model.po.*;
import com.xhuabu.source.model.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by jdk on 17/9/27.
 * 权限业务
 */
@Component
public class JLAuthCommonService {


    @Autowired
    private JLGroupService groupService;

    @Autowired
    private AdminDomain adminDomain;

    @Autowired
    private MenuDomain menuDomain;


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

    /**
     * 获取用户有权限访问的URI的Set
     *
     * @param userId
     * @return
     */
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

}
