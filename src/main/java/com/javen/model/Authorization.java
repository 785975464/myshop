package com.javen.model;

/**
 * Created by Jay on 2017/6/27.
 */
public class Authorization {
    Integer id;
    Integer role;
    Integer auth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "id=" + id +
                ", role=" + role +
                ", auth=" + auth +
                '}';
    }
}
