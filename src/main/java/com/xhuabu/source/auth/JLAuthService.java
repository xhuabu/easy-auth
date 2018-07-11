package com.xhuabu.source.auth;

import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.model.po.Auth;

public interface JLAuthService {

    /**
     * 权限信息
     *
     * @param id
     * @return
     */
    Auth getAuth(Integer id);

    /**
     * 权限列表
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Auth> getAuths(Integer groupId, String name, String uri, Integer page, Integer size);

    /**
     * 增加权限
     *
     * @param name
     * @param uri
     * @param adminId
     * @return
     * @throws AuthException
     */
    Integer addAuth(String name, String uri, Integer adminId) throws AuthException;

    /**
     * 更新权限
     *
     * @param id
     * @param name
     * @param uri
     * @return
     * @throws AuthException
     */
    Integer updateAuth(Integer id, String name, String uri) throws AuthException;

    /**
     * 删除权限
     *
     * @param id
     * @return
     * @throws AuthException
     */
    Integer deleteAuth(Integer id) throws AuthException;

    /**
     * 获取用户有权限访问的URI的Set
     *
     * @param userId
     * @return
     */
    JLAuthBean getAvailableUriByUserId(Integer userId);
}
