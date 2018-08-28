package com.xhuabu.source.domain;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.AdminGroupMapper;
import com.xhuabu.source.model.po.AdminGroup;
import com.xhuabu.source.model.po.AdminGroupExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Robert on 17/11/20.
 */
@Component
public class AdminGroupDomain implements DomainInterface<AdminGroup, AdminGroupExample> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminGroupMapper adminGroupMapper;

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public AdminGroup get(Integer id, boolean useCache) {
        if(null == id) {
            return null;
        }
        return adminGroupMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public AdminGroup get(String kid, boolean useCache) {
        if(null == kid) {
            return null;
        }
        AdminGroupExample example = new AdminGroupExample();
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
    public Integer add(AdminGroup obj) {
        return adminGroupMapper.insertSelective(obj);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(AdminGroup obj) {
        return adminGroupMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer delete(AdminGroup obj) {
        return adminGroupMapper.deleteByPrimaryKey(obj.getId());
    }

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    @Override
    public AdminGroup getByExample(AdminGroupExample example) {
        List<AdminGroup> list = adminGroupMapper.selectByExample(example);
        return (list.size() != 0) ? list.get(0) : null;
    }

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    @Override
    public Long countByExample(AdminGroupExample example) {
        return adminGroupMapper.countByExample(example);
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
    public PageInfo<AdminGroup> getsByExample(AdminGroupExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(adminGroupMapper.selectByExample(example));
    }

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    @Override
    public List<AdminGroup> getAllByExample(AdminGroupExample example) {
        return adminGroupMapper.selectByExample(example);
    }

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    @Override
    public Integer deleteByExample(AdminGroupExample example) {
        return adminGroupMapper.deleteByExample(example);
    }
}
