package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.model.po.AdminGroup;
import com.xhuabu.source.model.po.Group;
import com.xhuabu.source.model.po.GroupAuth;
import com.xhuabu.source.model.po.GroupMenu;
import com.xhuabu.source.model.vo.GroupDetailVO;
import com.xhuabu.source.model.vo.ListedAdminGroupVO;

import java.util.List;

/**
 * Created by vicky on 17/10/8.
 */
public interface JLGroupService {


    /**
     * 描述：新增群組
     *
     * @param name 群组名
     * @param comment 群组备注
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    Integer insertGroup(String name, String comment, Integer createAdminId) throws AuthException;

    /**
     * 描述：编辑群組
     *
     * @param name  群组名
     * @param groupId 群组ID
     * @return 1 成功， 0失败
     */
    Integer updateGroup(Integer groupId, String name) throws AuthException;

    /**
     * 删除群组
     *
     * @param groupId 群组ID
     * @return  1 成功， 0失败
     */
    Integer deleteGroup(Integer groupId) throws AuthException;

    /**
     * 删除组内成员
     *
     * @param adminId 管理员ID
     * @return  1 成功， 0失败
     */
    Integer deleteAdminGroup(Integer adminId) throws AuthException ;


    /**
     * 添加组成员
     *
     * @param groupId       群组ID
     * @param adminIds      管理员ids，如：1,2,3,4,5,6
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    Integer insertAdminGroup(Integer groupId, String adminIds, Integer createAdminId) throws AuthException ;

    /**
     * 管理员、组关系（一对一）
     *
     * @param adminId 管理员Id
     * @return 管理员群组实例
     */
    AdminGroup getAdminGroup(Integer adminId);

    /**
     * 获取群组权限
     *
     * @param groupId 群组ID
     * @return 组权限实例
     */
    List<GroupAuth> getGroupAuths(Integer groupId);

    /**
     * 获取组菜单
     * @param groupId 群组ID
     * @return 组菜单实例
     */
    List<GroupMenu> getGroupMenus(Integer groupId);

    /**
     * 更新组菜单
     *
     * @param groupId  群组ID
     * @param menuIds  菜单ids, 如： 1，2，3
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    Integer updateGroupMenu(Integer groupId, String menuIds, Integer createAdminId) throws AuthException;

    /**
     * 更新组、权限关系
     *
     * @param groupId 群组ID
     * @param uris 多个权限uri
     * @return 1 成功， 0失败
     */
    Integer updateGroupAuth(Integer groupId, String uris) throws AuthException;

    /**
     * 根据组id获取组成员列表
     *
     * @param pageNo  页码
     * @param pageSize 页数
     * @param id 组ID
     * @return  组成员列表
     */
    PageInfo<ListedAdminGroupVO> getListedAdminGroupById(Integer pageNo, Integer pageSize, Integer id);

    /**
     * 根据组id获取组详情
     *
     * @param groupId 组id
     * @return 组详情
     */
    GroupDetailVO getGroupDetailById(Integer groupId);

    /**
     * 组信息
     *
     * @param groupId
     * @return 组
     */
    Group getGroup(Integer groupId);

    /**
     * 组内管理员列表
     *
     * @param groupId 组ID
     * @param page 页码
     * @param size 页数
     * @return 组管理员分页
     */
    PageInfo<AdminGroup> getAdminGroups(Integer groupId, Integer page, Integer size);


    /**
     * 组列表
     *
     * @param name  组名筛选
     * @param page
     * @param size
     * @return
     */
    PageInfo<Group> getGroups(String name, Integer page, Integer size);

}
