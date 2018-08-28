package com.xhuabu.source.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.GroupAuthMapper;
import com.xhuabu.source.model.po.GroupAuth;
import com.xhuabu.source.model.po.GroupAuthExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Robert on 17/11/20.
 */
@Component
public class GroupAuthDomain implements DomainInterface<GroupAuth, GroupAuthExample> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupAuthMapper groupAuthMapper;

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public GroupAuth get(Integer id, boolean useCache) {
        if(null == id) {
            return null;
        }
        return groupAuthMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public GroupAuth get(String kid, boolean useCache) {
        if(null == kid) {
            return null;
        }
        GroupAuthExample example = new GroupAuthExample();
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
    public Integer add(GroupAuth obj) {
        return groupAuthMapper.insertSelective(obj);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(GroupAuth obj) {
        return groupAuthMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer delete(GroupAuth obj) {
        return groupAuthMapper.deleteByPrimaryKey(obj.getId());
    }

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    @Override
    public GroupAuth getByExample(GroupAuthExample example) {
        List<GroupAuth> list = groupAuthMapper.selectByExample(example);
        return (list.size() != 0) ? list.get(0) : null;
    }

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    @Override
    public Long countByExample(GroupAuthExample example) {
        return groupAuthMapper.countByExample(example);
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
    public PageInfo<GroupAuth> getsByExample(GroupAuthExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(groupAuthMapper.selectByExample(example));
    }

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    @Override
    public List<GroupAuth> getAllByExample(GroupAuthExample example) {
        return groupAuthMapper.selectByExample(example);
    }

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    @Override
    public Integer deleteByExample(GroupAuthExample example) {
        return groupAuthMapper.deleteByExample(example);
    }
}
