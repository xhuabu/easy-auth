package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.model.po.Admin;
import com.xhuabu.source.model.vo.ListedAdminVO;


public interface JLAdminService {


    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 管理员模型
     */
    public Admin signin(String username, String password);

    /**
     * 描述：编辑管理员
     *
     * @param id            管理员id
     * @param nickName      昵称
     * @param password      密码
     * @param groupId       组id
     * @param status        管理员状态 状态 0:初始状态 1:正常使用 -1:冻结
     * @param createAdminId 创建人ID
     * @return 1 更新成功， 0 更新失败
     */
    int updateAdmin(Integer id, String nickName, String password, Integer groupId, Integer status, Integer createAdminId);

    /**
     * 描述：添加管理员
     *
     * @param nickName      昵称
     * @param userName      用户名
     * @param password      密码
     * @param phone         手机号
     * @param groupId       组id
     * @param createAdminId 创建者id
     * @return 1 创建成功， 0 创建失败
     */
    int insertAdmin(String nickName, String userName, String password, String phone, Integer groupId, Integer createAdminId);

    /**
     * 描述：删除管理员
     *
     * @param adminId 管理员id
     * @return 1 删除成功， 0 删除失败
     */
    int deleteAdmin(Integer adminId);


    /**
     * 判断管理员存不存在
     *
     * @param userName 管理员用户名
     * @return true 存在， false 不存在
     */
    boolean isExistAdmin(String userName);

    /**
     * 判断手机号码是否重复
     *
     * @param phone 管理员手机
     * @return true 重复， false 未重复
     */
    boolean isExistPhone(String phone);

    /**
     * 根据管理员id获取管理员
     *
     * @param adminId 管理员ID
     * @return 管理员实例
     */
    Admin getAdmin(Integer adminId);

    /**
     * 搜索管理员
     *
     * @param key  关键字(姓名、账号)
     * @param page 页码
     * @param size 每页长度
     * @return
     */
    PageInfo<Admin> getAdminsByKeyAndStatus(String key, Integer status, Integer page, Integer size);

    /**
     * 分页获取管理员列表
     *
     * @return 管理员列表页
     */
    PageInfo<ListedAdminVO> getListedAdmin(Integer pageNo, Integer pageSize);

    /**
     * 通过id查询管理员信息
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    ListedAdminVO getAdminById(Integer adminId);
}
