package com.xhuabu.source.auth;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.coder.PasswordEncoder;
import com.xhuabu.source.common.enums.AdminStatusEnum;
import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.common.manager.AnnotationManager;
import com.xhuabu.source.common.tool.StringUtil;
import com.xhuabu.source.dao.AdminGroupMapper;
import com.xhuabu.source.dao.AdminManagerMapper;
import com.xhuabu.source.dao.AdminMapper;
import com.xhuabu.source.model.po.Admin;
import com.xhuabu.source.model.po.AdminExample;
import com.xhuabu.source.model.po.AdminGroup;
import com.xhuabu.source.model.po.AdminGroupExample;
import com.xhuabu.source.model.vo.ListedAdminVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class JLAdminServiceImpl implements JLAdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminMapper adminMapper;


    @Autowired
    private AdminGroupMapper adminGroupMapper;

    @Autowired
    AnnotationManager annotationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AdminManagerMapper adminManagerMapper;


    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 管理员模型
     */
    public Admin signin(String username, String password) {

        //1 判断用户是否存在
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if (adminList == null || adminList.isEmpty()) {
            logger.error("管理员不存在:{}", username);
            throw new AuthException("管理员不存在");
        }

        //2 取出用户
        Admin admin = adminList.get(0);

        //3 判断密码是否正确
        String savedPwd = admin.getPassword();
        String salt = admin.getSalt();

        //todo 需要自定义密码加密方式
        Boolean pwdMathch = passwordEncoder.crypt(password, salt).equals(savedPwd);
        if (!pwdMathch) {
            logger.error("管理员:{} 登录密码不正确:{}", username, password);
            throw new AuthException("管理员登录密码不正确");
        }

        //4 判断账户当前状态是否为"冻结"
        if (AdminStatusEnum.ADMIN_STATUS_FREEZE.getCode().equals(admin.getStatus())) {
            logger.info("当前账户已被冻结！");
            throw new AuthException("当前账户已被冻结!");
        }

        //5 登录成功，记录当前登录时间
        admin.setLastTime(new Date());
        adminMapper.updateByPrimaryKeySelective(admin);

        logger.info("管理员登录成功: {}， {}", username, password);
        return admin;
    }

    /**
     * 管理员电话密码登录
     *
     * @param phone 用户名
     * @param password 密码
     * @return 管理员模型
     */
    @Override
    public Admin signinWithPhone(String phone, String password){
        //1 判断用户是否存在
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if (adminList == null || adminList.isEmpty()) {
            logger.error("管理员不存在:{}", phone);
            throw new AuthException("管理员不存在");
        }

        //2 取出用户
        Admin admin = adminList.get(0);

        //3 判断密码是否正确
        String savedPwd = admin.getPassword();
        String salt = admin.getSalt();

        //todo 需要自定义密码加密方式
        Boolean pwdMathch = passwordEncoder.crypt(password, salt).equals(savedPwd);
        if (!pwdMathch) {
            logger.error("管理员:{} 登录密码不正确:{}", phone, password);
            throw new AuthException("管理员登录密码不正确");
        }

        //4 判断账户当前状态是否为"冻结"
        if (AdminStatusEnum.ADMIN_STATUS_FREEZE.getCode().equals(admin.getStatus())) {
            logger.info("当前账户已被冻结！");
            throw new AuthException("当前账户已被冻结!");
        }

        //5 登录成功，记录当前登录时间
        admin.setLastTime(new Date());
        adminMapper.updateByPrimaryKeySelective(admin);

        logger.info("管理员登录成功: {}， {}", phone, password);
        return admin;
    }


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
    @Override
    @Transactional
    public int updateAdmin(Integer id, String nickName, String password, Integer groupId, Integer status, Integer createAdminId) {

        Admin admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            logger.info("不存在该管理员！");
            return 0;
        }
        admin.setId(id);
        admin.setNickName(nickName);

        //如果password不为空，则重置密码
        if (!StringUtil.isEmpty(password)) {
            String salt = admin.getSalt();


            password = passwordEncoder.crypt(password, salt);
            admin.setPassword(password);
        }

        admin.setStatus(status);

        //更新admin表的管理员信息
        adminMapper.updateByPrimaryKeySelective(admin);

        //更新admin_group表的管理员与组的关联信息
        //1.先删除原先的关联信息
        AdminGroupExample adminGroupExample = new AdminGroupExample();
        AdminGroupExample.Criteria criteria = adminGroupExample.createCriteria();
        criteria.andAdminIdEqualTo(id);
        adminGroupMapper.deleteByExample(adminGroupExample);


        //2.重新建立关联信息
        AdminGroup adminGroup = new AdminGroup();
        adminGroup.setAdminId(id);
        adminGroup.setGroupId(groupId);
        adminGroup.setCreateAdminId(createAdminId);
        adminGroupMapper.insertSelective(adminGroup);

        return 1;
    }


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
    @Override
    @Transactional
    public int insertAdmin(String nickName, String userName, String password, String phone, Integer groupId, Integer createAdminId) {


        //新增管理员
        Admin admin = new Admin();
        //使用uuid生成盐值
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        String savePassword = passwordEncoder.crypt(password, salt);
        admin.setSalt(salt);
        admin.setPhone(phone);
        admin.setNickName(nickName);
        admin.setUsername(userName);
        admin.setPassword(savePassword);
        admin.setCreateAdminId(createAdminId);
        //是否要实现管理员添加
        admin.setStatus(AdminStatusEnum.ADMIN_STATUS_NORMAL.getCode());
        adminMapper.insertSelective(admin);
        //获取插入记录的id
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(userName);

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (null == admins) {
            return 0;
        }
        Integer adminId = admins.get(0).getId();

        //插入admin与group的admin_group表的关联
        AdminGroup adminGroup = new AdminGroup();
        adminGroup.setGroupId(groupId);
        adminGroup.setAdminId(adminId);
        adminGroup.setCreateAdminId(createAdminId);
        adminGroupMapper.insertSelective(adminGroup);
        return 1;

    }

    /**
     * 描述：删除管理员
     *
     * @param adminId 管理员id
     * @return 1 删除成功， 0 删除失败
     */
    @Override
    @Transactional
    public int deleteAdmin(Integer adminId) {
        return adminMapper.deleteByPrimaryKey(adminId);
    }


    /**
     * 判断管理员存不存在
     *
     * @param userName 管理员用户名
     * @return true 存在， false 不存在
     */
    @Override
    public boolean isExistAdmin(String userName) {

        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(userName);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins != null && !admins.isEmpty();
    }


    /**
     * 判断手机号码是否重复
     *
     * @param phone 管理员手机
     * @return true 重复， false 未重复
     */
    @Override
    public boolean isExistPhone(String phone) {

        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins != null && !admins.isEmpty();
    }


    /**
     * 根据管理员id获取管理员
     *
     * @param adminId 管理员ID
     * @return 管理员实例
     */
    @Override
    public Admin getAdmin(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    /**
     * 分页获取管理员列表
     *
     * @return 管理员列表页
     */
    @Override
    public PageInfo<ListedAdminVO> getListedAdmin(Integer pageNo, Integer pageSize) {

        List<ListedAdminVO> listedAdmin = adminManagerMapper.getListedAdmin();
        return new PageInfo<>(listedAdmin);

    }

    /**
     * 通过id查询管理员信息
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    @Override
    public ListedAdminVO getAdminById(Integer adminId) {
        return adminManagerMapper.getAdminById(adminId);
    }


    /**
     * 搜索管理员
     *
     * @param key  关键字(姓名、账号)
     * @param page 页码
     * @param size 每页长度
     * @return
     */
    @Override
    public PageInfo<Admin> getAdminsByKeyAndStatus(String key, Integer status, Integer page, Integer size) {


        AdminExample example = new AdminExample();

        if (!StringUtils.isEmpty(key)) {
            AdminExample.Criteria criteria = example.createCriteria();
            AdminExample.Criteria criteria2 = example.or();
            criteria.andNickNameLike("%" + key + "%");
            criteria2.andPhoneLike("%" + key + "%");
            if (status != null) {
                criteria.andStatusEqualTo(status);
                criteria2.andStatusEqualTo(status);
            }

        } else if (status != null) {
            AdminExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo(status);
        }

        //创建时间倒序
        example.setOrderByClause("update_time desc");

        return PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            adminMapper.selectByExample(example);
        });
    }
}
