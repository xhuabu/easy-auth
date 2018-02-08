package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.model.po.*;
import com.xhuabu.source.model.vo.GroupDetailVO;
import com.xhuabu.source.model.vo.GroupVO;
import com.xhuabu.source.model.vo.ListedAdminGroupVO;

import java.util.List;

/**
 * Created by vicky on 17/10/8.
 */
public interface JLGroupService {


    /**
     * 获取组列表
     *
     * @return 下拉框的组列表
     */
    List<Group> getGroups();


    /**
     * 描述：新增群組
     *
     * @param name 群组名
     * @param comment 群组备注
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    int insertGroup(String name, String comment, Integer createAdminId);

    /**
     * 描述：编辑群組
     *
     * @param name  群组名
     * @param groupId 群组ID
     * @return 1 成功， 0失败
     */
    int updateGroup(Integer groupId, String name);

    /**
     * 删除群组
     *
     * @param groupId 群组ID
     * @return  1 成功， 0失败
     */
    int deleteGroup(Integer groupId);


    /**
     * 删除组内成员
     *
     * @param adminId 管理员ID
     * @return  1 成功， 0失败
     */
    int deleteGroupAdminById(Integer adminId);


    /**
     * 添加组成员
     *
     * @param groupId       群组ID
     * @param adminIds      管理员ids，如：1,2,3,4,5,6
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    int insertAdminIntoGroup(Integer groupId, String adminIds, Integer createAdminId);

    /**
     * 获取管理员群组
     *
     * @param adminId 管理员Id
     * @return 管理员群组实例
     */
    List<AdminGroup> getAdminGroupByAdminId(Integer adminId);

    /**
     * 获取群组权限
     *
     * @param groupId 群组ID
     * @return 组权限实例
     */
    List<GroupAuth> getGroupAuthByGroupId(Integer groupId);

    /**
     * 获取组菜单
     * @param groupId 群组ID
     * @return 组菜单实例
     */
    List<GroupMenu> getGroupMenuByGroupId(Integer groupId);

    /**
     * 更新组菜单
     *
     * @param groupId  群组ID
     * @param menuIds  菜单ids, 如： 1，2，3
     * @param createAdminId 创建人ID
     * @return 1 成功， 0失败
     */
    int updateGroupMenu(Integer groupId, String menuIds, Integer createAdminId);

    /**
     * 更新组权限
     *
     * @param groupId 群组ID
     * @param uris 多个权限uri
     * @return 1 成功， 0失败
     */
    int updateGroupAuth(Integer groupId, String uris);



    /**
     * 描述：获取页面渲染的组列表
     *
     * @return
     */
    PageInfo<GroupVO> getListedGroup();

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
     * 添加组成员-获取未归组的管理员列表
     *
     * @param query 查询条件(姓名/账号)
     * @return 管理员分页模型
     */
    PageInfo<Admin> getListedAdmin(String query);


    /**
     * 根据组id获取组详情
     *
     * @param groupId 组id
     * @return 组详情
     */
    GroupDetailVO getGroupDetailById(Integer groupId);


    /**
     * 根据adminId获取所属组
     *
     * @param adminId
     * @return 组
     */
    Group getGroupByAdminId(Integer adminId);


    /**
     * 根据组ID获取组
     *
     * @param groupId
     * @return 组
     */
    Group getGroup(Integer groupId);

    /**
     * 根据组ID分页获取管理员列表
     *
     * @param groupId 组ID
     * @param key 查询条件
     * @param page 页码
     * @param size 页数
     * @return 组管理员分页
     */
    PageInfo<AdminGroup> getAdminGroupsByGroupId(Integer groupId, String key, Integer page, Integer size);


    /**
     * 获取组并分页
     *
     * @param key  组名筛选
     * @param page
     * @param size
     * @return
     */
    PageInfo<Group> getGroupWithPage(String key, Integer page, Integer size);

}
