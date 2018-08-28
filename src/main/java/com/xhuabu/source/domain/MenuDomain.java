package com.xhuabu.source.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.dao.MenuMapper;
import com.xhuabu.source.model.po.Menu;
import com.xhuabu.source.model.po.MenuExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Robert on 17/11/20.
 */
@Component
public class MenuDomain implements DomainInterface<Menu, MenuExample> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Menu get(Integer id, boolean useCache) {
        if(null == id) {
            return null;
        }
        return menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    @Override
    public Menu get(String kid, boolean useCache) {
        if(null == kid) {
            return null;
        }
        MenuExample example = new MenuExample();
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
    public Integer add(Menu obj) {
        return menuMapper.insertSelective(obj);
    }

    /**
     * 保存
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(Menu obj) {
        return menuMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    @Override
    public Integer delete(Menu obj) {
        return menuMapper.deleteByPrimaryKey(obj.getId());
    }

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    @Override
    public Menu getByExample(MenuExample example) {
        List<Menu> list = menuMapper.selectByExample(example);
        return (list.size() != 0) ? list.get(0) : null;
    }

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    @Override
    public Long countByExample(MenuExample example) {
        return menuMapper.countByExample(example);
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
    public PageInfo<Menu> getsByExample(MenuExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(menuMapper.selectByExample(example));
    }

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    @Override
    public List<Menu> getAllByExample(MenuExample example) {
        return menuMapper.selectByExample(example);
    }

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    @Override
    public Integer deleteByExample(MenuExample example) {
        return menuMapper.deleteByExample(example);
    }
}
