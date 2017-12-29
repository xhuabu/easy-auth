package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.model.po.Auth;

import java.util.List;

/**
 * Created by vicky on 17/10/9.
 */
public interface JLAuthService {


    /**
     * 新增权限
     *
     * @param name 权限名
     * @param uri 权限uri
     * @param comment 权限备注
     * @param createAdminId 权限创建人ID
     * @return 1 成功，0失败
     */
    int insertAuth(String name, String uri, String comment, Integer createAdminId);

    /**
     * 编辑权限
     *
     * @param authId 权限ID
     * @param name 权限名
     * @param uri 权限uri
     * @param comment 权限备注
     * @return 1 成功，0失败
     */
    int updateAuth(Integer authId, String name, String uri, String comment);


    /**
     * 删除权限
     *
     * @param authId 权限ID
     * @return 1 成功， 0 失败
     */
    int deleteAuth(Integer authId);


    /**
     * 通过Id获取Auth
     *
     * @param authId 权限ID
     * @return 权限实例
     */
    Auth getAuthById(Integer authId);


    /**
     * 获取所有的权限
     * @return 权限列表
     */
    List<Auth> getAllAuth();

    /**
     * 分页获取权限列表
     *
     * @param pageNo 页码
     * @param pageSize 每页的记录数
     * @return 分页权限列表
     */
    PageInfo<Auth> getListedAuth(Integer pageNo, Integer pageSize);




}
