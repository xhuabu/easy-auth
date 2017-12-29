package com.xhuabu.source.annotation.authorization;

import com.xhuabu.source.auth.JLAuthManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JLAuthManager JLAuthManager;

    /**
     * controller 执行之后调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        logger.info("开始处理授权");
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            Authorization authorization = h.getMethodAnnotation(Authorization.class);

            if (authorization != null) {

                Object adminIdObject = request.getSession().getAttribute(authorization.adminIdFieldName());
                if (adminIdObject != null){

                    Integer adminId = (Integer)adminIdObject;
                    JLAuthManager.grant(request, adminId);
                }
            }

        }

        super.postHandle(request, response, handler, modelAndView);
    }
}
