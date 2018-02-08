package com.xhuabu.source.model.vo;

/**
 * Created by ffn on 26/9/17.
 */
public class AdminSignInVO {
    private String token;
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AdminSignInVO{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
