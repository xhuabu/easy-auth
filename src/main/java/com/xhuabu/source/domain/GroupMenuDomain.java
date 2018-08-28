package com.xhuabu.source.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.GroupMenuMapper;
import com.xhuabu.source.model.po.GroupMenu;
import com.xhuabu.source.model.po.GroupMenuExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Robert on 17/11/20.
 */
@Component
public class GroupMenuDomain implements DomainInterface<GroupMenu, GroupMenuExample> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupMenuMapper groupMenuMapper;

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public GroupMenu get(Integer id, boolean useCache) {
        if(null == id) {
            return null;
        }
        return groupMenuMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public GroupMenu get(String kid, boolean useCache) {
        if(null == kid) {
            return null;
        }
        GroupMenuExample example = new GroupMenuExample();
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
    public Integer add(GroupMenu obj) {
        return groupMenuMapper.insertSelective(obj);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(GroupMenu obj) {
        return groupMenuMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer delete(GroupMenu obj) {
        return groupMenuMapper.deleteByPrimaryKey(obj.getId());
    }

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    @Override
    public GroupMenu getByExample(GroupMenuExample example) {
        List<GroupMenu> list = groupMenuMapper.selectByExample(example);
        return (list.size() != 0) ? list.get(0) : null;
    }

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    @Override
    public Long countByExample(GroupMenuExample example) {
        return groupMenuMapper.countByExample(example);
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
    public PageInfo<GroupMenu> getsByExample(GroupMenuExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(groupMenuMapper.selectByExample(example));
    }

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    @Override
    public List<GroupMenu> getAllByExample(GroupMenuExample example) {
        return groupMenuMapper.selectByExample(example);
    }

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    @Override
    public Integer deleteByExample(GroupMenuExample example) {
        return groupMenuMapper.deleteByExample(example);
    }
}
