package com.xhuabu.source.auth;


import com.xhuabu.source.common.manager.AnnotationManager;
import com.xhuabu.source.config.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jdk on 17/9/28.
 * 权限管理器
 */
@Component
public class JLAuthManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    JLAuthCommonService JLAuthCommonService;

    @Autowired
    AnnotationManager annotationManager;

    @Autowired
    PropertiesConfiguration propertiesConfiguration;


    /**
     *  获取权限及菜单封装bean
     */
    public JLAuthBean getAuthBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return (JLAuthBean) request.getSession().getAttribute(JLAuthCons.AUTH_KEY);
    }

    /**
     * 获取登录的用户id，没登录则返回0
     *
     * @return 0：没有用户 非0：用户ID
     */
    public Integer getUserId() {
        JLAuthBean authBean = getAuthBean();
        return authBean == null ? 0 : authBean.getUserId();
    }

    /**
     * 获取IP白名单列表
     *
     * @return IP白名单列表
     */
    public List<String> getWhiteIpList(){

//        Set<Class<?>> classSet = annotationManager.getConfigurationClasses();
//        List<String> ipList = new ArrayList<>();
//        try {
//            for ( Class aClass : classSet){
//                JLAuthConfiguration config = (JLAuthConfiguration)aClass.newInstance();
//                if (config.getIpWhiteList() != null && !config.getIpWhiteList().isEmpty()){
//                    ipList.addAll(config.getIpWhiteList());
//                }
//            }
//        }catch (Exception e){
//            logger.error("实例化配置类失败:{}", e);
//        }

        //从属性文件中获取
        String ipWhiteListStr = propertiesConfiguration.getIpWhiteList();
        if (ipWhiteListStr != null){
            return new ArrayList<>(Arrays.asList(ipWhiteListStr.split(",")));
        }

        return new ArrayList<>();
    }

    /**
     * 获取IP白名单列表
     *
     * @return IP白名单列表
     */
    public String getControllerPackagePath(){


        //从属性文件中获取
        return propertiesConfiguration.getContollerPackagePath();
    }


    /**
     *  鉴权
     *
     * @param request http请求对象
     * @param uri  请求uri
     * @return true 鉴权通过 false 鉴权失败
     */
    public boolean authentication(HttpServletRequest request, String uri) {

        //获取权限实体
        JLAuthBean authBean = (JLAuthBean) request.getSession().getAttribute(JLAuthCons.AUTH_KEY);

        if (authBean == null) {
            logger.info("鉴权不通过,session中没有权限信息，未登录？或者域名不对");
            return false;
        } else if (!authBean.getUriSet().contains(uri)) {
            logger.info("鉴权不通过,用户没有该uri的权限");
            return false;
        } else {
            logger.info("鉴权通过");
            return true;
        }
    }

    /**
     * 授权
     *
     * @param request http请求对象
     * @param userId 用户ID
     * @return 授权实体对象
     */
    public JLAuthBean grant(HttpServletRequest request, Integer userId) {

        //入参检验
        if (userId == null || userId == 0) {
            return null;
        }

        JLAuthBean authBean = JLAuthCommonService.getAvailableUriByUserId(userId);
        request.getSession().setAttribute(JLAuthCons.AUTH_KEY, authBean);

        return authBean;
    }


    /**
     *  解除授权
     *
     * @param request http请求对象
     */
    public void delGrant(HttpServletRequest request) {
        request.getSession().removeAttribute(JLAuthCons.AUTH_KEY);
    }

}
