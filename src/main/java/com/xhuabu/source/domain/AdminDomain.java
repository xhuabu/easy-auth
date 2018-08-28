package com.xhuabu.source.domain;

import com.xhuabu.source.dao.AdminMapper;
import com.xhuabu.source.model.po.Admin;
import com.xhuabu.source.model.po.AdminExample;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Robert on 17/11/20.
 */
@Component
public class AdminDomain implements DomainInterface<Admin, AdminExample> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Admin get(Integer id, boolean useCache) {
        if(null == id) {
            return null;
        }
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Admin get(String kid, boolean useCache) {
        if(null == kid) {
            return null;
        }
        AdminExample example = new AdminExample();
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
    public Integer add(Admin obj) {
        return adminMapper.insertSelective(obj);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(Admin obj) {
        return adminMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer delete(Admin obj) {
        return adminMapper.deleteByPrimaryKey(obj.getId());
    }

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    @Override
    public Admin getByExample(AdminExample example) {
        List<Admin> list = adminMapper.selectByExample(example);
        return (list.size() != 0) ? list.get(0) : null;
    }

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    @Override
    public Long countByExample(AdminExample example) {
        return adminMapper.countByExample(example);
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
    public PageInfo<Admin> getsByExample(AdminExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(adminMapper.selectByExample(example));
    }

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    @Override
    public List<Admin> getAllByExample(AdminExample example) {
        return adminMapper.selectByExample(example);
    }

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    @Override
    public Integer deleteByExample(AdminExample example) {
        return adminMapper.deleteByExample(example);
    }
}
