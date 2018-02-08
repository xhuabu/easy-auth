package com.xhuabu.source.model.vo;


import com.xhuabu.source.model.po.Auth;

/**
 * Created by jdk on 17/9/29.
 * 权限VO
 */
public class AuthVO {



    public Boolean getHave() {
        return isHave;
    }

    public void setHave(Boolean have) {
        isHave = have;
    }


    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    Auth auth;

    /**
     * 是否拥有该权限
     */
    Boolean isHave;
}
