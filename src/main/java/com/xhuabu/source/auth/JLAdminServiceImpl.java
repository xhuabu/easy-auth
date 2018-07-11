package com.xhuabu.source.auth;


import com.github.pagehelper.PageInfo;
import com.xhuabu.source.common.coder.PasswordEncoder;
import com.xhuabu.source.common.enums.AdminStatusEnum;
import com.xhuabu.source.common.exception.AuthException;
import com.xhuabu.source.common.manager.AnnotationManager;
import com.xhuabu.source.domain.AdminDomain;
import com.xhuabu.source.domain.AdminGroupDomain;
import com.xhuabu.source.model.po.Admin;
import com.xhuabu.source.model.po.AdminExample;
import com.xhuabu.source.model.po.AdminGroup;
import com.xhuabu.source.model.po.AdminGroupExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class JLAdminServiceImpl implements JLAdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AnnotationManager annotationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AdminDomain adminDomain;

    @Autowired
    private AdminGroupDomain adminGroupDomain;


    // 管理员用户名密码登录
    @Override
    public Admin signin(String username, String password) throws AuthException {

        //1 判断用户是否存在
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        Admin admin = adminDomain.getByExample(example);
        if (admin == null) {
            logger.error("管理员不存在:{}", username);
            throw new AuthException("管理员不存在");
        }

        //2 判断密码是否正确
        String savedPwd = admin.getPassword();
        String salt = admin.getSalt();

        //todo 需要自定义密码加密方式
        Boolean isMatch = passwordEncoder.crypt(password, salt).equals(savedPwd);
        if (!isMatch) {
            logger.error("管理员:{} 登录密码不正确:{}", username, password);
            throw new AuthException("管理员登录密码不正确");
        }

        //3 判断账户当前状态是否为"冻结"
        if (AdminStatusEnum.FREEZE.getCode().equals(admin.getStatus())) {
            logger.info("当前账户已被冻结！");
            throw new AuthException("当前账户已被冻结!");
        }

        //4 登录成功，记录当前登录时间
//        admin.setLastTime(new Date());
//        adminMapper.updateByPrimaryKeySelective(admin);

        logger.info("管理员登录成功: {}， {}", username, password);
        return admin;
    }


    // 管理员电话密码登录
    @Override
    public Admin signinWithPhone(String phone, String password) throws AuthException {
        //1 判断用户是否存在
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        Admin admin = adminDomain.getByExample(example);
        if (admin == null) {
            logger.error("管理员不存在:{}", phone);
            throw new AuthException("管理员不存在");
        }

        //2 判断密码是否正确
        String savedPwd = admin.getPassword();
        String salt = admin.getSalt();

        //todo 需要自定义密码加密方式
        Boolean pwdMathch = passwordEncoder.crypt(password, salt).equals(savedPwd);
        if (!pwdMathch) {
            logger.error("管理员:{} 登录密码不正确:{}", phone, password);
            throw new AuthException("管理员登录密码不正确");
        }

        //3 判断账户当前状态是否为"冻结"
        if (AdminStatusEnum.FREEZE.getCode().equals(admin.getStatus())) {
            logger.info("当前账户已被冻结！");
            throw new AuthException("当前账户已被冻结!");
        }

        //4 登录成功，记录当前登录时间
//        admin.setLastTime(new Date());
//        adminMapper.updateByPrimaryKeySelective(admin);

        logger.info("管理员登录成功: {}， {}", phone, password);
        return admin;
    }


    // 编辑管理员
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer updateAdmin(Integer id, String nickName, String password, Integer groupId, Integer status, Integer createAdminId) throws AuthException {

        Admin admin = adminDomain.get(id, true);
        if (admin == null) {
            logger.info("不存在该管理员！");
            throw new AuthException("管理员不存在");
        }
        admin.setNickname(nickName);

        //如果password不为空，则重置密码
        if (StringUtils.isNotEmpty(password)) {
            String salt = admin.getSalt();

            password = passwordEncoder.crypt(password, salt);
            admin.setPassword(password);
        }

        admin.setStatus(status);

        //更新admin表的管理员信息
        adminDomain.save(admin);

        AdminGroupExample example = new AdminGroupExample();
        example.or().andAdminIdEqualTo(id);
        AdminGroup adminGroup = adminGroupDomain.getByExample(example);
        if (!adminGroup.getGroupId().equals(groupId)) {
            // groupId发生变化, 更新admin_group表的管理员与组的关联信息
            adminGroup.setGroupId(groupId);
            adminGroupDomain.save(adminGroup);
        }

        return 0;
    }


    // 增加管理员
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer insertAdmin(String nickName, String userName, String password, String phone, Integer groupId, Integer createAdminId) {

        //新增管理员
        Admin admin = new Admin();
        //使用uuid生成盐值
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        String savePassword = passwordEncoder.crypt(password, salt);
        admin.setSalt(salt);
        admin.setPhone(phone);
        admin.setNickname(nickName);
        admin.setUsername(userName);
        admin.setPassword(savePassword);
        admin.setCreateAdminId(createAdminId);
        admin.setStatus(AdminStatusEnum.NORMAL.getCode());
        adminDomain.add(admin);

        //获取插入记录的id
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(userName);

        admin = adminDomain.getByExample(adminExample);

        //插入admin与group的admin_group表的关联
        AdminGroup adminGroup = new AdminGroup();
        adminGroup.setAdminId(admin.getId());
        adminGroup.setGroupId(groupId);
        adminGroup.setCreateAdminId(createAdminId);
        adminGroupDomain.add(adminGroup);
        return 0;
    }


    // 删除管理员
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteAdmin(Integer adminId) {

        // 1.删除组、管理员关系
        AdminGroupExample adminGroupExample = new AdminGroupExample();
        AdminGroupExample.Criteria criteria = adminGroupExample.createCriteria();
        criteria.andAdminIdEqualTo(adminId);
        adminGroupDomain.deleteByExample(adminGroupExample);

        // 2.删除管理员
        Admin admin = new Admin();
        admin.setId(adminId);
        adminDomain.delete(admin);

        return 0;
    }


    // 判断管理员存不存在
    @Override
    public Boolean isExistAdmin(String userName) {

        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(userName);
        Admin admin = adminDomain.getByExample(adminExample);
        return (admin != null);
    }


    // 判断手机号码是否重复
    @Override
    public Boolean isExistPhone(String phone) {

        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        Admin admin = adminDomain.getByExample(adminExample);
        return (admin != null);
    }


    // 管理员信息
    @Override
    public Admin getAdmin(Integer adminId) {
        return adminDomain.get(adminId, true);
    }


    // 搜索管理员
    @Override
    public PageInfo<Admin> getAdmins(Integer groupId, String username, String phone, Integer status, Integer page, Integer size) {

        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();

        List<Integer> adminIds = new ArrayList<>(0);
        if (groupId != null) {
            AdminGroupExample adminGroupExample = new AdminGroupExample();
            AdminGroupExample.Criteria criteria1 = adminGroupExample.createCriteria();
            criteria1.andGroupIdEqualTo(groupId);
            PageInfo<AdminGroup> adminGroupPageInfo = adminGroupDomain.getsByExample(adminGroupExample, page, size);
            for (AdminGroup adminGroup : adminGroupPageInfo.getList()) {
                adminIds.add(adminGroup.getAdminId());
            }
        }

        if (adminIds.size() != 0) {
            criteria.andIdIn(adminIds);
        }

        if (StringUtils.isNotEmpty(username)) {
            criteria.andNicknameLike("%" + username + "%");
        }

        if (StringUtils.isNotEmpty(phone)) {
            criteria.andPhoneLike("%" + phone + "%");
        }

        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        // 创建时间倒序
        if (StringUtils.isEmpty(example.getOrderByClause())) {
            example.setOrderByClause("create_time desc");
        }

        return adminDomain.getsByExample(example, page, size);
    }
}
