package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.domain.AdminGroupDomain;
import com.xhuabu.source.domain.GroupAuthDomain;
import com.xhuabu.source.domain.GroupDomain;
import com.xhuabu.source.domain.GroupMenuDomain;
import com.xhuabu.source.model.po.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by vicky on 17/10/8.
 */
@Service
public class JLGroupServiceImpl implements JLGroupService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JLAuthManager authManager;

    @Autowired
    private GroupDomain groupDomain;

    @Autowired
    private GroupMenuDomain groupMenuDomain;

    @Autowired
    private GroupAuthDomain groupAuthDomain;

    @Autowired
    private AdminGroupDomain adminGroupDomain;


    // 新增群組
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer insertGroup(String name, String comment, Integer createAdminId) throws AuthException {
        Group group = new Group();
        group.setName(name);
        group.setComment(comment);
        group.setCreateAdminId(createAdminId);
        return groupDomain.add(group);
    }


    // 编辑群組
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer updateGroup(Integer groupId, String name) throws AuthException {
        Group group = new Group();
        group.setName(name);
        group.setId(groupId);
        return groupDomain.save(group);
    }


    // 删除群组
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteGroup(Integer groupId) throws AuthException {

        try {
            //删除组
            Group group = new Group();
            group.setId(groupId);
            groupDomain.delete(group);

            // 删除组、菜单关系
            GroupMenuExample groupMenuExample = new GroupMenuExample();
            GroupMenuExample.Criteria criteria1 = groupMenuExample.createCriteria();
            criteria1.andGroupIdEqualTo(groupId);
            groupMenuDomain.deleteByExample(groupMenuExample);

            // 删除组、权限关系
            GroupAuthExample groupAuthExample = new GroupAuthExample();
            GroupAuthExample.Criteria criteria2 = groupAuthExample.createCriteria();
            criteria2.andGroupIdEqualTo(groupId);
            groupAuthDomain.deleteByExample(groupAuthExample);

            //删除组、管理员关系
            AdminGroupExample adminGroupExample = new AdminGroupExample();
            AdminGroupExample.Criteria criteria3 = adminGroupExample.createCriteria();
            criteria3.andGroupIdEqualTo(groupId);
            adminGroupDomain.deleteByExample(adminGroupExample);
        } catch (Exception e) {
            logger.info("删除群组异常：{}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
        return 0;
    }


    // 删除组内成员
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteAdminGroup(Integer adminId) throws AuthException {

        AdminGroupExample adminGroupExample = new AdminGroupExample();
        AdminGroupExample.Criteria criteria = adminGroupExample.createCriteria();
        criteria.andAdminIdEqualTo(adminId);
        return adminGroupDomain.deleteByExample(adminGroupExample);
    }


    // 添加组成员
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer insertAdminGroup(Integer groupId, String adminIds, Integer createAdminId) throws AuthException {

        String[] idStr = adminIds.split(",");
        AdminGroup adminGroup = new AdminGroup();
        for (String id : idStr) {
            adminGroup.setGroupId(groupId);
            adminGroup.setAdminId(Integer.parseInt(id));
            adminGroup.setCreateAdminId(createAdminId);
            adminGroupDomain.add(adminGroup);
        }

        return 0;
    }


    // 管理员、组关系（一对一）
    @Override
    public AdminGroup getAdminGroup(Integer adminId) {

        AdminGroupExample adminGroupExample = new AdminGroupExample();
        adminGroupExample.createCriteria().andAdminIdEqualTo(adminId);
        return adminGroupDomain.getByExample(adminGroupExample);
    }


    // 组、权限关系
    @Override
    public List<GroupAuth> getGroupAuths(Integer groupId) {

        GroupAuthExample groupAuthExample = new GroupAuthExample();
        groupAuthExample.createCriteria().andGroupIdEqualTo(groupId);
        return groupAuthDomain.getAllByExample(groupAuthExample);
    }


    // 组、菜单关系
    @Override
    public List<GroupMenu> getGroupMenus(Integer groupId) {

        GroupMenuExample groupMenuExample = new GroupMenuExample();
        groupMenuExample.createCriteria().andGroupIdEqualTo(groupId);
        return groupMenuDomain.getAllByExample(groupMenuExample);
    }


    // 更新组、菜单关系
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer updateGroupMenu(Integer groupId, String menuIds, Integer createAdminId) throws AuthException {

        //先删除指定组的所有菜单关联
        GroupMenuExample groupMenuExample = new GroupMenuExample();
        GroupMenuExample.Criteria criteria = groupMenuExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        groupMenuDomain.deleteByExample(groupMenuExample);

        //重新生成指定组的所有菜单关联
        String[] menuIdss = menuIds.split(",");
        GroupMenu groupMenu = new GroupMenu();
        groupMenu.setGroupId(groupId);
        groupMenu.setCreateAdminId(createAdminId);
        for (String id : menuIdss) {
            groupMenu.setMenuId(Integer.parseInt(id));
            groupMenuDomain.add(groupMenu);
        }

        return 0;
    }


    // 更新组、权限关系
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer updateGroupAuth(Integer groupId, String uris) throws AuthException {

        //先删除指定组的所有权限关联
        GroupAuthExample groupAuthExample = new GroupAuthExample();
        GroupAuthExample.Criteria criteria = groupAuthExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        groupAuthDomain.deleteByExample(groupAuthExample);

        //重新生成指定组的所有权限关联
        String[] uriss = uris.split(",");
        GroupAuth groupAuth = new GroupAuth();
        groupAuth.setGroupId(groupId);
        groupAuth.setCreateAdminId(authManager.getUserId());
        for (String uri : uriss) {
            groupAuth.setUri(uri);
            groupAuthDomain.add(groupAuth);
        }

        return 0;
    }


    // 组信息
    @Override
    public Group getGroup(Integer groupId) {
        return groupDomain.get(groupId, true);
    }


    // 组内管理员列表
    @Override
    public PageInfo<AdminGroup> getAdminGroups(Integer groupId, Integer page, Integer size) {

        AdminGroupExample example = new AdminGroupExample();
        AdminGroupExample.Criteria criteria = example.createCriteria();
        if (groupId != null) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (StringUtils.isEmpty(example.getOrderByClause())) {
            example.setOrderByClause("create_time desc");
        }

        return adminGroupDomain.getsByExample(example, page, size);
    }


    // 组列表
    @Override
    public PageInfo<Group> getGroups(String name, Integer page, Integer size) {

        GroupExample example = new GroupExample();
        GroupExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (StringUtils.isEmpty(example.getOrderByClause())) {
            example.setOrderByClause("create_time desc");
        }
        return groupDomain.getsByExample(example, page, size);
    }

}
