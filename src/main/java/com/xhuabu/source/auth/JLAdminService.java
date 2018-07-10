package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.model.po.Admin;
import com.xhuabu.source.model.vo.ListedAdminVO;


public interface JLAdminService {

    /**
     * 管理员用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 管理员模型
     */
    Admin signin(String username, String password) throws AuthException;

    /**
     * 管理员电话密码登录
     *
     * @param phone 用户名
     * @param password 密码
     * @return 管理员模型
     */
    Admin signinWithPhone(String phone, String password) throws AuthException;

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
    Integer updateAdmin(Integer id, String nickName, String password, Integer groupId, Integer status, Integer createAdminId) throws AuthException;

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
    Integer insertAdmin(String nickName, String userName, String password, String phone, Integer groupId, Integer createAdminId);

    /**
     * 描述：删除管理员
     *
     * @param adminId 管理员id
     * @return 1 删除成功， 0 删除失败
     */
    Integer deleteAdmin(Integer adminId);


    /**
     * 判断管理员存不存在
     *
     * @param userName 管理员用户名
     * @return true 存在， false 不存在
     */
    Boolean isExistAdmin(String userName);

    /**
     * 判断手机号码是否重复
     *
     * @param phone 管理员手机
     * @return true 重复， false 未重复
     */
    Boolean isExistPhone(String phone);

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
     * @param username 姓名
     * @param phone 手机号
     * @param status 状态
     * @param page 页码
     * @param size 每页长度
     * @return
     */
    PageInfo<Admin> getAdmins(String username, String phone, Integer status, Integer page, Integer size);

    /**
     * 分页获取管理员列表
     *
     * @return 管理员列表页
     */
    PageInfo<ListedAdminVO> getListedAdmin(Integer page, Integer size);

    /**
     * 通过id查询管理员信息
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    ListedAdminVO getAdminById(Integer adminId);
}
