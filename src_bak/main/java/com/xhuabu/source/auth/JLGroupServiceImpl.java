package com.xhuabu.source.auth;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.tool.StringUtil;
import com.xhuabu.source.dao.*;
import com.xhuabu.source.model.po.*;
import com.xhuabu.source.model.vo.GroupDetailVO;
import com.xhuabu.source.model.vo.GroupVO;
import com.xhuabu.source.model.vo.ListedAdminGroupVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicky on 17/10/8.
 */
@Service
public class JLGroupServiceImpl implements JLGroupService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private AdminGroupMapper adminGroupMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    GroupAuthMapper groupAuthMapper;

    @Autowired
    GroupMenuMapper groupMenuMapper;

    @Autowired
    AdminManagerMapper adminManagerMapper;

    @Autowired
    JLAuthManager authManager;


    /**
     * 获取组列表
     *
     * @return 组列表
     */
    @Override
    public List<Group> getGroups() {
        GroupExample groupExample = new GroupExample();
        return groupMapper.selectByExample(groupExample);
    }


    /**
     * 描述：新增群組
     *
     * @param name          群组名
     * @param comment       群组备注
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    @Override
    @Transactional
    public int insertGroup(String name, String comment, Integer createAdminId) {
        Group group = new Group();
        group.setName(name);
        group.setComment(comment);
        group.setCreateAdminId(createAdminId);
        return groupMapper.insertSelective(group);
    }


    /**
     * 描述：编辑群組
     *
     * @param name    群组名
     * @param groupId 群组ID
     * @return 1 成功， 0失败
     */
    @Override
    @Transactional
    public int updateGroup(Integer groupId, String name) {
        Group group = new Group();
        group.setName(name);
        group.setId(groupId);
        return groupMapper.updateByPrimaryKeySelective(group);
    }

    /**
     * 删除群组
     *
     * @param groupId 群组ID
     * @return 1 成功， 0失败
     */
    @Override
    @Transactional
    public int deleteGroup(Integer groupId) {

        try {
            //删除组
            groupMapper.deleteByPrimaryKey(groupId);

            //删除与指定组的所有管理员关联
            AdminGroupExample adminGroupExample = new AdminGroupExample();
            AdminGroupExample.Criteria criteria = adminGroupExample.createCriteria();
            criteria.andGroupIdEqualTo(groupId);
            adminGroupMapper.deleteByExample(adminGroupExample);
            return 1;
        } catch (Exception e) {
            logger.info("- 删除群组 - 异常：{}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }


    /**
     * 删除组内成员
     *
     * @param adminId 管理员ID
     * @return 1 成功， 0失败
     */
    @Override
    @Transactional
    public int deleteGroupAdminById(Integer adminId) {

        AdminGroupExample adminGroupExample = new AdminGroupExample();
        AdminGroupExample.Criteria criteria = adminGroupExample.createCriteria();
        criteria.andAdminIdEqualTo(adminId);
        return adminGroupMapper.deleteByExample(adminGroupExample);
    }


    /**
     * 添加组成员
     *
     * @param groupId       群组ID
     * @param adminIds      管理员ids，如：1,2,3,4,5,6
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    @Override
    @Transactional
    public int insertAdminIntoGroup(Integer groupId, String adminIds, Integer createAdminId) {

        String[] idStr = adminIds.split(",");
        AdminGroup adminGroup = new AdminGroup();
        for (String id : idStr) {
            adminGroup.setGroupId(groupId);
            adminGroup.setAdminId(Integer.parseInt(id));
            adminGroup.setCreateAdminId(createAdminId);
            adminGroupMapper.insertSelective(adminGroup);
        }

        return 1;
    }

    /**
     * 获取AdminGroup
     *
     * @param adminId 管理员Id
     * @return 管理员群组实例
     */
    @Override
    public List<AdminGroup> getAdminGroupByAdminId(Integer adminId) {

        AdminGroupExample adminGroupExample = new AdminGroupExample();
        adminGroupExample.createCriteria().andAdminIdEqualTo(adminId);
        return adminGroupMapper.selectByExample(adminGroupExample);
    }

    /**
     * 获取群组权限
     *
     * @param groupId 群组ID
     * @return 组权限实例
     */
    @Override
    public List<GroupAuth> getGroupAuthByGroupId(Integer groupId) {
        GroupAuthExample groupAuthExample = new GroupAuthExample();
        groupAuthExample.createCriteria().andGroupIdEqualTo(groupId);
        return groupAuthMapper.selectByExample(groupAuthExample);
    }

    /**
     * 获取组菜单
     *
     * @param groupId 群组ID
     * @return 组菜单实例
     */
    @Override
    public List<GroupMenu> getGroupMenuByGroupId(Integer groupId) {

        GroupMenuExample groupMenuExample = new GroupMenuExample();
        groupMenuExample.createCriteria().andGroupIdEqualTo(groupId);
        return groupMenuMapper.selectByExample(groupMenuExample);
    }


    /**
     * 更新组菜单
     *
     * @param groupId       群组ID
     * @param menuIds       菜单ids, 如： 1，2，3
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    @Override
    @Transactional
    public int updateGroupMenu(Integer groupId, String menuIds, Integer createAdminId) {


        //先删除指定组的所有菜单关联
        GroupMenuExample groupMenuExample = new GroupMenuExample();
        GroupMenuExample.Criteria criteria = groupMenuExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        groupMenuMapper.deleteByExample(groupMenuExample);

        //重新生成指定组的所有菜单关联
        String[] idStr = menuIds.split(",");
        GroupMenu groupMenu = new GroupMenu();
        groupMenu.setGroupId(groupId);
        groupMenu.setCreateAdminId(createAdminId);
        for (String id : idStr) {
            groupMenu.setMenuId(Integer.parseInt(id));
            groupMenuMapper.insertSelective(groupMenu);
        }

        return 1;

    }


    /**
     * 更新组权限
     *
     * @param groupId       群组ID
     * @param uris        * @param uris 多个权限uri
     * @return 1 成功， 0失败
     */
    @Override
    @Transactional
    public int updateGroupAuth(Integer groupId, String uris) {


        //先删除指定组的所有权限关联
        GroupAuthExample groupAuthExample = new GroupAuthExample();
        GroupAuthExample.Criteria criteria = groupAuthExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        groupAuthMapper.deleteByExample(groupAuthExample);

        //重新生成指定组的所有权限关联
        String[] uriStrList = uris.split(",");
        GroupAuth groupAuth = new GroupAuth();
        groupAuth.setGroupId(groupId);
        groupAuth.setCreateAdminId(authManager.getUserId());
        for (String uri : uriStrList) {
            groupAuth.setUri(uri);
            groupAuthMapper.insertSelective(groupAuth);
        }

        return 1;
    }


    /**
     * 描述：获取页面渲染的组列表
     *
     * @return 组分页模型
     */
    @Override
    public PageInfo<GroupVO> getListedGroup() {

        GroupExample groupExample = new GroupExample();
        groupExample.setOrderByClause("create_time DESC");
        List<Group> groups = groupMapper.selectByExample(groupExample);

        List<GroupVO> listGroup = new ArrayList<>();
        GroupVO groupVO;
        Admin admin = null;
        for (Group group : groups) {
            groupVO = new GroupVO();
            BeanUtils.copyProperties(group, groupVO);
            admin = adminMapper.selectByPrimaryKey(group.getCreateAdminId());
            groupVO.setCreaterName(admin.getNickName());
            listGroup.add(groupVO);
        }

        return new PageInfo<>(listGroup);
    }

    /**
     * 根据组id获取组详情
     *
     * @param groupId 组id
     * @return 组详情
     */
    @Override
    public GroupDetailVO getGroupDetailById(Integer groupId) {

        return adminManagerMapper.getGroupDetailById(groupId);
    }

    /**
     * 根据组id获取组成员列表
     *
     * @param pageNo   页码
     * @param pageSize 页数
     * @param id       组ID
     * @return 组成员列表
     */
    @Override
    public PageInfo<ListedAdminGroupVO> getListedAdminGroupById(Integer pageNo, Integer pageSize, Integer id) {

        PageHelper.startPage(pageNo, pageSize);
        List<ListedAdminGroupVO> listedAdminGroup = adminManagerMapper.getListedAdminGroup(id);
        return new PageInfo<>(listedAdminGroup);
    }

    /**
     * 获取未归组的管理员列表
     *
     * @param query 查询条件(姓名/账号)
     * @return 管理员分页模型
     */
    @Override
    public PageInfo<Admin> getListedAdmin(String query) {


        AdminGroupExample adminGroupExample = new AdminGroupExample();
        List<AdminGroup> adminGroups = adminGroupMapper.selectByExample(adminGroupExample);

        //获取已有组的管理员
        List<Integer> ids = new ArrayList<>();
        for (AdminGroup adminGroup : adminGroups) {
            ids.add(adminGroup.getAdminId());
        }

        //查询没归组的管理员
        AdminExample adminExample = new AdminExample();
        if (!StringUtil.isEmpty(query)) {
            AdminExample.Criteria criteria1 = adminExample.createCriteria();
            AdminExample.Criteria criteria2 = adminExample.or();
            criteria1.andIdNotIn(ids).andUsernameLike("%" + query + "%");
            criteria2.andIdNotIn(ids).andPhoneLike("%" + query + "%");
        } else {
            adminExample.createCriteria().andIdNotIn(ids);
        }
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        return new PageInfo<>(admins);

    }


    /**
     * 根据adminId获取所属组
     *
     * @param adminId
     * @return 组
     */
    @Override
    public Group getGroupByAdminId(Integer adminId){

        // 管理员和组是一对一关系
        AdminGroupExample example = new AdminGroupExample();
        example.or().andAdminIdEqualTo(adminId);
        List<AdminGroup> adminGroups = adminGroupMapper.selectByExample(example);

        if (adminGroups == null || adminGroups.isEmpty()) {
            return null;
        }

        // 获取组
        return groupMapper.selectByPrimaryKey(adminGroups.get(0).getGroupId());
    }


    /**
     * 根据组ID获取组
     *
     * @param groupId
     * @return 组
     */
    @Override
    public Group getGroup(Integer groupId) {

        return groupMapper.selectByPrimaryKey(groupId);
    }

    /**
     * 根据组ID分页获取管理员列表
     *
     * @param groupId 组ID
     * @param key 查询条件
     * @param page 页码
     * @param size 页数
     * @return 组管理员分页
     */
    @Override
    public PageInfo<AdminGroup> getAdminGroupsByGroupId(Integer groupId, String key, Integer page, Integer size) {


        // 根据Key 获取idlist
        List<Integer> adminIdList = new ArrayList<>();
        if (!StringUtils.isEmpty(key)) {
            AdminExample adminExample = new AdminExample();
            AdminExample.Criteria criteria1 = adminExample.createCriteria();
            AdminExample.Criteria criteria2 = adminExample.or();

            criteria1.andPhoneLike("%" + key + "%");
            criteria2.andNickNameLike("%" + key + "%");
            List<Admin> adminList = adminMapper.selectByExample(adminExample);
            for (Admin admin : adminList) {
                adminIdList.add(admin.getId());
            }
        }


        // 获取对应groupId的管理员IdList
        AdminGroupExample example = new AdminGroupExample();
        AdminGroupExample.Criteria criteria = example.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        if (!StringUtils.isEmpty(key)) {
            if (adminIdList.isEmpty()) {
                adminIdList.add(-1);
            }
            criteria.andAdminIdIn(adminIdList);
        }


        return PageHelper.startPage(page, size).doSelectPageInfo( () -> {
            adminGroupMapper.selectByExample(example);
        });

    }

    /**
     * 查询组
     *
     * @param key  组名筛选
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Group> getGroupWithPage(String key, Integer page, Integer size){


        GroupExample groupExample = getGroupExample(key);

        PageHelper.startPage(page, size);
        PageInfo<Group> pageInfo = new PageInfo<>(groupMapper.selectByExample(groupExample));

        return pageInfo;
    }

    /**
     * 获取组查询条件
     *
     * @param key
     * @return
     */
    private GroupExample getGroupExample(String key) {
        GroupExample groupsExample = new GroupExample();
        if (!StringUtils.isEmpty(key)) {
            groupsExample.createCriteria().andNameLike("%" + key + "%");
        }

        groupsExample.setOrderByClause("create_time desc");

        return groupsExample;
    }



}
