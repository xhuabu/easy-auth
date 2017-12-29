package com.xhuabu.source.auth;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.AuthMapper;
import com.xhuabu.source.dao.GroupAuthMapper;
import com.xhuabu.source.model.po.Auth;
import com.xhuabu.source.model.po.AuthExample;
import com.xhuabu.source.model.po.GroupAuthExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vicky on 17/10/9.
 */
@Service
public class JLAuthServiceImpl implements JLAuthService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private GroupAuthMapper groupAuthMapper;


    /**
     * 新增权限
     *
     * @param name          权限名
     * @param uri           权限uri
     * @param comment       权限备注
     * @param createAdminId 权限创建人ID
     * @return 1 成功，0失败
     */
    @Override
    @Transactional
    public int insertAuth(String name, String uri, String comment, Integer createAdminId) {
        Auth auth = new Auth();
        auth.setName(name);
        auth.setUri(uri);
        auth.setComment(comment);
        auth.setCreateAdminId(createAdminId);
        return authMapper.insertSelective(auth);
    }


    /**
     * 编辑权限
     *
     * @param authId  权限ID
     * @param name    权限名
     * @param uri     权限uri
     * @param comment 权限备注
     * @return 1 成功，0失败
     */
    @Override
    @Transactional
    public int updateAuth(Integer authId, String name, String uri, String comment) {
        Auth auth = new Auth();
        auth.setId(authId);
        auth.setName(name);
        auth.setUri(uri);
        auth.setComment(comment);
        return authMapper.updateByPrimaryKeySelective(auth);
    }


    /**
     * 删除权限
     *
     * @param authId 权限ID
     * @return 1 成功， 0 失败
     */
    @Override
    @Transactional
    public int deleteAuth(Integer authId) {

        //根据权限id删除权限
        authMapper.deleteByPrimaryKey(authId);

        //删除组与权限的关联
        GroupAuthExample groupAuthExample = new GroupAuthExample();
        GroupAuthExample.Criteria criteria = groupAuthExample.createCriteria();
        criteria.andAuthIdEqualTo(authId);
        groupAuthMapper.deleteByExample(groupAuthExample);

        return 1;
    }

    /**
     * 通过Id获取Auth
     *
     * @param authId 权限ID
     * @return 权限实例
     */
    @Override
    public Auth getAuthById(Integer authId) {
        return authMapper.selectByPrimaryKey(authId);
    }


    /**
     * 获取所有的权限
     *
     * @return 权限列表
     */
    @Override
    public List<Auth> getAllAuth() {

        AuthExample authExample = new AuthExample();
        authExample.setOrderByClause("create_time DESC");
        return authMapper.selectByExample(authExample);

    }

    /**
     * 分页获取权限列表
     *
     * @param pageNo 页码
     * @param pageSize 每页的记录数
     * @return 分页权限列表
     */
    @Override
    public PageInfo<Auth> getListedAuth(Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        AuthExample authExample = new AuthExample();
        authExample.setOrderByClause("create_time DESC");
        List<Auth> auths = authMapper.selectByExample(authExample);
        return new PageInfo<>(auths);
    }
}
