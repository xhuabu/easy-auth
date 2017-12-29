package com.xhuabu.source.annotation.authentication;


import com.xhuabu.source.auth.JLAuthManager;
import com.xhuabu.source.common.tool.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;


@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JLAuthManager JLAuthManager;

    /**
     * 请求前鉴定权限
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("拦截请求ip：{}, url:{}", IpUtil.getIp(request), request.getServletPath());


        // IP白名单处理
        List<String> whiteIpList = JLAuthManager.getWhiteIpList();
        logger.info("ip白名单:{}", whiteIpList);

        for ( String whiteIp : whiteIpList){
            if (IpUtil.getIp(request).equals(whiteIp)) {
                logger.info("白名单，不进行登录校验");
                return true;
            }
        }


        //资源权限鉴定
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            Authentication authentication = h.getMethodAnnotation(Authentication.class);

            // 没有注解
            if (authentication == null) {
                return true;
            }

            //检查是否有权限 ，获取RequestMapping注解的value
            RequestMapping requestMapping = h.getMethodAnnotation(RequestMapping.class);

            //获取RequestMapping注解的value,用来鉴权
            String uri = requestMapping.value()[0];

            if (!JLAuthManager.authentication(request, uri)) {
                // 需要登录
                logger.error("鉴权不通过: {}", request.getRequestURL());

                if (!request.getServletPath().startsWith("/api/")) {
                    // 是页面请求跳转到登录页
                    response.setCharacterEncoding("utf-8");
                    response.sendRedirect("/tourist");
                    return false;
                }

                // 是api请求返回错误
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write("未鉴权");
                pw.flush();
                return false;
            }
            return true;
        }

        return true;
    }

}
