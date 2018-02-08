package com.xhuabu.source.dao;


import com.xhuabu.source.model.vo.GroupDetailVO;
import com.xhuabu.source.model.vo.ListedAdminGroupVO;
import com.xhuabu.source.model.vo.ListedAdminVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vicky on 17/9/28.
 * 管理员信息操作
 */
@Repository
public interface AdminManagerMapper {

    /**
     * 描述：查询管理员列表
     */
    List<ListedAdminVO> getListedAdmin();

    /**
     * 描述：通过id查询管理员
     */
    ListedAdminVO getAdminById(@Param("id") Integer id);

    /**
     * 描述：通过组id查询组信息
     * */
    GroupDetailVO getGroupDetailById(@Param("id") Integer id);

    /**
     * 描述：通过组id获取组成员信息
     * */
    List<ListedAdminGroupVO> getListedAdminGroup(@Param("id") Integer id);

}
