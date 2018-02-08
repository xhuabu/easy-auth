package com.xhuabu.source.auth;

import com.xhuabu.source.annotation.authorization.Authj;

/**
 * Created by jdk on 17/12/22.
 * 权限对象
 */
public class AuthjBean {

    /**
     * 代表URI
     */
    String uri;

    /**
     * 权限对象名称
     */
    String name;

    /**AuthjInit
     * 是否需要鉴权
     */
    Boolean auth;


    public AuthjBean(String uri, Authj authj) {
        this.uri = uri;
        this.name = authj.value();
        this.auth = authj.auth();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

}
