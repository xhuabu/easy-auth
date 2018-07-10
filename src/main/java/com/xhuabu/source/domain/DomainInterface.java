package com.xhuabu.source.domain;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by zzhoo8 on 2017/9/21.
 */
public interface DomainInterface<T, E> {

    /**
     * 获取通过主键
     *
     * @param id 默认自增id
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    T get(Integer id, boolean useCache);

    /**
     * 获取通过主键
     *
     * @param kid
     * @param useCache 是否从Redis 缓存中获取
     * @return
     */
    T get(String kid, boolean useCache);

    /**
     * 添加
     *
     * @param dao
     * @return
     */
    Integer add(T dao);

    /**
     * 保存
     *
     * @param dao
     * @return
     */
    Integer save(T dao);

    /**
     * 删除
     *
     * @param dao
     * @return
     */
    Integer delete(T dao);

    /**
     * 获取通过example (取List的第0个)
     *
     * @param example
     * @return
     */
    T getByExample(E example);

    /**
     * 获取count
     *
     * @param example
     * @return
     */
    Long countByExample(E example);

    /**
     * 分页获取
     *
     * @param example
     * @param page
     * @param size
     * @return
     */
    PageInfo<T> getsByExample(E example, Integer page, Integer size);

    /**
     * 获取所有通过example
     *
     * @param example
     * @return
     */
    List<T> getAllByExample(E example);

    /**
     * 删除通过条件
     *
     * @param example
     * @return
     */
    Integer deleteByExample(E example);

}
