package com.xhuabu.source.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.AuthMapper;
import com.xhuabu.source.model.po.Auth;
import com.xhuabu.source.model.po.AuthExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Robert on 17/11/20.
 */
@Component
public class AuthDomain implements DomainInterface<Auth, AuthExample> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthMapper authMapper;

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Auth get(Integer id, boolean useCache) {
        if(null == id) {
            return null;
        }
        return authMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Auth get(String kid, boolean useCache) {
        if(null == kid) {
            return null;
        }
        AuthExample example = new AuthExample();
        example.createCriteria().andKidEqualTo(kid);
        return this.getByExample(example);
    }

    /**
     * 添加
     *
     * @param obj
     * @return
     */
    @Override
    public Integer add(Auth obj) {
        return authMapper.insertSelective(obj);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(Auth obj) {
        return authMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer delete(Auth obj) {
        return authMapper.deleteByPrimaryKey(obj.getId());
    }

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    @Override
    public Auth getByExample(AuthExample example) {
        List<Auth> list = authMapper.selectByExample(example);
        return (list.size() != 0) ? list.get(0) : null;
    }

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    @Override
    public Long countByExample(AuthExample example) {
        return authMapper.countByExample(example);
    }

    /**
     * 分页获取
     *
     * @param example
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Auth> getsByExample(AuthExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(authMapper.selectByExample(example));
    }

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    @Override
    public List<Auth> getAllByExample(AuthExample example) {
        return authMapper.selectByExample(example);
    }

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    @Override
    public Integer deleteByExample(AuthExample example) {
        return authMapper.deleteByExample(example);
    }
}
