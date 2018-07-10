package com.xhuabu.source.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.GroupMapper;
import com.xhuabu.source.model.po.Group;
import com.xhuabu.source.model.po.GroupExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Robert on 17/11/20.
 */
@Component
public class GroupDomain implements DomainInterface<Group, GroupExample> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupMapper groupMapper;

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Group get(Integer id, boolean useCache) {
        if(null == id) {
            return null;
        }
        return groupMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Group get(String kid, boolean useCache) {
        if(null == kid) {
            return null;
        }
        GroupExample example = new GroupExample();
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
    public Integer add(Group obj) {
        return groupMapper.insertSelective(obj);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(Group obj) {
        return groupMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer delete(Group obj) {
        return groupMapper.deleteByPrimaryKey(obj.getId());
    }

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    @Override
    public Group getByExample(GroupExample example) {
        List<Group> list = groupMapper.selectByExample(example);
        return (list.size() != 0) ? list.get(0) : null;
    }

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    @Override
    public Long countByExample(GroupExample example) {
        return groupMapper.countByExample(example);
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
    public PageInfo<Group> getsByExample(GroupExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(groupMapper.selectByExample(example));
    }

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    @Override
    public List<Group> getAllByExample(GroupExample example) {
        return groupMapper.selectByExample(example);
    }

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    @Override
    public Integer deleteByExample(GroupExample example) {
        return groupMapper.deleteByExample(example);
    }
}
